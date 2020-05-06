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
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        //CREACIÓN DEL MAZO
        ArrayList<Carta> mazo = new ArrayList<Carta>();
        mazo.add(new Carta(0, "Red-Face"));
        for (int i = 1; i <= 13; i++) {
            mazo.add(new Carta(i, "Blue"));
            mazo.add(new Carta(i, "Blue"));

            mazo.add(new Carta(i, "Red"));
            mazo.add(new Carta(i, "Red"));

            mazo.add(new Carta(i, "Black"));
            mazo.add(new Carta(i, "Black"));

            mazo.add(new Carta(i, "Yellow"));
            mazo.add(new Carta(i, "Yellow"));

        }
        mazo.add(new Carta(0, "Black-Face"));

        System.out.println("Por favor añada el número de jugadores: ");
        
        ArrayList<Jugador> jugadoresJuego=new ArrayList<>();
        
        int jugadores = Integer.parseInt(sc.readLine());
        for (int i = 0; i < jugadores; i++) {
            ArrayList<Carta> baraja = new ArrayList<>();
            llenarDeFichas(14, baraja, mazo);
            System.out.println("Nombre jugador #"+(i+1)+": ");
            Jugador jugador = new Jugador(sc.readLine(), baraja);
            jugadoresJuego.add(jugador);
        }
        int opcionMenu=-1;
        while (opcionMenu!=0) {            
            System.out.println("BIENVENIDO\nseleccione un jugador: ");
            for (int i = 0; i < jugadoresJuego.size(); i++) {
                System.out.println("Jugador #"+(i+1)+": "+jugadoresJuego.get(i).getNombre());
            }
            System.out.println("Salir #0");
            opcionMenu=Integer.parseInt(sc.readLine());
            if (opcionMenu==0 || opcionMenu>jugadoresJuego.size()) {
                continue;
            }
            System.out.println("1. Añadir carta a mazo");
            System.out.println("2. Crear Jugada");
            System.out.println("0. Volver");
            
            
            //int 
            //System.out.println(""); 
            
            
            
        }
        
        
        
/*
        ArrayList<Carta> baraja1 = new ArrayList<>();
        ArrayList<Carta> baraja2 = new ArrayList<>();
        llenarDeFichas(14, baraja1, mazo);
        llenarDeFichas(14, baraja2, mazo);
        Jugador jugador = new Jugador("Ánderson", baraja1);
        Jugador jugadorPro = new Jugador("Jair", baraja2);
        ArrayList<Carta> baraja3 = new ArrayList<>();

        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(11, "Azul"));
        baraja3.add(new Carta(0, "Comodin"));
        baraja3.add(new Carta(13, "Azul"));
        Jugada jugada = new Jugada(baraja3);
        System.out.println("Vale?: " + jugada.validarJugada() + " \nPuntaje: " + jugada.getPuntaje());
        
        Tablero tablero = new Tablero(new ArrayList<Jugada>());
        ArrayList<Jugada> jugada1 = new ArrayList<>();
        
        ArrayList<Carta> ju1 = new ArrayList<>();
        ju1.add(new Carta(0, "Comodin"));
        ju1.add(new Carta(0, "Comodin"));
        ju1.add(new Carta(9, "Azul"));
        jugada1.add(new Jugada(ju1));
        
        ArrayList<Carta> ju2 = new ArrayList<>();
        ju2.add(new Carta(11, "Verde"));
        ju2.add(new Carta(12, "Verde"));
        ju2.add(new Carta(13, "Verde"));
        jugada1.add(new Jugada(ju2));
        
        Tablero tableroPrueba = new Tablero(jugada1);
        System.out.println("Tablero: " + tablero.getJugadas());
        tablero.actualizarTablero(tableroPrueba, jugador);
        System.out.println("new Tablero: " + tablero.getJugadas());
        System.out.println("");
        
        ArrayList<Jugada> jugada2 = new ArrayList<>();
        ArrayList<Carta> ju5 = new ArrayList<>();
        ju5.add(new Carta(9, "Verde"));
        ju5.add(new Carta(10, "Verde"));
        ju5.add(new Carta(0, "Comodin"));
        jugada2.add(new Jugada(ju1));
        jugada2.add(new Jugada(ju2));
        jugada2.add(new Jugada(ju5));
        tableroPrueba = new Tablero(jugada2);
        System.out.println("Tablero: " + tablero.getJugadas());
        tablero.actualizarTablero(tableroPrueba, jugadorPro);
        System.out.println("new Tablero: " + tablero.getJugadas());
        
        ArrayList<Jugada> jugada3 = new ArrayList<>();
        ArrayList<Carta> ju6 = new ArrayList<>();
        ju6.add(new Carta(9, "Verde"));
        ju6.add(new Carta(10, "Verde"));
        ju6.add(new Carta(0, "Comodin"));
        jugada3.add(new Jugada(ju6));
        tableroPrueba = new Tablero(jugada3);
        System.out.println("Tablero: " + tablero.getJugadas());
        tablero.actualizarTablero(tableroPrueba, jugadorPro);
        System.out.println("new Tablero: " + tablero.getJugadas());
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
