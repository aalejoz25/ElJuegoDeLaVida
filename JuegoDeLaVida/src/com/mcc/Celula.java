package com.mcc;

/**
 * Elemento basico del tablero.
 * Cada celda define su estado presente y futuro mediante
 * una AccionDeCelda (estado siguiente generación)
 * y un booleano (estado actual / organismo )
 */
class Celula {
    private AccionDeCelula accion;
    private boolean estaViva;


    public Celula() {
        estaViva = false;
        accion = AccionDeCelula.Ninguna;
    }

    AccionDeCelula getAccion() { return accion; }
    void setAccion(AccionDeCelula accion) { this.accion = accion; }
    boolean getEstado() { return estaViva; }
    void setEstado(boolean estado) { this.estaViva = estado; }


}
