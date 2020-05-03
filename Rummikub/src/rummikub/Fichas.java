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
public class Fichas {
    
    public ArrayList<String> obtenerMazo() {
        ArrayList<String> fichas = new ArrayList<String>();
        fichas.add("C-0");
        for (int i = 1; i <= 13; i++) {
            fichas.add("A-" + i);
            fichas.add("A-" + i);
            fichas.add("R-" + i);
            fichas.add("R-" + i);
            fichas.add("N-" + i);
            fichas.add("N-" + i);
            fichas.add("V-" + i);
            fichas.add("V-" + i);
        }
        fichas.add("C-0");
        return fichas;
    }
    public String llenarDeFichas1x1(ArrayList<String> jugador, ArrayList<String> fichas) {
        Random rn=new Random();
        jugador.add(fichas.remove(rn.obtenerRamdom(fichas.size())));
        return jugador.get(jugador.size() - 1);
    }
    public ArrayList<ArrayList<String>> llenarDeFichas(int numeroDeCartas, ArrayList<ArrayList<String>> players, ArrayList<String> fichas) {
        for (int i = 0; i < numeroDeCartas; i++) {
            for (int j = 0; j < players.size(); j++) {
                llenarDeFichas1x1(players.get(j), fichas);
            }
        }
        return players;
    }
    
    
}
