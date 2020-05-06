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
        Rummikub server = new Rummikub(8500);
        server.start();
    }

    @Override
    public void onOpen(WebSocket clientSocket, ClientHandshake ch) {

    }

    @Override
    public void onClose(WebSocket clientSocket, int i, String string, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMessage(WebSocket clientSocket, String json) {
        JSONObject request = new JSONObject(json);
        JSONObject response = new JSONObject();
        JSONObject dataRequest = new JSONObject((new JSONObject(request.get("data"))).toString());
        JSONObject dataResponse = new JSONObject();
        try {
            String type = request.getString("type");
            if (type.equalsIgnoreCase("createRoom")) {
                String nombre = dataRequest.getString("playerName");
                Sala sala = new Sala((ArrayList<Carta>) cartas.clone());
                Jugador jugador = new Jugador(nombre, clientSocket);
                sala.addJugador(jugador);
                String idSala = null;
                do {
                    idSala = createSalaId();
                } while (salas.containsKey(idSala));
                salas.put(idSala, sala);
                response.put("type", type);
                dataResponse.put("room", idSala);
                dataResponse.put("message", "200 OK");
                response.put("data", dataResponse.toString());
            } else if (type.equalsIgnoreCase("joinToRoom")) {
                String nombre = dataRequest.getString("playerName");
                String idSala = dataRequest.getString("room");
                Sala sala = salas.get(idSala);
                String message = "";
                if (sala == null) {
                    message = "Romm doesn't exist";
                }else {
                    if (sala.isPlayerExist(nombre)) {
                        message = "Player is already in room";
                    }else {
                        message = "200 OK";
                    }
                }
                dataResponse.put("message", message);
                response.put("type", type);
                response.put("data", dataResponse.toString());
            } else if (type.equalsIgnoreCase("keepAlive")) {
                response.put("type", type);
                dataResponse.put("message", "Ok");
                response.put("data", dataResponse.toString());
            } else {
                response.put("type", "Error");
                dataResponse.put("message", "Bad request");
                response.put("data", dataResponse.toString());
            }
            clientSocket.send(response.toString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private String createSalaId() {
        Random random = new Random();
        String toReturn = "";
        for (int i = 0; i < 15; i++) {
            int type = random.nextInt(3);
            switch (type) {
                case 0: {
                    toReturn += (char) random.nextInt(10) + 48;
                    break;
                }
                case 1: {
                    toReturn += (char) random.nextInt(26) + 65;
                    break;
                }
                case 2: {
                    toReturn += (char) random.nextInt(26) + 97;
                    break;
                }
            }
        }
        return toReturn;
    }

    @Override
    public void onError(WebSocket clientSocket, Exception excptn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
