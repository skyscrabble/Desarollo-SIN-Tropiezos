package clases.relaciones;

import clases.personajes.Dios;
import clases.personajes.Personaje;

public class TieneFavor {
    public Personaje personaje;
    public Dios dios;

    public TieneFavor() {
    }

    public TieneFavor(Personaje personaje, Dios dios) {
        this.personaje = personaje;
        this.dios = dios;
    }

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

	public Dios getDios() {
		return dios;
	}

	public void setDios(Dios dios) {
		this.dios = dios;
	}
    
    
}
