/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import org.java_websocket.WebSocket;

/**
 *
 * @author jair-
 */
public class Sala {

    private Hashtable<String, Jugador> jugadores = new Hashtable<String, Jugador>();
    private Tablero tablero = new Tablero();
    private ArrayList<Carta> mazo;

    public Sala(ArrayList<Carta> mazo) {
        super();
        this.mazo = mazo;
    }

    public Carta getCarta() {
        Random rd = new Random();
        int index = rd.nextInt(mazo.size());
        return mazo.remove(index);
    }

    public void iniciarJuego() {
        jugadores.forEach((nombre, jugador) -> {
            for (int i = 0; i < 14; i++) {
                jugador.addCarta(getCarta());
            }
        });
    }

    public Jugador getJugadorBySocket(WebSocket webSocketClient) {
        for (Map.Entry<String, Jugador> entry : jugadores.entrySet()) {
            Jugador jugador = entry.getValue();
            if (jugador.getConn().equals(webSocketClient)) {
                return jugador;
            }
        }
        return null;
    }

    public boolean isPlayerExist(String nombre) {
        return jugadores.containsKey(nombre);
    }

    public Jugador getJugador(String nombre) {
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

    public Hashtable<String, Jugador> getJugadores() {
        return jugadores;
    }

}
