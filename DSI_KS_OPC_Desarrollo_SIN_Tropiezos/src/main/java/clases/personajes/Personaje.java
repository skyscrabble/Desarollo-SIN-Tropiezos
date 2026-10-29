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
	
}
