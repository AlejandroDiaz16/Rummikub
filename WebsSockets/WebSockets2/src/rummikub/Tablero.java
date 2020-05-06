/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub;

import java.util.ArrayList;

/**
 *
 * @author jair-
 */
public class Tablero {

    private ArrayList<Jugada> jugadas = new ArrayList<>();

    public Tablero(ArrayList<Jugada> jugadas) {
        this.jugadas = jugadas;
    }
    
    public Tablero() {
		super();
	}

	public boolean validarTablero() {
        for (Jugada jugada : jugadas) {
            if (!jugada.validarJugada()) {
                return false;
            }
        }
        return true;
    }

    private boolean validarNuevoTablero(Tablero nuevoTablero, Jugador jugador) {

        if (!jugador.isPrimeraJugada()) {
            int puntaje = 0;
            int i = 0;
            for (i = 0; i < this.jugadas.size(); i++) {;
                int index = nuevoTablero.jugadas.indexOf(this.jugadas.get(i));
                if (index == -1) {
                    return false;
                }
                nuevoTablero.jugadas.remove(index);
            }

            for (Jugada jugada : nuevoTablero.jugadas) {
                if (!jugada.validarJugada()) {
                    return false;
                }
                puntaje += jugada.getPuntaje();
            }
            if (puntaje < 30) {
                return false;
            }
            jugador.setPrimeraJugada(true);
            nuevoTablero.jugadas.addAll(this.jugadas);
        }
        return nuevoTablero.validarTablero();

    }

    public void actualizarTablero(Tablero nuevoTablero, Jugador jugador) {
        if (validarNuevoTablero(nuevoTablero, jugador)) {
            this.jugadas = nuevoTablero.jugadas;
        }
    }

    public ArrayList<Jugada> getJugadas() {
        return jugadas;
    }

}
