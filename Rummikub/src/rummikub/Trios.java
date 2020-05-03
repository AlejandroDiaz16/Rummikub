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
public class Trios {

    public void organizarPorTrios(ArrayList<String> jugador) {
        ArrayList<String> orden = new ArrayList();
        while (jugador.size() > 0) {
            orden.add(jugador.remove(0));
        }

        ArrayList<String> color = new ArrayList();
        ArrayList<String> numeros = new ArrayList();
        color.add("A");
        color.add("R");
        color.add("N");
        color.add("V");
        for (int i = 1; i <= 13; i++) {
            numeros.add(i + "");
        }

        int c0 = 0;
        c0 += orden.remove("C-0") ? 1 : 0;
        c0 += orden.remove("C-0") ? 1 : 0;

        for (int i = 0; i < numeros.size(); i++) {
            for (int j = 0; j < color.size(); j++) {
                String ficha = color.get(j) + "-" + numeros.get(i);
                if (orden.remove(ficha)) {
                    jugador.add(ficha);
                    if (orden.remove(ficha)) {
                        jugador.add(ficha);
                    }
                }
            }
        }

        if (c0 == 1) {
            jugador.add("C-0");
        }
        if (c0 == 2) {
            jugador.add("C-0");
            jugador.add("C-0");
        }
        c0 = 0;

    }

    public boolean validarTrio(ArrayList<String> trio2) {
        try {

            ArrayList<String> trio = new ArrayList<>();
            for (int i = 0; i < trio2.size(); i++) {
                trio.add(trio2.get(i));
            }
            boolean vale = true;
            ArrayList<String> color = new ArrayList();
            color.add("A");
            color.add("R");
            color.add("N");
            color.add("V");

            String valorPrimeraFicha = "";

            if (trio.size() < 3 || trio.size() > 4) {
                return false;
            } else {
                for (int i = 0; i < trio.size(); i++) {

                }
            }
            for (int i = 0; i < trio.size(); i++) {
                if (!trio.get(i).equals("C-0")) {
                    valorPrimeraFicha = trio.get(i).split("-")[1];
                    break;
                }
            }
            int comodines = 0;
            for (int i = 0; i < trio.size(); i++) {
                if (!trio.get(i).equals("C-0")) {
                    if (!color.remove(trio.get(i).split("-")[0])) {
                        return false;
                    }
                } else {
                    comodines++;
                }
            }
            if (!(comodines <= trio.size())) {
                return false;
            }
            return vale;
        } catch (Exception e) {
            return false;
        }
    }

    public int obtenerPuntajeTrio(ArrayList<String> trio2) {
        int puntaje = 0;
        try {
            if (validarTrio(trio2)) {

                for (int i = 0; i < trio2.size(); i++) {
                    if (!trio2.get(i).equals("C-0")) {
                        return Integer.parseInt(trio2.get(i).split("-")[1]) * (trio2.size());
                    }
                }
                return puntaje;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

}
