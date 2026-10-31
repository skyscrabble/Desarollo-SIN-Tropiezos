package com.mitologia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Lee un formato sencillo para los ficheros de escenarios.
 * No intenta inferir conocimiento: solo transforma texto en datos de entrada.
 */
public final class EscenarioParser {

    private EscenarioParser() {
    }

    /**
     * Lee un fichero de escenario y construye el objeto Escenario
     * a partir de sus lineas, separando entre condiciones y misiones.
     *
     * @param fichero ruta del fichero que contiene la definicion textual del escenario
     * @return escenario creado con su nombre, condiciones y mision
     * @throws IOException si se produce un error al leer el fichero
     */
    public static Escenario leer(Path fichero) throws IOException {
        Escenario escenario = new Escenario();
        escenario.nombre = fichero.getFileName().toString();

        List<String> lineas = Files.readAllLines(fichero);
        for (int numero = 1; numero <= lineas.size(); numero++) {
            String linea = lineas.get(numero - 1).trim();
            if (linea.isEmpty() || linea.startsWith("#")) {
                continue;
            }

            String[] partes = linea.split("\\s+");
            String tipo = partes[0].toUpperCase();
            if ("PREGUNTA".equals(tipo)) {
                escenario.mision = leerMision(partes, linea, numero);
            } else {
                escenario.condiciones.add(leerCondicion(partes, numero));
            }
        }

        if (escenario.mision == null) {
            escenario.mision = new Mision(Mision.Tipo.NINGUNA, null);
        }
        return escenario;
    }

    /**
     * Construye una mision a partir de los elementos de una linea de PREGUNTA.
     *
     * @param partes elementos separados de la linea de pregunta
     * @param linea texto completo de la linea, utilizado en los mensajes de error
     * @param numero numero de linea del fichero
     * @return mision creada con su tipo, sujeto y objetivo
     * @throws IllegalArgumentException si la pregunta no tiene el formato valido
     *                                  o si su tipo no esta reconocido
     */
    private static Mision leerMision(String[] partes, String linea, int numero) {
        if (partes.length < 3) {
            throw error(numero, "La pregunta debe tener tipo y sujeto: " + linea);
        }

        Mision.Tipo tipo;
        try {
            tipo = Mision.Tipo.valueOf(partes[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw error(numero, "Tipo de pregunta no valido: " + partes[1]);
        }

        Mision mision = new Mision(tipo, null);
        // La resolucion de nombres se realiza en AplicadorCondiciones
        mision.sujeto = new clases.personajes.Humano(decodificar(partes[2]), true, true);
        if (partes.length > 3) {
            String objetivo = unirDesde(partes, 3);
            if (tipo == Mision.Tipo.OBJETO) {
                mision.objetivoObjeto = new clases.Objeto(decodificar(objetivo));
            } else if (tipo == Mision.Tipo.CAPACIDAD) {
                mision.objetivoCapacidad = new clases.Capacidad(decodificar(objetivo));
            } else {
                mision.objetivoPersonaje = new clases.personajes.Humano(decodificar(objetivo), true, true);
            }
        }
        return mision;
    }

    /**
     * Construye una condicion inicial a partir de los elementos de una linea
     * del fichero del escenario.
     *
     * @param partes elementos separados de la linea de CONDICION.
     * @param numero numero de linea del fichero
     * @return condicion inicial creada con su tipo y argumentos
     * @throws IllegalArgumentException si el tipo no se reconoce o faltan argumentos
     */
    private static CondicionInicial leerCondicion(String[] partes, int numero) {
        String tipo = partes[0].toUpperCase();
        CondicionInicial.Tipo condicion;
        try {
            condicion = CondicionInicial.Tipo.valueOf(tipo);
        } catch (IllegalArgumentException e) {
            throw error(numero, "Condicion no reconocida: " + partes[0]);
        }

        int minimo = condicion == CondicionInicial.Tipo.PRESO
                || condicion == CondicionInicial.Tipo.LIBRE
                || condicion == CondicionInicial.Tipo.VIVO
                || condicion == CondicionInicial.Tipo.MUERTO
                ? 2 : 3;
        if (partes.length < minimo) {
            throw error(numero, "Faltan argumentos en la condicion " + tipo);
        }

        String primero = decodificar(partes[1]);
        String segundo = partes.length > 2 ? decodificar(unirDesde(partes, 2)) : null;
        return new CondicionInicial(condicion, primero, segundo);
    }

    // Une los elementos de un array desde una posicion determinada, separandolos mediante espacios
    private static String unirDesde(String[] partes, int inicio) {
        StringBuilder resultado = new StringBuilder();
        for (int i = inicio; i < partes.length; i++) {
            if (resultado.length() > 0) {
                resultado.append(' ');
            }
            resultado.append(partes[i]);
        }
        return resultado.toString();
    }

    //Decodifica un texto sustituyendo los guiones bajos por espacios.
    private static String decodificar(String texto) {
        return texto.replace('_', ' ');
    }

    // Excepcion de error indicando el numero de linea en el que se produjo el error
    private static IllegalArgumentException error(int numero, String mensaje) {
        return new IllegalArgumentException("Error en la linea " + numero + ": " + mensaje);
    }
}
