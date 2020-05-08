/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

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

    private String validarNuevoTablero(ArrayList<Jugada> jugadas, Jugador jugador) {
        ArrayList<Jugada> jugadasBackup = (ArrayList<Jugada>) jugadas.clone();
        if (!jugador.isPrimeraJugada()) {
            int puntaje = 0;
            int i = 0;
            for (i = 0; i < this.jugadas.size(); i++) {;
                int index = jugadas.indexOf(this.jugadas.get(i));
                if (index == -1) {
                    return "invalid Board";
                }
                jugadas.remove(index);
            }

            for (Jugada jugada : jugadas) {
                if (!jugada.validarJugada()) {
                    return "invalid Board";
                }
                puntaje += jugada.getPuntaje();
            }
            if (puntaje < 30) {
                return "Score isn't enough";
            }
            jugador.setPrimeraJugada(true);
            jugadas.addAll(this.jugadas);
        }
        if (validarTablero(jugadas)) {
            this.jugadas = jugadasBackup;
            return "20O OK";
        }
        return "invalid Board";

    }

    public String actualizarTablero(JSONArray jsonJugadas, Jugador jugador) {
        ArrayList<Jugada> jugadas = new ArrayList<>();
        for (int i = 0; i < jsonJugadas.length(); i++) {
            JSONObject jsonJugada = new JSONObject(jsonJugadas.get(i).toString());
            JSONArray jsonCartas = jsonJugada.getJSONArray("cards");
            jugadas.add(new Jugada(jsonCartas));
        }
        return validarNuevoTablero(jugadas, jugador);
    }

    public ArrayList<Jugada> getJugadas() {
        return jugadas;
    }

}
