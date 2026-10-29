package clases.relaciones;

import clases.Capacidad;
import clases.personajes.Personaje;

public class TieneCapacidad {
    public Personaje personaje;
    public Capacidad capacidad;

    public TieneCapacidad() {
    }

    public TieneCapacidad(Personaje personaje, Capacidad capacidad) {
        this.personaje = personaje;
        this.capacidad = capacidad;
    }
}
