/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

/**
 *
 * @author jair-
 */
public class Rummikub extends WebSocketServer {

    private static Hashtable<String, Sala> salas = new Hashtable<>();
    private static ArrayList<Carta> cartas = new ArrayList<>();

    public Rummikub(int port) {
        super(new InetSocketAddress(port));
        System.out.println("Server up");
    }

    public static void main(String[] args) throws IOException {

        cartas.add(new Carta(0, "Red-Face"));
        for (int i = 1; i <= 13; i++) {
            cartas.add(new Carta(i, "Blue"));
            cartas.add(new Carta(i, "Blue"));

            cartas.add(new Carta(i, "Red"));
            cartas.add(new Carta(i, "Red"));

            cartas.add(new Carta(i, "Black"));
            cartas.add(new Carta(i, "Black"));

            cartas.add(new Carta(i, "Yellow"));
            cartas.add(new Carta(i, "Yellow"));
        }
        cartas.add(new Carta(0, "Black-Face"));
        JSONObject ssss = new JSONObject();
        ssss.put("arrat", cartas);
        System.out.println(ssss.toString());
        Rummikub server = new Rummikub(12500);
        server.start();
    }

    @Override
    public void onOpen(WebSocket clientSocket, ClientHandshake ch) {
        System.out.println(clientSocket.getLocalSocketAddress() + " has connected");
    }

    @Override
    public void onClose(WebSocket clientSocket, int i, String string, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMessage(WebSocket clientSocket, String json) {
        JSONObject request = new JSONObject(json);
        JSONObject response = new JSONObject();
        JSONObject dataRequest = request.getJSONObject("data");
        JSONObject dataResponse;
        try {
            String type = request.getString("type");
            if (type.equalsIgnoreCase("createRoom")) {
                String nombre = dataRequest.getString("playerName");
                nombre = nombre.toLowerCase();
                dataResponse = createRoom(nombre, clientSocket);
                response.put("type", type);
                response.put("data", dataResponse);
            } else if (type.equalsIgnoreCase("joinToRoom")) {
                String nombre = dataRequest.getString("playerName");
                nombre = nombre.toLowerCase();
                String idSala = dataRequest.getString("room");
                dataResponse = JoinToRoom(nombre, clientSocket, idSala);
                response.put("type", type);
                response.put("data", dataResponse);
            } else if (type.equalsIgnoreCase("getBoard")) {
                String idSala = dataRequest.getString("room");
                dataResponse = getBoard(idSala);
                response.put("type", type);
                response.put("data", dataResponse);
            } else if (type.equalsIgnoreCase("setSocketPlayer")) {
                String nombre = dataRequest.getString("playerName");
                nombre = nombre.toLowerCase();
                String idSala = dataRequest.getString("room");
                dataResponse = setSocketPlayer(nombre, idSala, clientSocket);
                response.put("type", type);
                response.put("data", dataResponse);
            }
            else if (type.equalsIgnoreCase("keepAlive")) {
                dataResponse = new JSONObject();
                response.put("type", type);
                dataResponse.put("message", "Ok");
                response.put("data", dataResponse);
            } else {
                dataResponse = new JSONObject();
                response.put("type", "Error");
                dataResponse.put("message", "Bad request");
                response.put("data", dataResponse);
            }
            clientSocket.send(response.toString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private String createSalaId() {
        Random random = new Random();
        String toReturn = "";
        for (int i = 0; i < 7; i++) {
            int type = random.nextInt(3);
            switch (type) {
                case 0: {
                    toReturn += (char) (random.nextInt(10) + 48);
                    break;
                }
                case 1: {
                    toReturn += (char) (random.nextInt(26) + 65);
                    break;
                }
                case 2: {
                    toReturn += (char) (random.nextInt(26) + 97);
                    break;
                }
            }
        }
        return toReturn;
    }
    
    private JSONObject setSocketPlayer(String nombre, String idSala, WebSocket newClientSocket){
        JSONObject dataResponse = new JSONObject();
        Sala sala = salas.get(idSala);
        String message = "";
        if (sala == null) {
            message = "Romm doesn't exist";
        }else {
            Jugador jugador = sala.getJugador(nombre);
            if (jugador == null) {
                message = "Player doesn't exist";
            }else {
                message = "200 OK";
                jugador.setConn(newClientSocket);
                System.out.println(nombre + " socket updated");
            }
        }
        dataResponse.put("message", message);
        return dataResponse;
    }
    
    private JSONObject createRoom(String nombre, WebSocket clientSocket) {
        JSONObject dataResponse = new JSONObject();
        Sala sala = new Sala((ArrayList<Carta>) cartas.clone());
        Jugador jugador = new Jugador(nombre, clientSocket);
        sala.addJugador(jugador);
        String idSala = null;
        do {
            idSala = createSalaId();
        } while (salas.containsKey(idSala));
        salas.put(idSala, sala);
        dataResponse.put("room", idSala);
        dataResponse.put("message", "200 OK");
        System.out.println(nombre + " has created a room: " + idSala);
        return dataResponse;
    }

    private JSONObject JoinToRoom(String nombre, WebSocket clientSocket, String idSala) {
        JSONObject dataResponse = new JSONObject();
        Sala sala = salas.get(idSala);
        String message = "";
        if (sala == null) {
            message = "Romm doesn't exist";
        } else {
            if (sala.isPlayerExist(nombre)) {
                message = "Player is already in room";
            } else if (sala.getJugadores().size() >= 4) {
                message = "Room is full";
            } else {
                System.out.println(nombre + " has joined to " + idSala);
                sala.getJugadores().forEach((name, jugador) -> {
                    jugador.getConn().send(nombre + " has joined");
                });
                sala.addJugador(new Jugador(nombre, clientSocket));
                message = "200 OK";
            }
        }
        dataResponse.put("message", message);
        return dataResponse;
    }

    private JSONObject getBoard(String idSala) {
        JSONObject dataResponse = new JSONObject();
        Sala sala = salas.get(idSala);
        String message = "";
        if (sala == null) {
            message = "Romm doesn't exist";
        } else {
            message = "200 OK";
            dataResponse.put("board", sala.getTablero());
        }
        dataResponse.put("message", message);
        return dataResponse;
    }

    @Override
    public void onError(WebSocket clientSocket, Exception excptn) {

    }
}
