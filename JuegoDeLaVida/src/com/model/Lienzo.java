package com.model;

import com.util.Consola;

import java.util.Random;

/**
 * Unico de su tipo en el juego, solo hay un tablero en _todo el ciclo de vida del juego.
 * Contiene un arreglo bidimensional de celdas y metodos para definir y aplicar acciones a las mismas,
 * así como toda la informacion sobre numero de filas/columnas, numero de generaciones,
 * generacion actual y numero de organismos inicales
 */
class Lienzo {
    private final Celula[][] celulas;
    private final int filas;
    private final int columnas;
    private final int numeroDeGeneraciones;
    private final int numeroDeOrganismosIniciales;
    private int generacion = 1;

    /**
     * Construye el tablero con toda la informacion necesaria
     *
     * @param numeroDeFilas        número de filas del tablero
     * @param numeroDeColumnas     número de columnas del tablero
     * @param numGeneraciones número de generaciones del juego
     * @param porcentajeDeOrganismos   porcentaje de organismos vivos con respecto al numero de celdas del tablero
     */
    public Lienzo(int numeroDeFilas, int numeroDeColumnas, int numGeneraciones, int porcentajeDeOrganismos) {
        filas = numeroDeFilas;
        columnas = numeroDeColumnas;
        int numeroDeCeldas = filas * columnas;

        celulas = new Celula[numeroDeFilas][numeroDeColumnas];
        numeroDeGeneraciones = numGeneraciones;
        numeroDeOrganismosIniciales = Math.round((porcentajeDeOrganismos * filas * columnas) / 100f);

        for (int f = 0; f < celulas.length; f++) {
            for (int c = 0; c < celulas[f].length; c++) {
                celulas[f][c] = new Celula();
            }
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("Tablero inicializado con " +
                filas + " filas y " + columnas + " columnas = " +
                numeroDeCeldas + " celdas");

        System.out.println("Cantidad de organismos iniciales = " +
                numeroDeCeldas + " x " + porcentajeDeOrganismos +
                " % " + " = " + (porcentajeDeOrganismos * filas * columnas) / 100f
                + " -> " + numeroDeOrganismosIniciales);
        System.out.println("---------------------------------------------------------");
    }

    int getNumeroDeFilas() {
        return filas;
    }

    int getNumeroDeColumnas() {
        return columnas;
    }

    int getGeneracionActual() {
        return generacion;
    }

    int getNumeroDeGeneraciones() {
        return numeroDeGeneraciones;
    }

    int getNumeroDeOrganismosIniciales() {
        return numeroDeOrganismosIniciales;
    }

    Celula[][] getCelulas() {
        return celulas;
    }

    /**
     * Define la accion de una celda especifica basado en el numero de vecinos y el estado actual
     *
     * @param numVecinos numero de vecinos de la celda
     * @param organismo  estado actual de la celda
     */
    private AccionDeCelula definirAccionParaCeldas(int numVecinos, boolean organismo) {

        //todo si esta vivo y (vecines<2 or >=4)
        //todo si esta no vivo y vecinos ==3
        if (organismo) {
            if (numVecinos < 2) return AccionDeCelula.Eliminar;
            else if (numVecinos == 2 || numVecinos == 3) return AccionDeCelula.Ninguna;
            else return AccionDeCelula.Eliminar; //Vecinos > 3
        } else if (numVecinos == 3) return AccionDeCelula.Agregar;
        return AccionDeCelula.Ninguna;
    }

    /**
     * Obtiene el numero de vecinos para una celda[x][y]
     *
     * @param x fila
     * @param y columna
     * @return AccionDeCelda
     */
    /**
     * Obtiene el numero de vecinos para una celda[x][y]
     * Genera Excepciones durante la ejecución
     *
     * @param x fila
     * @param y columna
     * @return AccionDeCelda
     */
    private int contarVecinosParaCeldaExcepciones(int x, int y) {

        byte vecinos = 0;
        for (int f = -1; f <= 1; f++) {
            for (int c = -1; c <= 1; c++) {
                if (f == 0 && c == 0) continue; //todo poner al final y si la celda esta vivi restar 1
                try {
                    if (celulas[x + f][y + c].getEstado()) vecinos++;
                } catch (Exception e) {
                }
            }
        }

        return vecinos;
    }

    /**
     * Actualiza las acciones en cada una de las celdas del tablero
     *
     * @return void
     */
    public void calcularAcciones() {
        for (byte f = 0; f < celulas.length; f++) {
            for (byte c = 0; c < celulas[f].length; c++) {
                int vecinos = contarVecinosParaCeldaExcepciones(f, c);
                AccionDeCelula accion = definirAccionParaCeldas(vecinos, celulas[f][c].getEstado());
                celulas[f][c].setAccion(accion);
            }
        }

    }

    /**
     * Aplica la ultima accion en cada una de las celdas
     *
     * @return void
     */
    public void aplicarAcciones() {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (celulas[f][c].getAccion() == AccionDeCelula.Agregar) celulas[f][c].setEstado(true);
                if (celulas[f][c].getAccion() == AccionDeCelula.Eliminar) celulas[f][c].setEstado(false);
            }
        }
        generacion++;
    }

    /**
     * Imprime el tablero en la consola.
     * Muestra las acciones de celdas si la funcion está activa
     *
     * @return void
     */
    public void mostrarCeldas() {
        System.out.println("Tablero Generacion " + generacion);
        System.out.print(" ");
        for (int c = 0; c < columnas; c++) {
            System.out.print(" ");
            System.out.print(c);
        }
        System.out.print(" ");

        for (int f = 0; f < celulas.length; f++) {
            System.out.print("\n" + f);
            for (int c = 0; c < celulas[f].length; c++) {
                System.out.print("|");
                String caracter;
                String color;
                if (celulas[f][c].getEstado()) {
                    caracter = "*";
                    if (Juego.getMarcar()) {
                        color = (celulas[f][c].getAccion() == AccionDeCelula.Eliminar) ? Consola.Color.RED : Consola.Color.BLUE;
                    } else color = Consola.Color.BLUE;
                } else {
                    if (Juego.getMarcar()) {
                        caracter = (celulas[f][c].getAccion() == AccionDeCelula.Agregar) ? "¤" : " ";
                    } else caracter = " ";
                    color = Consola.Color.WHITE;
                }
                System.out.print(color + caracter + Consola.Color.RESET);
            }
            System.out.print("|");
        }
        System.out.println();
    }

    /**
     * Genera organismos iniciales en celdas aleatorias que no tengan organismos vivos
     *
     * @return void
     */
    public void generarOrganismosRandom() {
        Random random = new Random();
        for (int i = 0; i < numeroDeOrganismosIniciales; i++) {
            int fil;
            int col;
            do {
                fil = random.nextInt(filas);
                col = random.nextInt(columnas);
            } while (celulas[fil][col].getEstado());
            celulas[fil][col].setEstado(true);
        }
    }

    /**
     * Regresa el numero de organismos total del tablero en la generación actual.
     * Si el numero es cero el juego debe terminar
     *
     * @return int
     */
    public int numeroDeOrganismos() {
        int numeroDeOrganismos = 0;
        for (byte f = 0; f < filas; f++) {
            for (byte c = 0; c < columnas; c++) {
                if (celulas[f][c].getEstado()) numeroDeOrganismos++;
            }
        }
        return numeroDeOrganismos;
    }

    /**
     * Regresa el numero de acciones total de las celdas de tipo agregar o eliminar
     * Si el numero es false el juego debe terminar
     *
     * @return boolean
     */
    public boolean hayAcciones() {
        for (byte f = 0; f < filas; f++) {
            for (byte c = 0; c < columnas; c++) {
                if (celulas[f][c].getAccion() != AccionDeCelula.Ninguna) return true;
            }
        }
        return false;
    }
}