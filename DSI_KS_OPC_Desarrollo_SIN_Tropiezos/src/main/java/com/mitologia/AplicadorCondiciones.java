package com.mitologia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieSession;

import clases.Capacidad;
import clases.Objeto;
import clases.acciones.AccionDestinoObjeto;
import clases.acciones.AccionDestinoPersonaje;
import clases.acciones.Apresar;
import clases.acciones.Localizar;
import clases.personajes.Dios;
import clases.personajes.Personaje;
import clases.relaciones.TieneCapacidad;
import clases.relaciones.TieneEnojo;
import clases.relaciones.TieneFavor;
import clases.relaciones.TieneObjeto;

// Convierte las condiciones explicitas de un escenario en hechos iniciales utilizables por Drools
public final class AplicadorCondiciones {

    private AplicadorCondiciones() {
    }

    /**
     * Aplica las condiciones iniciales de un escenario y registra su mision
     * en la sesion de Drools.
     *
     * @param escenario escenario que contiene la mision y las condiciones iniciales
     * @param session sesion de Drools donde se insertan los hechos
     * @param indice mapa utilizado para resolver referencias a todos los tipos de clases
     * @return mision resuelta y aplicada al escenario
     */
    public static Mision aplicar(Escenario escenario, KieSession session, Map<String, Object> indice) {
        Mision mision = resolverMision(escenario.mision, indice);

        // Aplicar todas las condiciones
        for (CondicionInicial condicion : escenario.condiciones) {
            aplicarCondicion(condicion, session, indice);
        }
        
        // Insertar la mision en la sesion
        session.insert(mision);
        return mision;
    }

    // Debe utilizarse siempre el objeto que esta en la base de hechos
    /**
     * Sustituye los objetos provisionales que crea el parser por los objetos
     * reales que ya existen en la base de hechos.
     *
     * @param mision mision original con las referencias pendientes de resolver
     * @param indice mapa utilizado para resolver referencias a todos los tipos de clases
     * @return mision con todas las referencias a la base de hechos resueltas
     */
    private static Mision resolverMision(Mision original, Map<String, Object> indice) {
        Mision mision = new Mision();
        mision.tipo = original.tipo;	// El tipo de la original se mantiene
        if (original.sujeto != null) {
            mision.sujeto = exigir(indice, original.sujeto.nombre, Personaje.class);
        }
        if (original.objetivoPersonaje != null) {
        	// exigir busca un objeto por nombre y comprueba que sea del tipo esperado
            mision.objetivoPersonaje = exigir(indice, original.objetivoPersonaje.nombre, Personaje.class);
        }
        if (original.objetivoObjeto != null) {
            mision.objetivoObjeto = exigir(indice, original.objetivoObjeto.nombre, Objeto.class);
        }
        if (original.objetivoCapacidad != null) {
            mision.objetivoCapacidad = exigir(indice, original.objetivoCapacidad.nombre, Capacidad.class);
        }
        return mision;
    }

    /**
     * Analiza el tipo de condicion, obtiene sus elementos y actualiza la sesion de Drools
     * con los hechos o cambios de estado correspondientes.
     *
     * @param condicion condicion que se debe aplicar
     * @param session sesion de Drools donde se insertan o actualizan los hechos
     * @param indice mapa utilizado para resolver referencias a todos los tipos de clases
     * @throws IllegalArgumentException si el tipo de condicion no esta soportado
     */    
    private static void aplicarCondicion(CondicionInicial condicion,
            KieSession session, Map<String, Object> indice) {
        Personaje primero = exigir(indice, condicion.primero, Personaje.class);
        Object segundo = condicion.segundo == null ? null : indice.get(condicion.segundo);

        switch (condicion.tipo) {
        case FAVOR:
            session.insert(new TieneFavor(primero, exigir(indice, condicion.segundo, Dios.class)));
            break;
        case ENOJO:
            session.insert(new TieneEnojo(primero,
                    exigir(indice, condicion.segundo, Personaje.class)));
            break;
        case POSEE:
        	// Antes de asignar el objeto al nuevo propietario, elimina cualquier posesion anterior
        	// Asi un objeto solo puede teneer un poseedor
            borrarPosesionDeOtrosPersonajes(session, primero,
                    exigir(indice, condicion.segundo, Objeto.class));
            session.insert(new TieneObjeto(primero,
                    exigir(indice, condicion.segundo, Objeto.class)));
            break;
        case NO_POSEE:
        	// Elimina de la sesion la relación de TieneObjeto entre un Personaje y un Objeto
            borrarPosesion(session, primero,
                    exigir(indice, condicion.segundo, Objeto.class));
            break;
        case CAPACIDAD:
            session.insert(new TieneCapacidad(primero,
                    exigir(indice, condicion.segundo, Capacidad.class)));
            break;
        case PRESO:
            primero.estado_libertad = false;
            session.update(session.getFactHandle(primero), primero);
            break;
        case LIBRE:
            primero.estado_libertad = true;
            session.update(session.getFactHandle(primero), primero);
            break;
        case VIVO:
            primero.estado_vida = true;
            session.update(session.getFactHandle(primero), primero);
            break;
        case MUERTO:
            primero.estado_vida = false;
            session.update(session.getFactHandle(primero), primero);
            break;
        // En caso de que se puedan modificar relaciones familiares
        /*
        case PARENTESCO:
            session.insert(new Parentesco(primero,
                    exigir(indice, condicion.segundo, Personaje.class)));
            break;
        */
        case APRESADO:
            insertarApresamiento(session, primero,
                    exigir(indice, condicion.segundo, Personaje.class));
            break;
        case LOCALIZA:
            insertarLocalizacion(session, primero, segundo);
            break;
        default:
            throw new IllegalArgumentException("Condicion no soportada: " + condicion.tipo);
        }
    }

    /**
     * Registra una Accion de Apresar y marca a la victima con el estado de preso.
     *
     * @param session sesion de Drools donde se actualizan e insertan los hechos
     * @param victima personaje que sera apresado
     * @param captor personaje que realiza el apresamiento
     */
    private static void insertarApresamiento(KieSession session, Personaje victima, Personaje captor) {
        victima.estado_libertad = false;
        session.update(session.getFactHandle(victima), victima);
        Apresar accion = new Apresar();
        accion.sujeto = captor;
        session.insert(accion);
        session.insert(new AccionDestinoPersonaje(accion, victima));
    }

    /**
     * Registra una Accion de Localizar y establece su destino, que puede ser
     * un personaje o un objeto.
     *
     * @param session sesion de Drools donde se insertan los hechos
     * @param sujeto personaje que realiza la accion de Localizar
     * @param destino personaje u objeto que se desea localizar
     * @throws IllegalArgumentException si el destino no es un personaje ni un objeto
     */
    private static void insertarLocalizacion(KieSession session, Personaje sujeto, Object destino) {
        Localizar accion = new Localizar();
        accion.sujeto = sujeto;
        session.insert(accion);
        if (destino instanceof Personaje) {
            session.insert(new AccionDestinoPersonaje(accion, (Personaje) destino));
        } else if (destino instanceof Objeto) {
            session.insert(new AccionDestinoObjeto(accion, (Objeto) destino));
        } else {
            throw new IllegalArgumentException("LOCALIZA necesita un destino valido");
        }
    }

    /**
     * Elimina de la sesion la relación de TieneObjeto entre un Personaje y un Objeto.
     *
     * @param session sesion de Drools que contiene los hechos de Posesion
     * @param personaje personaje cuya Posesion se desea eliminar
     * @param objeto Objeto que dejara de pertenecer al personaje
     */
    private static void borrarPosesion(KieSession session, Personaje personaje, Objeto objeto) {
        List<Object> borrar = new ArrayList<>();
        for (Object hecho : session.getObjects()) {
            if (hecho instanceof TieneObjeto) {
                TieneObjeto posesion = (TieneObjeto) hecho;
                if (posesion.personaje == personaje && posesion.objeto == objeto) {
                    borrar.add(hecho);
                }
            }
        }
        for (Object hecho : borrar) {
            session.delete(session.getFactHandle(hecho));
        }
    }

    /**
     * Elimina de la sesion las relaciones de TieneObjeto del Objeto que pertenecen
     * a personajes que no sean el propietario indicado.
     *
     * @param session sesion de Drools que contiene los hechos de posesion
     * @param propietario personaje que debe conservar la posesion del objeto
     * @param objeto objeto cuya posesion se comprueba
     */
    private static void borrarPosesionDeOtrosPersonajes(KieSession session,
            Personaje propietario, Objeto objeto) {
        List<Object> borrar = new ArrayList<>();
        for (Object hecho : session.getObjects()) {
            if (hecho instanceof TieneObjeto) {
                TieneObjeto posesion = (TieneObjeto) hecho;
                if (posesion.objeto == objeto && posesion.personaje != propietario) {
                	// Primero construir el conjunto a borrar
                    borrar.add(hecho);
                }
            }
        }
        // Despues, borrarlo de la sesion
        for (Object hecho : borrar) {
            session.delete(session.getFactHandle(hecho));
        }
    }

    // Metodo generado por IA y revisado
    /**
     * Busca un elemento del tipo indicado en el indice y garantiza que exista.
     *
     * @param indice mapa utilizado para resolver referencias a todos los tipos de clases
     * @param nombre nombre del elemento que se desea buscar
     * @param tipo clase esperada del elemento
     * @param <T> tipo del elemento devuelto
     * @return elemento encontrado y convertido al tipo indicado
     * @throws IllegalArgumentException si no existe un elemento con ese nombre y tipo
     */
    private static <T> T exigir(Map<String, Object> indice, String nombre, Class<T> tipo) {
        T resultado = buscar(indice, nombre, tipo);
        if (resultado == null) {
            throw new IllegalArgumentException("No existe " + tipo.getSimpleName()
                    + " con nombre: " + nombre);
        }
        return resultado;
    }

    // Metodo generado por IA y revisado
    /**
     * Busca un objeto en el indice y comprueba que pertenece al tipo indicado.
     *
     * @param indice mapa utilizado para resolver referencias a todos los tipos de clases
     * @param nombre nombre del objeto que se desea buscar
     * @param tipo clase que debe tener el objeto encontrado
     * @param <T> tipo del objeto devuelto
     * @return objeto convertido al tipo indicado, o {@code null} si no existe
     *         o no coincide con el tipo esperado
     */
    private static <T> T buscar(Map<String, Object> indice, String nombre, Class<T> tipo) {
        Object objeto = indice.get(nombre);
        return tipo.isInstance(objeto) ? tipo.cast(objeto) : null;
    }
}
