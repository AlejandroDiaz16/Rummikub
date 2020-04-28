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
        //Algo para hacer pruebas
        ArrayList<String> prueba = new ArrayList<String>();
        
        
        
        prueba.add("A-2");
        prueba.add("A-3");
        prueba.add("A-4");
        prueba.add("A-5");
        prueba.add("A-6");
        prueba.add("A-7");
        prueba.add("A-8");
        prueba.add("A-9");
        prueba.add("A-10");
        prueba.add("A-11");
        prueba.add("A-12");
        prueba.add("C-0");
        prueba.add("C-0");
        System.out.println("Validar: " + validarEscalera(prueba));

        //son 106 fichas del 1-13 en 4 colores diferenres y dos comodines
        //JUGADORES
        ArrayList<ArrayList<String>> players = new ArrayList<ArrayList<String>>();;
        //BARAJA
        ArrayList<String> fichas = new ArrayList<String>();

        fichas.add("C-0");
        for (int i = 1; i <= 13; i++) {
            fichas.add("A-" + i);
            fichas.add("A-" + i);
            fichas.add("R-" + i);
            fichas.add("R-" + i);
            fichas.add("N-" + i);
            fichas.add("N-" + i);
            fichas.add("V-" + i);
            fichas.add("V-" + i);
        }
        fichas.add("C-0");
        /*
        System.out.println(fichas.toString());
        System.out.println(fichas.remove("A-1"));
        System.out.println(fichas.toString());
        System.out.println(fichas.remove("A-1"));
        System.out.println(fichas.toString());
        System.out.println(fichas.remove("A-1"));
        System.out.println(fichas.toString());
        System.out.println(fichas.remove("R-1"));
        System.out.println(fichas.toString());
         */

        //Mensaje bienvenida
        //INSTANCIA DE LOS JUGADORES
        int jugadores = numeroJugadores();
        for (int i = 0; i < jugadores; i++) {
            ArrayList<String> player = new ArrayList<String>();;
            players.add(player);
        }
        System.out.println("Numero de jugadores: " + players.size());

        //System.out.println(fichas.toString());
        //se inicia con 14 fichas por jugados
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < players.size(); j++) {
                llenarDeFichas(players.get(j), fichas);
            }
        }

        //fichas de cada jugador
        /*for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).toString());
        }*/
        //System.out.println(fichas.toString());
        //organizar fichas
        /*for (int i = 0; i < players.size(); i++) {
            organizarPorEscalera(players.get(i));
            System.out.println(players.get(i).toString());
            organizarPorTrios(players.get(i));
            System.out.println(players.get(i).toString());
        }*/
        // TODO code application logic here
    }

    public static int obtenerRamdom(int limit) {
        //RANDOM
        Random rd = new Random();
        return rd.nextInt(limit);
    }

    public static int numeroJugadores() {
        System.out.println("BIENVENIDO\n2 jugadores\n3 jugadores\n4 jugadores\n...\n");
        //NUMERO DE JUGADORES QUE VAN A ENTRAR
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int jugadores = 0;
        while (!(jugadores == 2 || jugadores == 3 || jugadores == 4)) {
            //System.out.println("Jugadores incorrectos");
            try {
                jugadores = Integer.parseInt(sc.readLine());
                if (!(jugadores == 2 || jugadores == 3 || jugadores == 4)) {
                    System.out.println("jugadores incorrectos");
                }
            } catch (Exception e) {
                System.out.println("Error");
                jugadores = 0;
            }
        }
        return jugadores;
    }

    public static String llenarDeFichas(ArrayList<String> jugador, ArrayList<String> fichas) {
        jugador.add(fichas.remove(obtenerRamdom(fichas.size())));
        return jugador.get(jugador.size() - 1);
    }

    public static void organizarPorEscalera(ArrayList<String> jugador) {
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
        for (int k = 0; k < jugador.size(); k++) {
            if ("C-0".equals(jugador.get(k))) {
                jugador.remove(k);
            }
        }
        if (c0 != 0) {
            if (c0 == 1) {
                jugador.add("C-0");
            }
            if (c0 == 2) {
                jugador.add("C-0");
                jugador.add("C-0");
            }
            c0 = 0;
        }
        for (int i = 0; i < color.size(); i++) {
            for (int j = 0; j < numeros.size(); j++) {
                String ficha = color.get(i) + "-" + numeros.get(j);
                int cantidad = 0;
                for (int k = 0; k < jugador.size(); k++) {
                    if (ficha.equals(jugador.get(k))) {
                        jugador.remove(k);
                        cantidad++;
                        k--;
                    }
                }
                if (cantidad != 0) {
                    if (cantidad == 1) {
                        jugador.add(ficha);
                    }
                    if (cantidad == 2) {
                        jugador.add(ficha);
                        jugador.add(ficha);
                    }
                    cantidad = 0;
                }

            }
        }

    }

    public static void organizarPorTrios(ArrayList<String> jugador) {
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
        for (int k = 0; k < jugador.size(); k++) {
            if ("C-0".equals(jugador.get(k))) {
                jugador.remove(k);
            }
        }
        if (c0 != 0) {
            if (c0 == 1) {
                jugador.add("C-0");
            }
            if (c0 == 2) {
                jugador.add("C-0");
                jugador.add("C-0");
            }
            c0 = 0;
        }
        for (int i = 0; i < numeros.size(); i++) {
            for (int j = 0; j < color.size(); j++) {
                String ficha = color.get(j) + "-" + numeros.get(i);
                int cantidad = 0;
                for (int k = 0; k < jugador.size(); k++) {
                    if (ficha.equals(jugador.get(k))) {
                        jugador.remove(k);
                        cantidad++;
                        k--;
                    }
                }
                if (cantidad != 0) {
                    if (cantidad == 1) {
                        jugador.add(ficha);
                    }
                    if (cantidad == 2) {
                        jugador.add(ficha);
                        jugador.add(ficha);
                    }
                    cantidad = 0;
                }

            }
        }

    }

    public static boolean validarEscalera(ArrayList<String> escalera) {
        boolean vale = true;
        ArrayList<String> color = new ArrayList();
        color.add("A");
        color.add("R");
        color.add("N");
        color.add("V");
        String colorPrimeraFicha = escalera.get(0).split("-")[0];
        for (int i = 0; i < escalera.size(); i++) {
            if (escalera.get(i).equals("C-0")) {
                boolean noHayMas = true;
                for (int j = i+1; j < escalera.size(); j++) {
                    if (escalera.get(j).equals("C-0")) {
                        noHayMas = false;
                        if (i == 0 && j == 1) {
                            colorPrimeraFicha = escalera.get(2).split("-")[0];
                            int valor = Integer.parseInt(escalera.get(2).split("-")[1]);
                            escalera.set(i, colorPrimeraFicha + "-" + (valor-2));
                            escalera.set(j, colorPrimeraFicha + "-" + (valor-1));
                        } else if (i == 0) {
                            colorPrimeraFicha = escalera.get(1).split("-")[0];
                            int valor = Integer.parseInt(escalera.get(1).split("-")[1]);
                            escalera.set(i, colorPrimeraFicha + "-" + (valor-1));
                            valor = Integer.parseInt(escalera.get(j-1).split("-")[1]);
                            escalera.set(j, colorPrimeraFicha + "-" + (valor+1));
                        } else{
                            colorPrimeraFicha = escalera.get(0).split("-")[0];
                            int valor = Integer.parseInt(escalera.get(i-1).split("-")[1]);
                            escalera.set(i, colorPrimeraFicha + "-" + (valor+1));
                            valor = Integer.parseInt(escalera.get(j-1).split("-")[1]);
                            escalera.set(j, colorPrimeraFicha + "-" + (valor+1));
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
        System.out.println(escalera.toString());
        boolean primeraFicha = true;
        if (escalera.size() < 3) {
            vale = false;
        }
        for (int i = 0; i < color.size(); i++) {

            if (colorPrimeraFicha.equals(color.get(i))) {
                primeraFicha = false;
                int valorPrimeraFicha = Integer.parseInt(escalera.get(0).split("-")[1]);
                int valorUltimaFicha = Integer.parseInt(escalera.get(escalera.size()-1).split("-")[1]);
                
                if (valorPrimeraFicha<1 || valorUltimaFicha>13) {
                    vale=false;
                }
                for (int j = 1; j < escalera.size(); j++) {
                    if (escalera.get(j).equals(colorPrimeraFicha + "-" + (valorPrimeraFicha + j))) {
                    } else {
                        System.out.println("ficha no válida");
                        vale = false;
                        break;
                    }
                }
                break;
            }
        }
        if (primeraFicha) {
            vale = false;
        }
        return vale;
    }

    public static boolean validarTrio(ArrayList<String> trio) {
        boolean vale = true;
        ArrayList<String> color = new ArrayList();
        color.add("A");
        color.add("R");
        color.add("N");
        color.add("V");
        String colorPrimeraFicha = trio.get(0).split("-")[0];
        String valorPrimeraFicha = trio.get(0).split("-")[1];
        if (trio.size() < 3 || trio.size() > 4) {
            vale = false;
        }
        for (int i = 0; i < trio.size(); i++) {
            if (valorPrimeraFicha.equals(trio.get(i).split("-")[1])) {
                if (!color.remove(trio.get(i).split("-")[0])) {
                    vale = false;
                    System.out.println("ficha no válida, misma ficha");
                    break;

                }
            } else {
                System.out.println("ficha no válida, mal número");
                vale = false;
                break;
            }
        }
        return true;
    }

}
