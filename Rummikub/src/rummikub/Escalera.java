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
public class Escalera {

    public void organizarPorEscalera(ArrayList<String> jugador) {
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
        for (int i = 0; i < color.size(); i++) {
            for (int j = 0; j < numeros.size(); j++) {
                String ficha = color.get(i) + "-" + numeros.get(j);
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
    }

    public boolean validarEscalera(ArrayList<String> escalera2) {
        try {
            boolean vale = true;
            ArrayList<String> color = new ArrayList();
            color.add("A");
            color.add("R");
            color.add("N");
            color.add("V");
            ArrayList<String> escalera = new ArrayList<>();
            if (escalera2.size() < 3 || escalera2.size() > 13) {
                return false;
            }
            for (int i = 0; i < escalera2.size(); i++) {
                escalera.add(escalera2.get(i));
            }
            String colorPrimeraFicha = convertirLasC0Normales(escalera);
            System.out.println(escalera.toString());
            System.out.println(escalera2.toString());
            boolean primeraFicha = true;
            for (int i = 0; i < color.size(); i++) {
                if (colorPrimeraFicha.equals(color.get(i))) {
                    primeraFicha = false;
                    int valorPrimeraFicha = Integer.parseInt(escalera.get(0).split("-")[1]);
                    int valorUltimaFicha = Integer.parseInt(escalera.get(escalera.size() - 1).split("-")[1]);
                    if (valorPrimeraFicha < 1 || valorUltimaFicha > 13) {
                        return false;
                    }
                    for (int j = 1; j < escalera.size(); j++) {
                        if (!(escalera.get(j).equals(colorPrimeraFicha + "-" + (valorPrimeraFicha + j)))) {
                            return false;
                        }
                    }
                    break;
                }
            }
            if (primeraFicha) {
                return false;
            }
            return vale;
        } catch (Exception e) {
            return false;
        }
    }

    public int obtenerPuntajeEscalera(ArrayList<String> escalera2) {
        int puntaje = 0;
        try {
            if (validarEscalera(escalera2)) {
                ArrayList<String> escalera = new ArrayList<>();
                for (int i = 0; i < escalera2.size(); i++) {
                    escalera.add(escalera2.get(i));
                }
                convertirLasC0Normales(escalera);
                for (int i = 0; i < escalera.size(); i++) {
                    puntaje += Integer.parseInt(escalera.get(i).split("-")[1]);
                }
                return puntaje;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public String convertirLasC0Normales(ArrayList<String> escalera) {
        String colorPrimeraFicha = "";
        for (int i = 0; i < escalera.size(); i++) {
            if (escalera.get(i).equals("C-0")) {
                boolean noHayMas = true;
                for (int j = i + 1; j < escalera.size(); j++) {
                    if (escalera.get(j).equals("C-0")) {
                        noHayMas = false;
                        //continue;
                        if (i == 0 && j == 1) {
                            colorPrimeraFicha = escalera.get(2).split("-")[0];
                            int valor = Integer.parseInt(escalera.get(2).split("-")[1]);
                            escalera.set(i, colorPrimeraFicha + "-" + (valor - 2));
                            escalera.set(j, colorPrimeraFicha + "-" + (valor - 1));
                        } else if (i == 0) {
                            colorPrimeraFicha = escalera.get(1).split("-")[0];
                            int valor = Integer.parseInt(escalera.get(1).split("-")[1]);
                            escalera.set(i, colorPrimeraFicha + "-" + (valor - 1));
                            valor = Integer.parseInt(escalera.get(j - 1).split("-")[1]);
                            escalera.set(j, colorPrimeraFicha + "-" + (valor + 1));
                        } else {
                            colorPrimeraFicha = escalera.get(0).split("-")[0];
                            int valor = Integer.parseInt(escalera.get(i - 1).split("-")[1]);
                            escalera.set(i, colorPrimeraFicha + "-" + (valor + 1));
                            valor = Integer.parseInt(escalera.get(j - 1).split("-")[1]);
                            escalera.set(j, colorPrimeraFicha + "-" + (valor + 1));
                        }
                        break;
                    }
                }
                if (i == 0 && noHayMas) {
                    colorPrimeraFicha = escalera.get(1).split("-")[0];
                    int valor = Integer.parseInt(escalera.get(1).split("-")[1]);
                    escalera.set(i, colorPrimeraFicha + "-" + (valor - 1));
                } else if (noHayMas) {
                    int valor = Integer.parseInt(escalera.get(i - 1).split("-")[1]);
                    escalera.set(i, colorPrimeraFicha + "-" + (valor + 1));
                }

                break;
            }
        }
        System.out.println("La escalera sin C-0 es así: " + escalera.toString());
        return colorPrimeraFicha;
    }
}
