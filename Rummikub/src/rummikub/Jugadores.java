/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author jair-
 */
public class Jugadores {
    public int numeroJugadores() {
        System.out.println("BIENVENIDO\n2 jugadores\n3 jugadores\n4 jugadores\n...\n");
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int jugadores = 0;
        while (!(jugadores == 2 || jugadores == 4|| jugadores == 3)) {
            try {
                jugadores = Integer.parseInt(sc.readLine());
                if (!(jugadores == 2 || jugadores == 4|| jugadores == 3)) {
                    System.out.println("jugadores incorrectos");
                }else{
                    return jugadores;
                }
            } catch (Exception e) {
                System.out.println("Error");
                jugadores = 0;
            }
        }
        return jugadores;
    }
    
    public void crearJugadores(ArrayList<ArrayList<Carta>> players, int numeroJugadores){
        for (int i = 0; i < numeroJugadores; i++) {
            ArrayList<Carta> player = new ArrayList<Carta>();;
            players.add(player);
        }
        
    }
    
}
