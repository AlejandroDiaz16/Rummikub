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
public class Sala {
    ArrayList<Jugador> jugadores;
    Tablero tablero;
    ArrayList<Carta> mazo;

    public Sala(ArrayList<Jugador> jugadores, Tablero tablero, ArrayList<Carta> mazo) {
        this.jugadores = jugadores;
        this.tablero = tablero;
        this.mazo = mazo;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public ArrayList<Carta> getMazo() {
        return mazo;
    }
    

}
