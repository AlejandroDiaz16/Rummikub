/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author jair-
 */
public class Jugada {

    private ArrayList<Carta> cartas = new ArrayList<>();
    private int puntaje = 0;

    public Jugada(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    private boolean validarEscalera() {
        puntaje=0;
        int valorPrimal = Integer.MAX_VALUE;
        String colorPrimal = "";
        if (this.cartas.size() < 3 || this.cartas.size() > 13) {
            return false;
        }
        for (int i = 0; i < cartas.size(); i++) {
            Carta carta = cartas.get(i);
            if (carta.getValor() == 0) {
                continue;
            }
            colorPrimal = carta.getColor();
            valorPrimal = carta.getValor() - (i);
            break;
        }
        if (valorPrimal<1) {
            return false;
        }
        for (Carta carta : cartas) {
            if (carta.getValor() != 0 && (!carta.getColor().equals(colorPrimal) || carta.getValor() != valorPrimal)) {
                return false;
            }
            puntaje+=valorPrimal;
            valorPrimal++;
        }
        if (valorPrimal>14) {
            return false;
        }
        return true;
    }

    private boolean validarTrio() {
        puntaje=0;
        HashSet<String> colores = new HashSet<>();
        Carta cartaAnterior = null;
        if (this.cartas.size() < 3 || this.cartas.size() > 4) {
            return false;
        }
        for (Carta carta : cartas) {
            if (carta.getValor() == 0) {
                continue;
            }
            if (cartaAnterior != null && (cartaAnterior.getValor() != carta.getValor() || colores.contains(carta.getColor()))) {
                return false;
            }
            colores.add(carta.getColor());
            cartaAnterior = carta;
        }
        puntaje=cartaAnterior.getValor()*cartas.size();
        return true;
    }


    public boolean validarJugada() {
        return validarEscalera() || validarTrio();
    }

    public int getPuntaje() {
        return puntaje;
    }

    @Override
    public String toString() {
        return "Jugada{" + "cartas=" + cartas + ", puntaje=" + puntaje + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.cartas);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jugada other = (Jugada) obj;
        if (!Objects.equals(this.cartas, other.cartas)) {
            return false;
        }
        return true;
    }
    

}
