/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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

    private boolean validarTablero(ArrayList<Jugada> jugadas) {
        for (Jugada jugada : jugadas) {
            if (!jugada.validarJugada()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean validarTablero() {
        return validarTablero(this.jugadas);
    }

    private boolean validarNuevoTablero(ArrayList<Jugada> jugadas, Jugador jugador) {

        if (!jugador.isPrimeraJugada()) {
            int puntaje = 0;
            int i = 0;
            for (i = 0; i < this.jugadas.size(); i++) {;
                int index = jugadas.indexOf(this.jugadas.get(i));
                if (index == -1) {
                    return false;
                }
                jugadas.remove(index);
            }

            for (Jugada jugada : jugadas) {
                if (!jugada.validarJugada()) {
                    return false;
                }
                puntaje += jugada.getPuntaje();
            }
            if (puntaje < 30) {
                return false;
            }
            jugador.setPrimeraJugada(true);
            jugadas.addAll(this.jugadas);
        }
        return validarTablero(jugadas);

    }

    public void actualizarTablero(ArrayList<Jugada> jugadas, Jugador jugador) {
        if (validarNuevoTablero(jugadas, jugador)) {
            this.jugadas = jugadas;
        }
    }

    public ArrayList<Jugada> getJugadas() {
        return jugadas;
    }

}
