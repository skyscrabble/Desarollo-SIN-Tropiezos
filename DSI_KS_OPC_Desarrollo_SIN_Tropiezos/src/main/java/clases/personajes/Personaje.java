package clases.personajes;

import clases.Capacidad;
import clases.Objeto;
import clases.Ubicacion;

public abstract class Personaje {
	public String nombre;
	public String estado_libertad;
	public String estado_vida;
	public Ubicacion ubicacion_actual;
	
	// Todas estas modeladas como clases por eficiencia en DROOLS
	
	//public boolean es_hijo_o_hija;
	//public boolean es_padre_o_madre;
	//public Objeto posee_objeto;
	//public Capacidad tiene_capacidad;
	//ublic Personaje tiene_enojo;
	//public Personaje tiene_favor;
	
}
