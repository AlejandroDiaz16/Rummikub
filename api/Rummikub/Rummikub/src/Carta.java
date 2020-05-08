/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.HashMap;
import java.util.Objects;
import org.json.JSONObject;

/**
 *
 * @author jair-
 */
public class Carta {

    private int valor;
    private String color;

    public Carta(int valor, String color) {
        this.valor = valor;
        this.color = color;
    }
    
    public Carta (JSONObject json){
        this.valor = json.getInt("valor");
        this.color = json.getString("color");
    }

    public int getValor() {
        return valor;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Carta{" + "valor=" + valor + ", color=" + color + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.valor;
        hash = 83 * hash + Objects.hashCode(this.color);
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
        final Carta other = (Carta) obj;
        if (this.valor != other.valor) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return true;
    }

}
