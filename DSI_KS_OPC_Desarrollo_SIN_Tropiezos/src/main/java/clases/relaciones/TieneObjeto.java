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

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}
    
    
}
