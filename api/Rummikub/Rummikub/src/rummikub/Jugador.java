/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub;

import java.util.ArrayList;
import java.util.Collections;
import org.java_websocket.WebSocket;

/**
 *
 * @author jair-
 */
public class Jugador {
    private String nombre;
    private boolean primeraJugada=false;
    private ArrayList<Carta> baraja=new ArrayList<>();
    private WebSocket conn;
    private boolean ready;
    
    public Jugador(String nombre, WebSocket conn) {
        this.nombre = nombre;
        this.conn = conn;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
    
    public void setConn(WebSocket conn) {
        this.conn = conn;
    }
    
    public WebSocket getConn() {
        return conn;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isPrimeraJugada() {
        return primeraJugada;
    }
    
    public void addCarta(Carta carta) {
    	baraja.add(carta);
    }
    
    public void setPrimeraJugada(boolean primeraJugada) {
        this.primeraJugada = primeraJugada;
    }
    
    public void ordenarPorEscalera() {
    	baraja.sort((Carta carta1, Carta carta2) -> {
    		int caso1 = carta1.getColor().compareToIgnoreCase(carta2.getColor());
    		if(caso1 == 0) {
    			Integer valorCarta1 = (Integer) carta1.getValor();
    			Integer valorCarta2 = (Integer) carta2.getValor();
    			return valorCarta1.compareTo(valorCarta2);
    		}else return caso1;
    	});
    }
    
    public void ordenarPorTrios() {
    	baraja.sort((Carta carta1, Carta carta2) -> {
    		Integer valorCarta1 = (Integer) carta1.getValor();
			Integer valorCarta2 = (Integer) carta2.getValor();
    		int caso1 = valorCarta1.compareTo(valorCarta2);
    		if(caso1 == 0) {
    			return carta1.getColor().compareToIgnoreCase(carta2.getColor());
    		}else return caso1;
    	});
    }

    public ArrayList<Carta> getBaraja() {
        return baraja;
    }
    

    
    
    
    
}
