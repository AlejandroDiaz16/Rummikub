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
public class Jugador {
    private String nombre;
    private boolean primeraJugada=false;
    private ArrayList<Carta> baraja=new ArrayList<>();

    public Jugador(String nombre ,ArrayList<Carta> baraja) {
        this.nombre=nombre;
        this.baraja=baraja;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isPrimeraJugada() {
        return primeraJugada;
    }

    public void setPrimeraJugada(boolean primeraJugada) {
        this.primeraJugada = primeraJugada;
    }
    
    

    
    
    
    
}
