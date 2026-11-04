package clases.relaciones;

import clases.personajes.Personaje;

public class TieneEnojo {
    public Personaje personaje;
    public Personaje personajeEnojado;

    public TieneEnojo() {
    }

    public TieneEnojo(Personaje personaje, Personaje personajeEnojado) {
        this.personaje = personaje;
        this.personajeEnojado = personajeEnojado;
    }

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

	public Personaje getPersonajeEnojado() {
		return personajeEnojado;
	}

	public void setPersonajeEnojado(Personaje personajeEnojado) {
		this.personajeEnojado = personajeEnojado;
	}
    
    
}
