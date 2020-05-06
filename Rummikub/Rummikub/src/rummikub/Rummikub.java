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
        Sala sala=new Sala(mazo);
        
        //ACÁ SE RECIBEN LOS JUGADORES
        ArrayList<String> arregloNombres=new ArrayList<>();
        System.out.println("Esperando jugadores... ");
        int i=0;
        for (i = 0; i < 4; i++) {
            System.out.println("Nombre jugador #"+(i+1)+": ");
            String nombreJugador=sc.readLine();
            if (nombreJugador.equals("")) {
                break;
            }
            sala.addJugador(new Jugador(nombreJugador));
            arregloNombres.add(nombreJugador);
        }
        System.out.println("Hay "+(i)+" en la sala");
        
        //FIN
        //ACÁ INICIA LA SALA
        System.out.println("Iniciando juego...");
        sala.iniciarJuego();
        //FIN
        //ACÁ MUESTRA LOS MAZOS
        for (int j = 0; j < i; j++) {
            System.out.println("Baraja del jugador "+(j+1));
            System.out.println(sala.getJugadores(arregloNombres.get(j)).getBaraja().toString());
        }
        //FIN
        //ACÁ SE MUESTRA EL TABLERO
        System.out.println("Tablero");
        System.out.println(sala.tablero.getJugadas().toString());
        //FIN
        //ACÁ SE VA A AGREGAR ALGO AL TABLERO
        
        //FIN
        
        /*while (sala.getMazo().size()>0) {            
            System.out.println("Turno del jugador: ");
        }*/
//CREACIÓN DEL MAZO
        
        
        
        
        
        /*

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
            
            
        }*/
        
    }
}
