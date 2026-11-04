package clases.personajes;

import clases.Ubicacion;

public abstract class Personaje {
	public String nombre;
	public boolean estado_libertad;
	public boolean estado_vida;
	public Ubicacion ubicacion_actual;
	
	// Todas estas modeladas como clases por eficiencia en DROOLS
	
	//public Personaje es_hijo_o_hija;
	//public Personaje es_padre_o_madre;
	//public Objeto posee_objeto;
	//public Capacidad tiene_capacidad;
	//ublic Personaje tiene_enojo;
	//public Personaje tiene_favor;
	
	protected Personaje() {
    }

	protected Personaje(String nombre, boolean estado_libertad, boolean estado_vida) {
        this.nombre = nombre;
        this.estado_libertad = estado_libertad;
        this.estado_vida = estado_vida;
    }

	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isEstado_libertad() {
		return estado_libertad;
	}

	public void setEstado_libertad(boolean estado_libertad) {
		this.estado_libertad = estado_libertad;
	}

	public boolean isEstado_vida() {
		return estado_vida;
	}

	public void setEstado_vida(boolean estado_vida) {
		this.estado_vida = estado_vida;
	}

	public Ubicacion getUbicacion_actual() {
		return ubicacion_actual;
	}

	public void setUbicacion_actual(Ubicacion ubicacion_actual) {
		this.ubicacion_actual = ubicacion_actual;
	}
	
	
}
