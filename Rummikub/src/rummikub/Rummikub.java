/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author jair-
 */
public class Rummikub {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ArrayList<String> prueba = new ArrayList<>();
        prueba.add("A-3");
        prueba.add("A-4");
        prueba.add("A-5");
        prueba.add("A-6");
        prueba.add("A-7");
        prueba.add("C-0");
        prueba.add("C-0");

        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        Fichas fichas = new Fichas();
        Jugadores jugadores = new Jugadores();
        Escalera escalera = new Escalera();

        escalera.organizarPorEscalera(prueba);
        System.out.println(prueba.toString());
        System.out.println(escalera.validarEscalera(prueba));
        //acá se obtiene el mazo
        ArrayList<String> mazo = fichas.obtenerMazo();
        System.out.println(mazo.toString());
        //Cuantas personas van a jugar?
        int quantityPlayers = jugadores.numeroJugadores();
        //Se crean los jugadores
        ArrayList<ArrayList<String>> players = new ArrayList<ArrayList<String>>();
        jugadores.crearJugadores(players, quantityPlayers);
        System.out.println("Número de jugadores creados: " + players.size());
        //Se agregan las fichas a los jugadores
        fichas.llenarDeFichas(14, players, mazo);
        System.out.println("Fichas de cada jugador: ");
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Fichas de jugador " + (i + 1) + ": " + players.get(i).toString());
        }
        for (int i = 0; i < players.size(); i++) {
            escalera.organizarPorEscalera(players.get(i));
            System.out.println("Fichas de jugador " + (i + 1) + ": " + players.get(i).toString());
        }

        ArrayList<ArrayList<String>> tablero = new ArrayList();

        ArrayList<String> div = new ArrayList<>();
        div.add("A-3");
        div.add("C-0");
        div.add("C-0");
        ArrayList<String> div2 = new ArrayList<>();
        div2.add("A-1");
        div2.add("A-2");
        div2.add("A-3");

        ArrayList<String> div3 = new ArrayList<>();
        div3.add("A-1");
        div3.add("A-2");
        div3.add("A-3");

        ArrayList<String> div4 = new ArrayList<>();
        div4.add("A-1");
        div4.add("A-2");
        div4.add("A-3");

        ArrayList<String> div5 = new ArrayList<>();
        div5.add("A-1");
        div5.add("A-2");
        div5.add("A-3");

        ArrayList<String> div6 = new ArrayList<>();
        div6.add("A-1");
        div6.add("C-0");
        div6.add("A-3");
        tablero.add(div);
        tablero.add(div2);
        tablero.add(div3);
        tablero.add(div4);
        tablero.add(div5);
        tablero.add(div6);
        //System.out.println(Tablero.validarTablero(tablero));
        //System.out.println(Tablero.puntosTablero(tablero));

    }
}
