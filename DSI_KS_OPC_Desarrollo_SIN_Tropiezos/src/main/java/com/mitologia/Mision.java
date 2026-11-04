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

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Personaje getSujeto() {
		return sujeto;
	}

	public void setSujeto(Personaje sujeto) {
		this.sujeto = sujeto;
	}

	public Personaje getObjetivoPersonaje() {
		return objetivoPersonaje;
	}

	public void setObjetivoPersonaje(Personaje objetivoPersonaje) {
		this.objetivoPersonaje = objetivoPersonaje;
	}

	public Objeto getObjetivoObjeto() {
		return objetivoObjeto;
	}

	public void setObjetivoObjeto(Objeto objetivoObjeto) {
		this.objetivoObjeto = objetivoObjeto;
	}

	public Capacidad getObjetivoCapacidad() {
		return objetivoCapacidad;
	}

	public void setObjetivoCapacidad(Capacidad objetivoCapacidad) {
		this.objetivoCapacidad = objetivoCapacidad;
	}

	public boolean isAlcanzada() {
		return alcanzada;
	}

	public void setAlcanzada(boolean alcanzada) {
		this.alcanzada = alcanzada;
	}

	public boolean isImposible() {
		return imposible;
	}

	public void setImposible(boolean imposible) {
		this.imposible = imposible;
	}
    
    
}
