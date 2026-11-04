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

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

	public Capacidad getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Capacidad capacidad) {
		this.capacidad = capacidad;
	}
    
    
}
