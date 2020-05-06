/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

/**
 *
 * @author jair-
 */
public class Sala {
    Hashtable<String, Jugador> jugadores = new Hashtable<String, Jugador>();
    Tablero tablero = new Tablero();
    ArrayList<Carta> mazo;
    
    public Sala(ArrayList<Carta> mazo) {
		super();
		this.mazo = mazo;
	}

	public Carta getCarta() {
    	Random rd = new Random();
    	int index = rd.nextInt(mazo.size());
        return mazo.get(index);
    }
    
    public void iniciarJuego() {
    	jugadores.forEach((nombre, jugador) -> {
    		for (int i = 0; i < 14; i++) {
				jugador.addCarta(getCarta());
			}
    	});
    }
    
    public Jugador getJugadores(String nombre) {
        return jugadores.get(nombre);
    }
    
    public void addJugador(Jugador jugador) {
    	jugadores.put(jugador.getNombre(), jugador);
    }

    public Tablero getTablero() {
        return tablero;
    }

    public ArrayList<Carta> getMazo() {
        return mazo;
    }
    

}
