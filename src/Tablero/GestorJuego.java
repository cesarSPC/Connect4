/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tablero;

import Observador.IObservador;
import java.awt.Color;
import java.util.Random;
import javax.swing.JLabel;

/**
 *
 * @author cesar
 */
public class GestorJuego implements IObservador {

    private int turnoActual;
    private GestorAcciones acciones;
    private Integer[][] fichas;

    public GestorJuego(GestorAcciones acciones) {
        Random r = new Random();
        fichas = new Integer[Tablero.FILAS][Tablero.COLUMNAS];

        // Se elige al azar amarillo o rojo de quien empieza el juego
        this.turnoActual = r.nextInt(Tablero.AMARILLA, Tablero.ROJA + 1);
        this.acciones = acciones;

    }

    @Override
    public void actualizar() {
        cambiarTurno();
        cambiarTurnoUI(acciones.getVista().labTurno);
    }

    public void cambiarTurnoUI(JLabel label) {
        switch (turnoActual) {
            case Tablero.AMARILLA -> {
                label.setText("AMARILLAS");
                label.setForeground(new Color(252, 227, 136));
            }
            case Tablero.ROJA -> {
                label.setText("ROJAS");
                label.setForeground(new Color(252, 121, 121));
            }
        }
    }

    public void cambiarTurno() {
        switch (turnoActual) {
            case Tablero.AMARILLA -> {
                turnoActual = Tablero.ROJA;
            }
            case Tablero.ROJA -> {
                turnoActual = Tablero.AMARILLA;
            }
        }
    }

    public boolean comprobarGanador(int fila, int columna, int tipo) {
        fichas[fila][columna] = tipo;

        int[][] direcciones = {
            {-1, 0}, // Abajo
            {0, 1}, // Derecha
            {0, -1}, // Izquierda
            {1, 1}, // Arriba a la derecha
            {-1, 1}, // Abajo a la derecha
            {-1, -1}, // Abajo a la izquierda
            {1, -1} // Arriba a la izquierda
        };

        // Recorrer cada dirección
        for (int[] direccion : direcciones) {
            int factorFila = direccion[0];
            int factorColumna = direccion[1];
            if (comprobarDireccion(fila, columna, tipo, factorFila, factorColumna)) {
                return true;
            }
        }

        return false;
    }

    private boolean comprobarDireccion(int fila, int columna, int tipo, int factorFila, int factorColumna) {
        int fichasEnFila = 0;

        // Comprobar del 1 al 3
        for (int i = 1; i < 4; i++) {
            try {
                // Cuando no hay ficha en la direccion
                if (fichas[fila + (factorFila * i)][columna + (factorColumna * i)] == null) {
                    break;
                }

                // Cuenta fichas en linea hasta llegar a 3
                if (fichas[fila + factorFila * i][columna + (factorColumna * i)] == tipo) {
                    fichasEnFila++;
                } else {
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
            
        }

        return fichasEnFila == 3;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

}