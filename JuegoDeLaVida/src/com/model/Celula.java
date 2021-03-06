package com.model;

public class Celula {
    private AccionDeCelula accion;
    private boolean estaViva;
    private String representacionGrafica;


    public Celula(String representacion) {
        estaViva = false;
        accion = AccionDeCelula.Ninguna;
        representacionGrafica = representacion;
    }

    AccionDeCelula getAccion() { return accion; }
    void setAccion(AccionDeCelula accion) { this.accion = accion; }
    boolean getEstado() { return estaViva; }
    void setEstado(boolean estado) { this.estaViva = estado; }
    String getRepresentacionGrafica() { return representacionGrafica; }

}
