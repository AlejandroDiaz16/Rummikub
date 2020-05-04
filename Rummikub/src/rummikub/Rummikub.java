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
import java.util.Random;

/**
 *
 * @author jair-
 */
public class Rummikub {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        ArrayList<Carta> mazo = new ArrayList<Carta>();
        mazo.add(new Carta(0, "Comodin"));
        for (int i = 1; i <= 13; i++) {
            mazo.add(new Carta(i, "Azul"));
            mazo.add(new Carta(i, "Azul"));
            
            mazo.add(new Carta(i, "Rojo"));
            mazo.add(new Carta(i, "Rojo"));
            
            mazo.add(new Carta(i, "Negro"));
            mazo.add(new Carta(i, "Negro"));
            
            mazo.add(new Carta(i, "Verde"));
            mazo.add(new Carta(i, "Verde"));
            
        }
        mazo.add(new Carta(0, "Comodin"));
        ArrayList<Carta> baraja1=new ArrayList<>();
        ArrayList<Carta> baraja2=new ArrayList<>();
        llenarDeFichas(14, baraja1, mazo);
        llenarDeFichas(14, baraja2, mazo);
        Jugador jugador=new Jugador("Ánderson", baraja1);
        Jugador jugadorPro=new Jugador("Jair", baraja2);
        ArrayList<Carta> baraja3=new ArrayList<>();
        
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(11, "Azul"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(13, "Azul"));
        Jugada jugada=new Jugada(baraja3);
        System.out.println("Vale?: "+jugada.validarJugada()+" \nPuntaje: "+jugada.getPuntaje());
        Tablero tablero=new Tablero(new ArrayList<Jugada>());
        
        
        ArrayList<Jugada> jugar=new ArrayList<>();
        ArrayList<Carta> ju1=new ArrayList<>();
        ju1.add(new Carta(0, "Comodin"));
        ju1.add(new Carta(0, "Comodin"));
        ju1.add(new Carta(9, "Azul"));
        jugar.add(new Jugada(ju1));
        ArrayList<Carta> ju2=new ArrayList<>();
        ju2.add(new Carta(11, "Verde"));
        ju2.add(new Carta(12, "Verde"));
        ju2.add(new Carta(13, "Verde"));
        jugar.add(new Jugada(ju2));
        Tablero tableroPrueba=new Tablero(jugar);
        System.out.println("Tablero: "+tablero.getJugadas());
        tablero.actualizarTablero(tableroPrueba, jugador);
        System.out.println("new Tablero: "+tablero.getJugadas());
        
        ArrayList<Jugada> jugar5=new ArrayList<>();
        ArrayList<Carta> ju5=new ArrayList<>();
        ju5.add(new Carta(9, "Verde"));
        ju5.add(new Carta(10, "Verde"));
        ju5.add(new Carta(0, "Comodin"));
        jugar5.add(new Jugada(ju1));
        jugar5.add(new Jugada(ju2));
        jugar5.add(new Jugada(ju5));
        
        Tablero tableroPrueba2=new Tablero(jugar5);
        //System.out.println(tableroPrueba2.getJugadas());
        tablero.actualizarTablero(tableroPrueba2, jugadorPro);
        System.out.println("new Tablero: "+tablero.getJugadas());
        
        
        
        
        //tablero.actualizarTablero(,);
        /*ArrayList<Jugada> jugar2=new ArrayList<>();
        ArrayList<Carta> ju3=new ArrayList<>();
        ju3.add(new Carta(0, "Comodin"));
        ju3.add(new Carta(0, "Comodin"));
        ju3.add(new Carta(9, "Azul"));
        jugar2.add(new Jugada(ju3));
        ArrayList<Carta> ju4=new ArrayList<>();
        ju4.add(new Carta(11, "Verde"));
        ju4.add(new Carta(12, "Verde"));
        ju4.add(new Carta(13, "Verde"));
        jugar2.add(new Jugada(ju4));
        Tablero tableroPrueba2=new Tablero(jugar2);
        System.out.println("Tablero: "+tablero.getJugadas());
        tablero.actualizarTablero(tableroPrueba2, jugadorPro);
        System.out.println("new Tablero: "+tablero.getJugadas());
*/
    }
    
    
    public static void llenarDeFichas(int numeroDeCartas, ArrayList<Carta> player, ArrayList<Carta> fichas) {
        for (int i = 0; i < numeroDeCartas; i++) {
                llenarDeFichas1x1(player, fichas);
        }
    }
    
    public static void llenarDeFichas1x1(ArrayList<Carta> jugador, ArrayList<Carta> fichas) {
        Random rd = new Random();
        jugador.add(fichas.remove(rd.nextInt(fichas.size())));
    }
}
