package com.mitologia;

import java.util.ArrayList;
import java.util.List;

public class Escenario {
    public String nombre;
    public Mision mision;	// Pregunta inicial, lo que se quiere resolver
    public List<CondicionInicial> condiciones = new ArrayList<>();
}
