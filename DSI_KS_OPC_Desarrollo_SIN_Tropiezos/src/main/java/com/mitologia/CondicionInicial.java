package com.mitologia;

public class CondicionInicial {
    public enum Tipo {
        FAVOR,
        ENOJO,
        POSEE,
        NO_POSEE,
        CAPACIDAD,
        PRESO,		// Solamente el estado inicial
        LIBRE,
        VIVO,
        MUERTO,
        APRESADO,	// Representacion de la accion con (victima, captor)
        LOCALIZA,
        //PARENTESCO Incluible si van a cambiar relaciones familiares en el mito, pero ya esta en BH
    }

    public Tipo tipo;
    public String primero;
    public String segundo;

    public CondicionInicial(Tipo tipo, String primero, String segundo) {
        this.tipo = tipo;
        this.primero = primero;
        this.segundo = segundo;
    }
}
