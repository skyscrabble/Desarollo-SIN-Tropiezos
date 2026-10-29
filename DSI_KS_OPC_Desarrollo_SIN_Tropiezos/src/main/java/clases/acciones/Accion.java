package clases.acciones;

import clases.personajes.Personaje;

public abstract class Accion {
	/* 
	 * Tener dos clases distintas segun el destino es la 
	 * mejor manera de hacerlo aunque tambien podria hacerse
	 * con dos atributos separados de cada tipo correspondiente
	 */
	public Personaje sujeto;
}
