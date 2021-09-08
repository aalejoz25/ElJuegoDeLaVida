package com.model;

import java.util.Scanner;

public class Juego {
    private static Lienzo lienzo;


    public void configurarParametros() {
        int filas = 20;
        int columnas = 20;

        System.out.print("Numero de celulas iniciales (max " + filas * columnas + "): ");
        int numeroDeCelulasIniciales = leerEntero();
        if (numeroDeCelulasIniciales < 1) numeroDeCelulasIniciales = 1;
        else if (numeroDeCelulasIniciales > filas * columnas) {
            numeroDeCelulasIniciales = 5;
            System.out.println("Se ha establecido el numero de celulas iniciales en 5");
        }
        System.out.print("Desea generar las celulas en posiciones aleatorias (Sí: true , No: false): ");
        boolean organismosRandom = leerBooleano();
        System.out.print("Digite un caracter con el que desea representar la celula: ");
        String representacion = leerString();
        lienzo = new Lienzo(filas, columnas, numeroDeCelulasIniciales, representacion);
        if (organismosRandom) lienzo.generarOrganismosRandom();
        else configurarCoordenadasDeCelulasIniciales();
        bucleDeEjecucion();
    }

    private static void bucleDeEjecucion() {
        Scanner sc = new Scanner(System.in);
        lienzo.calcularAcciones();
        lienzo.mostrarCelulas();
        do {
            lienzo.aplicarAcciones();
            lienzo.calcularAcciones();
            lienzo.mostrarCelulas();
            if (!lienzo.hayAcciones()) break; //Ya no hay cambios en generaciones siguientes
            if (lienzo.numeroDeOrganismos() == 0) break; //Ya no hay organismos
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("error" + e);
            }
        } while (lienzo.hayAcciones() == true);
        int numeroDeOrganismos = lienzo.numeroDeOrganismos();
        if (numeroDeOrganismos > 0)
            System.out.println("No hay mas movimientos Finalizado con " + numeroDeOrganismos + " organismos");
        else System.out.println("Ya no hay organismos");
        System.out.println("Finalizado");
    }


    private static void configurarCoordenadasDeCelulasIniciales() {
        for (int i = 1; i <= lienzo.getNumeroDeCelulasVivasIniciales(); i++) {

            int x = 0;
            int y = 0;

            do {
                System.out.println("Celula " + i);
                System.out.print(" x = ");

                try {
                    x = leerEntero();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (x >= lienzo.getNumeroDeFilas()) {
                    x = lienzo.getNumeroDeFilas() - 1;
                    System.out.println("x -> " + x);
                } else if (x < 0) {
                    x = 0;
                    System.out.println("x -> " + x);
                }

                System.out.print(" y = ");
                try {
                    y = leerEntero();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                if (y >= lienzo.getNumeroDeColumnas()) {
                    y = lienzo.getNumeroDeColumnas() - 1;
                    System.out.println("y -> " + y);
                } else if (y < 0) {
                    y = 0;
                    System.out.println("y -> " + y);
                }

                if (lienzo.getCelulas()[x][y].getEstado())
                    System.out.println("La celda [" + x + "][" + y + "] ya tiene celula.\nReiniciando proceso para celula inicial " + i);
            } while (lienzo.getCelulas()[x][y].getEstado());

            lienzo.getCelulas()[x][y].setEstado(true);
        }
    }


    private static int leerEntero() {
        Scanner sc = new Scanner(System.in);
        int i = 0;
        try {
            i = sc.nextInt();
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }
        return i;
    }

    private static String leerString() {
        Scanner sc = new Scanner(System.in);
        String caracter = "•";
        try {
            caracter = sc.nextLine();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return caracter;
    }


    private static boolean leerBooleano() {
        Scanner sc = new Scanner(System.in);
        boolean b = true;
        try {
            b = sc.nextBoolean();
        } catch (Exception e) {
            System.out.println("Error de entrada -> se usa Default (true)");
        }
        return b;
    }


}
