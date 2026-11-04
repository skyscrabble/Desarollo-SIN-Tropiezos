package clases.acciones;

import clases.Objeto;

//Clase para modelar el doble tipo posible de atributo destino en Accion
public class AccionDestinoObjeto {
    public Accion accion;
    public Objeto destino;

    public AccionDestinoObjeto() {
    }

    public AccionDestinoObjeto(Accion accion, Objeto destino) {
        this.accion = accion;
        this.destino = destino;
    }

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	public Objeto getDestino() {
		return destino;
	}

	public void setDestino(Objeto destino) {
		this.destino = destino;
	}
    
    
}
