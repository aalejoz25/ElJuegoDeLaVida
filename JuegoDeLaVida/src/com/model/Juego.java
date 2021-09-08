package com.model;

import com.util.Consola;

import java.util.Scanner;

public class Juego {
    private static Lienzo lienzo;


    /**
     * Funcion de entrada del programa
     * @param args arreglo String con los parámetros ingresados al inciar el programa
     */


    /**
     * Obtiene entrada del usuario referente al juego,
     * despues inicializa el tablero y configuraciones de juego,
     * finalmente inicia el Game Loop
     *
     * @return void
     */
    public void configurar() {
        //System.out.print("Numero de Filas : ");
        int filas = 30;
        //System.out.print("Numero de Columnas : ");
        int columnas = 30;
        System.out.print("Porcentaje de Organismos Iniciales (de 1 a 50) % : ");
        int porcentajeDeOrganismosIniciales = leerEntero();
        if (porcentajeDeOrganismosIniciales < 1) porcentajeDeOrganismosIniciales = 1;
        else if (porcentajeDeOrganismosIniciales > 50) porcentajeDeOrganismosIniciales = 50;
        System.out.print("Generar organismos iniciales en posiciones random Sí: true , No: false ");
        boolean organismosRandom = leerBooleano();

        lienzo = new Lienzo(filas, columnas, porcentajeDeOrganismosIniciales);
        if (organismosRandom) lienzo.generarOrganismosRandom();
        else configurarCoordenadasDeOrganismosIniciales();
        bucleDeEjecucion();
    }

    /**
     * El famoso GameLoop donde se ejecuta el juego de forma cíclica
     * hasta que se cumpla alguna condición de terminación
     *
     * @return void
     */
    private static void bucleDeEjecucion() {
        Scanner sc = new Scanner(System.in);
        lienzo.calcularAcciones();
        lienzo.mostrarCelulas();
        System.out.print("Esperando enter ->");
        sc.nextLine();
        do {
            lienzo.aplicarAcciones();
            lienzo.calcularAcciones();
            lienzo.mostrarCelulas();
            if (!lienzo.hayAcciones()) break; //Ya no hay cambios en generaciones siguientes
            if (lienzo.numeroDeOrganismos() == 0) break; //Ya no hay organismos
            System.out.print("Presione enter ->");
            sc.nextLine();
        } while (lienzo.hayAcciones() == true);
        int numeroDeOrganismos = lienzo.numeroDeOrganismos();
        if (numeroDeOrganismos > 0)
            System.out.println(Consola.Color.GREEN + "Juego Finalizado con " + numeroDeOrganismos + " organismos" + Consola.Color.RESET);
        else System.out.println(Consola.Color.RED + "Juego Perdido" + Consola.Color.RESET);
        System.out.println("Juego Terminado");
    }

    /**
     * Obtiene entrada de usuario de tipo entero
     *
     * @return int
     */
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

    /**
     * Obtiene entrada de usuario de tipo booleano
     *
     * @return boolean
     */
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

    /**
     * Obtiene entrada de usuario para definir las posiciones de los organismos iniciales en el tablero
     *
     * @return void
     */
    private static void configurarCoordenadasDeOrganismosIniciales() {
        for (int i = 1; i <= lienzo.getNumeroDeOrganismosIniciales(); i++) {

            int x = 0;
            int y = 0;

            do {
                System.out.println("Organismo " + i);
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
                    System.out.println("La celda [" + x + "][" + y + "] ya tien organismo.\nReiniciando proceso para organismo inicial " + i);
            } while (lienzo.getCelulas()[x][y].getEstado());

            lienzo.getCelulas()[x][y].setEstado(true);
        }
    }


}
