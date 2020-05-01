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
public class Tablero {
    
    
    public boolean validarTablero(ArrayList<ArrayList<String>> tablero){
        Trios trio=new Trios();
        Escalera escalera=new Escalera();
        boolean vale=true;
        for (int i = 0; i < tablero.size(); i++) {
            ArrayList<String> validar=tablero.get(i);
            if (!escalera.validarEscalera(validar)) {
                if (!trio.validarTrio(validar)) {
                    return false;
                }
            }
        }  
        return vale;
    }
    public int puntosTablero(ArrayList<ArrayList<String>> tablero){
        Trios trio=new Trios();
        Escalera escalera=new Escalera();
        int puntuacion=0;
        if (validarTablero(tablero)) {
            for (int i = 0; i < tablero.size(); i++) {
                if (escalera.validarEscalera(tablero.get(i))) {
                    puntuacion+=escalera.obtenerPuntajeEscalera(tablero.get(i));
                }else{
                    puntuacion+=trio.obtenerPuntajeTrio(tablero.get(i));                
                }
            }
        }else{
            return 0;
        }
        return puntuacion;
    }
}
