package com.model;

import com.util.Consola;
import java.util.Random;

public class Lienzo {
    private final Celula[][] celulas;
    private final int filas;
    private final int columnas;
    private final int numeroDeCelulasVivasIniciales;
    private int generacion = 1;

    public Lienzo(int numeroDeFilas, int numeroDeColumnas, int numeroDeCelulasVivas, String representacionDeCelula) {
        filas = numeroDeFilas;
        columnas = numeroDeColumnas;
        int numeroDeCeldas = filas * columnas;
        celulas = new Celula[numeroDeFilas][numeroDeColumnas];

        numeroDeCelulasVivasIniciales = numeroDeCelulasVivas;

        for (int f = 0; f < celulas.length; f++) {
            for (int c = 0; c < celulas[f].length; c++) {
                celulas[f][c] = new Celula(representacionDeCelula);
            }
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("Tablero inicializado con " +
                filas + " filas y " + columnas + " columnas = " +
                numeroDeCeldas + " celdas");

        System.out.println("Cantidad de organismos iniciales = " + numeroDeCelulasVivas);
        System.out.println("---------------------------------------------------------");
    }

    int getNumeroDeFilas() {
        return filas;
    }

    int getNumeroDeColumnas() {
        return columnas;
    }


    int getNumeroDeCelulasVivasIniciales() {
        return numeroDeCelulasVivasIniciales;
    }

    Celula[][] getCelulas() {
        return celulas;
    }

    private AccionDeCelula definirAccionParaCelula(int numVecinos, boolean estaViva) {

        //si esta vivo y (vecinos<2 o >=4)
        //si esta no vivo y vecinos == 3
        if (estaViva) {
            if (numVecinos < 2) return AccionDeCelula.Eliminar;
            else if (numVecinos == 2 || numVecinos == 3) return AccionDeCelula.Ninguna;
            else return AccionDeCelula.Eliminar;
        } else if (numVecinos == 3) return AccionDeCelula.Agregar;
        return AccionDeCelula.Ninguna;
    }

    private int contarVecinosParaCelula(int x, int y) {

        byte vecinos = 0;
        for (int f = -1; f <= 1; f++) {
            for (int c = -1; c <= 1; c++) {
                if (f == 0 && c == 0) continue;
                try {
                    if (celulas[x + f][y + c].getEstado()) vecinos++;
                } catch (Exception e) {
                }
            }
        }

        return vecinos;
    }

    public void calcularAcciones() {
        for (byte f = 0; f < celulas.length; f++) {
            for (byte c = 0; c < celulas[f].length; c++) {
                int vecinos = contarVecinosParaCelula(f, c);
                AccionDeCelula accion = definirAccionParaCelula(vecinos, celulas[f][c].getEstado());
                celulas[f][c].setAccion(accion);
            }
        }

    }

    public void aplicarAcciones() {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (celulas[f][c].getAccion() == AccionDeCelula.Agregar) celulas[f][c].setEstado(true);
                if (celulas[f][c].getAccion() == AccionDeCelula.Eliminar) celulas[f][c].setEstado(false);
            }
        }
        generacion++;
    }

    public void mostrarCelulas() {
        System.out.print("\n");
        System.out.println("Lienzo Generacion " + generacion);
        System.out.print(" ");
        for (int c = 0; c < columnas; c++) {
            System.out.print(" ");
        }
        System.out.print(" ");

        for (int f = 0; f < celulas.length; f++) {
            System.out.print("\n");
            for (int c = 0; c < celulas[f].length; c++) {
                System.out.print("|");
                String caracter;
                String color;
                if (celulas[f][c].getEstado()) {
                    caracter = celulas[0][0].getRepresentacionGrafica();
                    color = Consola.Color.RED;
                } else {
                    caracter = " ";
                    color = Consola.Color.TRANSPARENTE;
                }
                System.out.print(color + caracter + Consola.Color.RESET);
            }
            System.out.print("|");
        }
        System.out.println();
    }

    public void generarOrganismosRandom() {
        Random random = new Random();
        for (int i = 0; i < numeroDeCelulasVivasIniciales; i++) {
            int fil;
            int col;
            do {
                fil = random.nextInt(filas);
                col = random.nextInt(columnas);
            } while (celulas[fil][col].getEstado());
            celulas[fil][col].setEstado(true);
        }
    }

    public int numeroDeOrganismos() {
        int numeroDeOrganismos = 0;
        for (byte f = 0; f < filas; f++) {
            for (byte c = 0; c < columnas; c++) {
                if (celulas[f][c].getEstado()) numeroDeOrganismos++;
            }
        }
        return numeroDeOrganismos;
    }

    public boolean hayAcciones() {
        for (byte f = 0; f < filas; f++) {
            for (byte c = 0; c < columnas; c++) {
                if (celulas[f][c].getAccion() != AccionDeCelula.Ninguna) return true;
            }
        }
        return false;
    }
}
