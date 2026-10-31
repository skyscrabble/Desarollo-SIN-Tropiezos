package com.mitologia;

import clases.Capacidad;
import clases.Objeto;
import clases.personajes.Personaje;

// La mision es la pregunta que queremos resolver
public class Mision {
    public enum Tipo {
        MATAR,
        APRESAR,
        LIBERAR,
        OBJETO,
        CAPACIDAD,
        NINGUNA
    }

    public Tipo tipo;
    public Personaje sujeto;
    public Personaje objetivoPersonaje;
    public Objeto objetivoObjeto;
    public Capacidad objetivoCapacidad;
    public boolean alcanzada;
    public boolean imposible;	// Prescindible, modelable solo con un booleano

    public Mision() {
    }

    public Mision(Tipo tipo, Personaje sujeto) {
        this.tipo = tipo;
        this.sujeto = sujeto;
    }
}
