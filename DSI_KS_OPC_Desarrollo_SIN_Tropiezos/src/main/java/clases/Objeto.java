package clases;

public class Objeto {
    public String nombre;
    public Ubicacion ubicacion_actual;

    public Objeto() {
    }

    public Objeto(String nombre) {
        this.nombre = nombre;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Ubicacion getUbicacion_actual() {
		return ubicacion_actual;
	}

	public void setUbicacion_actual(Ubicacion ubicacion_actual) {
		this.ubicacion_actual = ubicacion_actual;
	}
    
    
}
