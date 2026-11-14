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
                System.out.println("ERROR en " + fichero.getFileName() + ": " + e.getMessage());
            }
        }
    }

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

        System.out.println("=== " + nombre + " ===");
        for (String linea : Files.readAllLines(fichero, StandardCharsets.UTF_8)) {
            System.out.println(linea);
        }
        System.out.println("\n *** Fin Descripcion Escenario ***\n\n");

        Escenario escenario = EscenarioParser.leer(fichero);
        List<Object> hechos = BaseHechos.crearHechosIniciales();
        Map<String, Object> indice = BaseHechos.crearIndice(hechos);

        KieSession session = kContainer.newKieSession("ksession-mitologia");
        List<String> salida = new ArrayList<>();
        session.setGlobal("salida", salida);

        try {
            for (Object hecho : hechos) {
                session.insert(hecho);
            }
            Mision mision = AplicadorCondiciones.aplicar(escenario, session, indice);

            session.getAgenda().getAgendaGroup("Informacion Inicial").setFocus();
            session.fireAllRules();

            Path destino = carpetaSoluciones.resolve(
                    "Respuesta" + nombre.replaceFirst("^Escenario", ""));
            escribirRespuesta(destino, mision, salida);
        } finally {
            session.dispose();
        }
        System.out.println("\n *** Fin Escenario " + nombre + " ***\n\n");
    }

    private static void escribirRespuesta(Path destino, Mision mision, List<String> salidaRelato) throws IOException {
        List<String> lineas = new ArrayList<>();

        String nombreObjetivo = "";
        if (mision.objetivoPersonaje != null) {
            nombreObjetivo = mision.objetivoPersonaje.nombre;
        } else if (mision.objetivoObjeto != null) {
            nombreObjetivo = mision.objetivoObjeto.nombre;
        } else if (mision.objetivoCapacidad != null) {
            nombreObjetivo = mision.objetivoCapacidad.nombre;
        }

        String verbo;
        switch (mision.tipo) {
            case MATAR:     verbo = "matar a"; break;
            case APRESAR:   verbo = "apresar a"; break;
            case LIBERAR:   verbo = "liberar a"; break;
            case OBJETO:    verbo = "obtener"; break;
            case CAPACIDAD: verbo = "obtener la capacidad"; break;
            default:        verbo = "cumplir la mision"; break;
        }

        String sujeto = mision.sujeto != null ? mision.sujeto.nombre : "El heroe";
        String pregunta = sujeto + (mision.alcanzada ? " puede " : " no puede ") + verbo
                + (nombreObjetivo.isEmpty() ? "" : " " + nombreObjetivo);

        if (mision.alcanzada) {
            lineas.add("Si, " + pregunta + " debido a que:");
            lineas.addAll(salidaRelato);
        } else {
            lineas.add("No, " + pregunta + " con las condiciones iniciales");
            if (!salidaRelato.isEmpty()) {
                lineas.add("Sucesos que si se producen:");
                lineas.addAll(salidaRelato);
            }
        }

        Files.write(destino, lineas, StandardCharsets.UTF_8);

        for (String linea : lineas) {
            System.out.println(linea);
        }
    }
}  