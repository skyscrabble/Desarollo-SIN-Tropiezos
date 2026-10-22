package clases.acciones;

import clases.Objeto;
import clases.personajes.Personaje;

public abstract class Accion {
	public String nombre;
	// No sé si es la mejor manera de hacerlo
	public Personaje destino_personaje;
	public Objeto destino_objeto;
	public Personaje sujeto;
}
