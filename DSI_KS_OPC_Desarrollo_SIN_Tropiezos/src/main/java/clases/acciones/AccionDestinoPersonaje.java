package clases.acciones;

import clases.personajes.Personaje;

// Clase para modelar el doble tipo posible de atributo destino en Accion
public class AccionDestinoPersonaje {
    public Accion accion;
    public Personaje destino;

    public AccionDestinoPersonaje() {
    }

    public AccionDestinoPersonaje(Accion accion, Personaje destino) {
        this.accion = accion;
        this.destino = destino;
    }

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	public Personaje getDestino() {
		return destino;
	}

	public void setDestino(Personaje destino) {
		this.destino = destino;
	}
    
    
}
