package clases.relaciones;

import clases.personajes.Personaje;

public class Parentesco {
    public Personaje padreOMadre;
    public Personaje hijo;

    public Parentesco() {
    }

    public Parentesco(Personaje padreOMadre, Personaje hijo) {
        this.padreOMadre = padreOMadre;
        this.hijo = hijo;
    }

	public Personaje getPadreOMadre() {
		return padreOMadre;
	}

	public void setPadreOMadre(Personaje padreOMadre) {
		this.padreOMadre = padreOMadre;
	}

	public Personaje getHijo() {
		return hijo;
	}

	public void setHijo(Personaje hijo) {
		this.hijo = hijo;
	}
    
    
}
