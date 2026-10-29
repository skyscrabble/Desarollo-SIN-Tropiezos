package clases.relaciones;

import clases.Objeto;
import clases.personajes.Personaje;

public class TieneObjeto {
    public Personaje personaje;
    public Objeto objeto;

    public TieneObjeto() {
    }

    public TieneObjeto(Personaje personaje, Objeto objeto) {
        this.personaje = personaje;
        this.objeto = objeto;
    }
}
