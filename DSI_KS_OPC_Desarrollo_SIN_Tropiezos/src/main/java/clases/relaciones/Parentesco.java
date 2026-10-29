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
}
