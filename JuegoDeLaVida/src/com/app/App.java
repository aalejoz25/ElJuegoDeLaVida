package com.app;

import com.model.Juego;

public class App {

    public static void main(String[] args) {
        Juego miJuego = new Juego();
        System.out.println("El Juego de la Vida\n");

        try {
            miJuego.configurar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
