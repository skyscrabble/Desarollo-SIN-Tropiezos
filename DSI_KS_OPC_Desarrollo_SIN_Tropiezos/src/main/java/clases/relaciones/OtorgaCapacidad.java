package clases.relaciones;

import clases.Capacidad;
import clases.Objeto;

public class OtorgaCapacidad {
    public Objeto objeto;
    public Capacidad capacidad;

    public OtorgaCapacidad() {
    }

    public OtorgaCapacidad(Objeto objeto, Capacidad capacidad) {
        this.objeto = objeto;
        this.capacidad = capacidad;
    }

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public Capacidad getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Capacidad capacidad) {
		this.capacidad = capacidad;
	}
    
    
}
