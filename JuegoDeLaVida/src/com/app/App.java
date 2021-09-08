package com.app;

import com.model.Juego;

public class App {

    public static void main(String[] args) {
        Juego miJuego = new Juego();
        System.out.println("Tecnologías de programación");
        System.out.println("Juego de la Vida de John H. Conway\n");

        try {
            miJuego.configurar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
