package com.mitologia;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Main {

    public static void main(String[] args) throws IOException {
        String escenarios = "escenarios";
        String soluciones = "soluciones";
        if (args.length >= 1) escenarios = args[0];
        if (args.length >= 2) soluciones = args[1];

        Path carpetaSoluciones = Paths.get(soluciones);
        Files.createDirectories(carpetaSoluciones);

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        for (Path fichero : listarEscenarios(Paths.get(escenarios))) {
            try {
                procesar(fichero, kContainer, carpetaSoluciones);
            } catch (IllegalArgumentException e) {
                // Un escenario erroneo no cancela los demas
                System.out.println("ERROR en " + fichero.getFileName() + ": " + e.getMessage());
            }
        }
    }

    // Devuelve los .txt por nombre
    private static List<Path> listarEscenarios(Path carpeta) throws IOException {
        List<Path> ficheros = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(carpeta, "*.txt")) {
            for (Path p : stream) {
                ficheros.add(p);
            }
        }
        Collections.sort(ficheros);
        return ficheros;
    }

    private static void procesar(Path fichero, KieContainer kContainer, Path carpetaSoluciones)
            throws IOException {
        String nombre = fichero.getFileName().toString();

        // Mostrar el escenario en consola
        System.out.println("=== " + nombre + " ===");
        for (String linea : Files.readAllLines(fichero, StandardCharsets.UTF_8)) {
            System.out.println(linea);
        }
        System.out.println("\n *** Fin Descripcion Escenario ***\n\n");

        //  Leer escenario y crear hechos (nuevos)
        Escenario escenario = EscenarioParser.leer(fichero);
        List<Object> hechos = BaseHechos.crearHechosIniciales();
        Map<String, Object> indice = BaseHechos.crearIndice(hechos);

        // Una sesion por escenario
        KieSession session = kContainer.newKieSession("ksession-mitologia");
        try {
            // Primero la base de hechos
        	//FactHandle para el Aplicador de condiciones
            for (Object hecho : hechos) {
                session.insert(hecho);
            }
            Mision mision = AplicadorCondiciones.aplicar(escenario, session, indice);

            //  Ejecutar primero la informacion inicial luego las demas reglas
            session.getAgenda().getAgendaGroup("Informacion Inicial").setFocus();
            session.fireAllRules();

            //  Resultado
            Path destino = carpetaSoluciones.resolve(
                    "Respuesta" + nombre.replaceFirst("^Escenario", ""));
            escribirRespuesta(destino, mision);
        } finally {
            session.dispose();
        }
        System.out.println("\n *** Fin Escenario " + nombre + " ***\n\n");
    }

    // De momento solo Si/No. Mover a una clase GeneradorSalidas cuando exista el relato.
    private static void escribirRespuesta(Path destino, Mision mision) throws IOException {
        List<String> lineas = new ArrayList<>();
        lineas.add(mision.alcanzada ? "Si" : "No");
        // TODO: anadir el relato "X debido a Y" cuando las reglas lo generen
        Files.write(destino, lineas, StandardCharsets.UTF_8);

        for (String linea : lineas) {
            System.out.println(linea);
        }
    }
}