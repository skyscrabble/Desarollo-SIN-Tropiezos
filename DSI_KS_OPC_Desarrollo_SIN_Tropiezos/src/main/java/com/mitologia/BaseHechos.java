package com.mitologia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clases.Capacidad;
import clases.Objeto;
import clases.Ubicacion;
import clases.personajes.Criatura;
import clases.personajes.Dios;
import clases.personajes.Heroe;
import clases.personajes.Humano;
import clases.personajes.Personaje;
import clases.personajes.Semidios;
import clases.relaciones.OtorgaCapacidad;
import clases.relaciones.Parentesco;
import clases.relaciones.TieneCapacidad;
import clases.relaciones.TieneEnojo;
import clases.relaciones.TieneFavor;
import clases.relaciones.TieneObjeto;

/**
 * Crea unicamente hechos iniciales. No realiza inferencia.
 * Codigo generado con IA y revisado manualmente.
 */
public final class BaseHechos {

    private BaseHechos() {
    }

    public static List<Object> crearHechosIniciales() {
        List<Object> hechos = new ArrayList<>();

        Ubicacion laberinto = new Ubicacion("Laberinto del Minotauro");
        Ubicacion islaMedusa = new Ubicacion("Isla de las Gorgonas");
        agregar(hechos, laberinto, islaMedusa);

        Dios poseidon = new Dios("Poseidon", true, true);
        Dios atenea = new Dios("Atenea", true, true);
        Dios hermes = new Dios("Hermes", true, true);
        Dios hefesto = new Dios("Hefesto", true, true);
        Dios hades = new Dios("Hades", true, true);
        Dios zeus = new Dios("Zeus", true, true);

        Humano casiopea = new Humano("Casiopea", true, true);
        Humano andromeda = new Humano("Andromeda", false, true);
        Humano minos = new Humano("Minos", true, true);
        Humano dedalo = new Humano("Dedalo", true, true);
        Humano icaro = new Humano("Icaro", true, true);
        Humano danae = new Humano("Danae", true, true);
        
        Semidios perseo = new Semidios("Perseo", true, true);
        
        Heroe heracles = new Heroe("Heracles", true, true);
        Heroe teseo = new Heroe("Teseo", true, true);
        Heroe ariadna = new Heroe("Ariadna", true, true);

        Criatura ceto = new Criatura("Ceto", false, true);
        Criatura grayas = new Criatura("Grayas", true, true);
        Criatura ninfas = new Criatura("Ninfas", true, true);
        Criatura medusa = new Criatura("Medusa", true, true);
        Criatura minotauro = new Criatura("Minotauro", false, true);
        Criatura pegaso = new Criatura("Pegaso", true, true);

        minotauro.ubicacion_actual = laberinto;
        medusa.ubicacion_actual = islaMedusa;

        agregar(hechos, poseidon, atenea, hermes, hefesto, hades, zeus,
                casiopea, andromeda, perseo, heracles, teseo, ariadna, minos,
                dedalo, icaro, ceto, grayas, ninfas, medusa, minotauro, pegaso);

        Capacidad invisibilidad = new Capacidad("Invisibilidad");
        Capacidad vuelo = new Capacidad("Vuelo");
        Capacidad espejo = new Capacidad("Espejo");
        Capacidad fuerza = new Capacidad("Fuerza");
        agregar(hechos, invisibilidad, vuelo, espejo, fuerza);

        Objeto brida = new Objeto("Brida de Oro");
        Objeto escudoBronce = new Objeto("Escudo de Bronce");
        Objeto escudoEspejo = new Objeto("Escudo Espejo");
        Objeto sandalias = new Objeto("Sandalias Aladas");
        Objeto zurron = new Objeto("Zurron Magico");
        Objeto yelmo = new Objeto("Yelmo de Hades");
        Objeto hoz = new Objeto("Hoz de Acero");
        Objeto ojoGrayas = new Objeto("Ojo de las Grayas");
        Objeto cabezaMedusa = new Objeto("Cabeza de Medusa");
        Objeto cabezaCeto = new Objeto("Cabeza de Ceto");
        Objeto cabezaMinotauro = new Objeto("Cabeza de Minotauro");
        Objeto hilo = new Objeto("Hilo de Ariadna");
        Objeto espada = new Objeto("Espada de Teseo");
        Objeto alasIcaro = new Objeto("Alas de Icaro");
        Objeto alasDedalo = new Objeto("Alas de Dedalo");
        Objeto cuerno = new Objeto("Cuerno del Minotauro");
        Objeto cera = new Objeto("Cera de Abejas");
        Objeto plumas = new Objeto("Plumas de Aves");
        agregar(hechos, brida, escudoBronce, escudoEspejo, sandalias, zurron,
                yelmo, hoz, ojoGrayas, cabezaMedusa, cabezaCeto, cabezaMinotauro, hilo, espada,
                alasIcaro, alasDedalo, cuerno, cera, plumas);

        // Objetos que conceden capacidades
        hechos.add(new OtorgaCapacidad(yelmo, invisibilidad));
        hechos.add(new OtorgaCapacidad(sandalias, vuelo));
        hechos.add(new OtorgaCapacidad(escudoEspejo, espejo));
        hechos.add(new OtorgaCapacidad(alasIcaro, vuelo));
        hechos.add(new OtorgaCapacidad(alasDedalo, vuelo));
        hechos.add(new OtorgaCapacidad(cuerno, fuerza));

        // Posesiones iniciales
        hechos.add(new TieneObjeto(hefesto, brida));
        hechos.add(new TieneObjeto(atenea, escudoBronce));
        hechos.add(new TieneObjeto(hermes, hoz));
        hechos.add(new TieneObjeto(hades, yelmo));
        hechos.add(new TieneObjeto(ninfas, zurron));
        hechos.add(new TieneObjeto(ninfas, sandalias));
        hechos.add(new TieneObjeto(grayas, ojoGrayas));
        hechos.add(new TieneObjeto(ariadna, hilo));
        hechos.add(new TieneObjeto(teseo, espada));
        hechos.add(new TieneObjeto(minotauro, cuerno));
        hechos.add(new TieneObjeto(dedalo, cera));
        hechos.add(new TieneObjeto(dedalo, plumas));
        
        // Capacidades que ya existen por conocimiento inicial
        hechos.add(new TieneCapacidad(heracles, fuerza));

        // Relaciones de parentesco y favor/enojo
        hechos.add(new Parentesco(casiopea, andromeda));
        hechos.add(new Parentesco(zeus, perseo));
        hechos.add(new Parentesco(danae, perseo));
        hechos.add(new TieneEnojo(casiopea, poseidon));
        hechos.add(new TieneFavor(heracles, zeus));

        return hechos;
    }

    public static Map<String, Object> crearIndice(List<Object> hechos) {
        Map<String, Object> indice = new HashMap<>();
        for (Object hecho : hechos) {
            String nombre = nombreDe(hecho);
            if (nombre != null) {
                indice.put(nombre, hecho);
            }
        }
        return indice;
    }

    private static void agregar(List<Object> hechos, Object... objetos) {
        for (Object objeto : objetos) {
            hechos.add(objeto);
        }
    }

    private static String nombreDe(Object objeto) {
        if (objeto instanceof Personaje) {
            return ((Personaje) objeto).nombre;
        }
        if (objeto instanceof Objeto) {
            return ((Objeto) objeto).nombre;
        }
        if (objeto instanceof Capacidad) {
            return ((Capacidad) objeto).nombre;
        }
        if (objeto instanceof Ubicacion) {
            return ((Ubicacion) objeto).nombre;
        }
        return null;
    }
}
