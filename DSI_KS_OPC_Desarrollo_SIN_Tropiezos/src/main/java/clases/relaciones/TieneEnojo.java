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
}
