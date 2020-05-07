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
import java.util.Map;
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

        Rummikub server = new Rummikub(12500);
        server.start();
    }

    @Override
    public void onOpen(WebSocket clientSocket, ClientHandshake ch) {
        System.out.println(clientSocket.getRemoteSocketAddress() + " has connected");
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
            } else if (type.equalsIgnoreCase("setsocketplayer")) {
                String nombre = dataRequest.getString("playerName");
                nombre = nombre.toLowerCase();
                String idSala = dataRequest.getString("room");
                dataResponse = setSocketPlayer(nombre, idSala, clientSocket);
                response.put("type", type);
                response.put("data", dataResponse);
            } else if (type.equalsIgnoreCase("getplayersbyroom")) {
                String idSala = dataRequest.getString("room");
                dataResponse = getPlayersByRoom(idSala);
                response.put("type", type);
                response.put("data", dataResponse);
            } else if (type.equalsIgnoreCase("updateState")) {
                String idSala = dataRequest.getString("room");
                dataResponse = updateState(idSala, clientSocket);
                response.put("type", type);
                response.put("data", dataResponse);
            } else if (type.equalsIgnoreCase("startGame")) {
                String idSala = dataRequest.getString("room");
                dataResponse = startGame(idSala);
                response.put("type", type);
                response.put("data", dataResponse);
            } else if (type.equalsIgnoreCase("getCardsByPlayer")) {
                String idSala = dataRequest.getString("room");
                dataResponse = getCardsByPlayer(idSala, clientSocket);
                response.put("type", type);
                response.put("data", dataResponse);
            } else if (type.equalsIgnoreCase("keepAlive")) {
                dataResponse = new JSONObject();
                response.put("type", type);
                dataResponse.put("message", "Ok");
                System.out.println(clientSocket.getRemoteSocketAddress() + " is live");
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

    private void endGame(Sala sala) {
        endGame(sala, null);
    }

    private void endGame(Sala sala, Jugador playerWinner) {
        JSONObject response = new JSONObject();
        JSONObject dataResponse = new JSONObject();
        ArrayList<String> winners = new ArrayList<>();
        Hashtable<String, Jugador> jugadores = sala.getJugadores();
        if (playerWinner != null) {
            winners.add(playerWinner.getNombre());
        } else {
            int minCards = Integer.MAX_VALUE;
            for (Map.Entry<String, Jugador> entry : jugadores.entrySet()) {
                Jugador jugador = entry.getValue();
                int cardsPlayer = jugador.getBaraja().size();
                minCards = Integer.min(minCards, cardsPlayer);
            }
            for (Map.Entry<String, Jugador> entry : jugadores.entrySet()) {
                Jugador jugador = entry.getValue();
                int cardsPlayer = jugador.getBaraja().size();
                if (cardsPlayer == minCards) {
                    winners.add(jugador.getNombre());
                }
            }

        }
        dataResponse.put("message", "200 OK");
        dataResponse.put("winners", winners);
        response.put("type", "endGame");
        response.put("data", dataResponse);
        for (Map.Entry<String, Jugador> entry : jugadores.entrySet()) {
            Jugador jugador = entry.getValue();
            jugador.getConn().send(response.toString());
        }
        if (winners.size() > 1) {
            System.out.println(winners.toString() + " have won");
        } else {
            System.out.println(winners.toString() + " has/have won");
        }
    }

    private JSONObject getCardsByPlayer(String idSala, WebSocket webSocketClient) {
        JSONObject dataResponse = new JSONObject();
        Sala sala = salas.get(idSala);
        String message = "";
        if (sala == null) {
            message = "Room doesn't exist";
        } else {
            Jugador jugador = sala.getJugadorBySocket(webSocketClient);
            ArrayList<Carta> baraja = jugador.getBaraja();
            if (baraja.size() == 0) {
                endGame(sala, jugador);
            } else {
                dataResponse.put("cards", baraja);
            }
            System.out.println(jugador.getNombre());
            System.out.println(baraja.size());
            System.out.println(baraja);
        }
        dataResponse.put("message", message);
        return dataResponse;
    }

    private JSONObject startGame(String idSala) {
        JSONObject dataResponse = new JSONObject();
        Sala sala = salas.get(idSala);
        String message = "";
        if (sala == null) {
            message = "Room doesn't exist";
        } else if (sala.isGameStarted()) {
            message = "This room has already started";
        } else {
            boolean isPlayersReady = true;
            message = "200 OK";
            Hashtable<String, Jugador> jugadores = sala.getJugadores();
            if (jugadores.size() < 2) {
                message = "Don't enough players";
            } else {
                for (Map.Entry<String, Jugador> entry : sala.getJugadores().entrySet()) {
                    Jugador jugador = entry.getValue();
                    if (!jugador.isReady()) {
                        isPlayersReady = false;
                        message = "Players are not ready";
                        break;
                    }
                }
                if (isPlayersReady) {
                    sala.iniciarJuego();
                }
                dataResponse.put("isPlayersReady", isPlayersReady);
            }
        }
        dataResponse.put("message", message);
        return dataResponse;
    }

    private JSONObject updateState(String idSala, WebSocket webSockerClient) {
        JSONObject dataResponse = new JSONObject();
        Sala sala = salas.get(idSala);
        String message = "";
        if (sala == null) {
            message = "Room doesn't exist";
        } else if (sala.isGameStarted()) {
            message = "This room has already started";
        } else {
            Jugador jugador = sala.getJugadorBySocket(webSockerClient);
            if (jugador == null) {
                message = "Player doesn't exist";
            } else {
                message = "200 OK";
                jugador.setReady(!jugador.isReady());
                System.out.println(jugador.getNombre() + " isReady?: " + jugador.isReady());
                JSONObject responseOtherPlayer = new JSONObject();
                JSONObject dataResponseOtherPlayer = getPlayersByRoom(idSala);
                responseOtherPlayer.put("type", "getPlayersByRoom");
                responseOtherPlayer.put("data", dataResponseOtherPlayer);
                sala.getJugadores().forEach((name, player) -> {
                    player.getConn().send(responseOtherPlayer.toString());
                });
            }
        }
        dataResponse.put("message", message);

        return dataResponse;
    }

    private JSONObject setSocketPlayer(String nombre, String idSala, WebSocket newClientSocket) {
        JSONObject dataResponse = new JSONObject();
        Sala sala = salas.get(idSala);
        String message = "";
        if (sala == null) {
            message = "Romm doesn't exist";
        } else if (sala.isGameStarted()) {
            message = "This room has already started";
        } else {
            Jugador jugador = sala.getJugador(nombre);
            if (jugador == null) {
                message = "Player doesn't exist";
            } else {
                message = "200 OK";
                jugador.setConn(newClientSocket);
                System.out.println(nombre + " socket updated");
            }
        }
        dataResponse.put("message", message);
        return dataResponse;
    }

    private JSONObject getPlayersByRoom(String idSala) {
        JSONObject dataResponse = new JSONObject();
        Sala sala = salas.get(idSala);
        String message = "";
        if (sala == null) {
            message = "Room doesn't exist";
        } else if (sala.isGameStarted()) {
            message = "This room has already started";
        } else {
            ArrayList<JSONObject> jugadores = new ArrayList<>();
            sala.getJugadores().forEach((nombre, jugador) -> {
                JSONObject JSONJugador = new JSONObject();
                JSONJugador.put("playerName", nombre);
                JSONJugador.put("state", jugador.isReady());
                JSONJugador.put("isTurn", jugador.isTurn());
                jugadores.add(JSONJugador);
            });
            dataResponse.put("playersInfo", jugadores);
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
        } else if (sala.isGameStarted()) {
            message = "This room has already started";
        } else {
            if (sala.isPlayerExist(nombre)) {
                message = "Player is already in room";
            } else if (sala.getJugadores().size() >= 4) {
                message = "Room is full";
            } else {
                System.out.println(nombre + " has joined to " + idSala);
                sala.addJugador(new Jugador(nombre, clientSocket));

                JSONObject responseOtherPlayer = new JSONObject();
                JSONObject dataResponseOtherPlayer = getPlayersByRoom(idSala);
                responseOtherPlayer.put("type", "getPlayersByRoom");
                responseOtherPlayer.put("data", dataResponseOtherPlayer);
                sala.getJugadores().forEach((name, jugador) -> {
                    jugador.getConn().send(responseOtherPlayer.toString());
                });
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
        } else if (sala.isGameStarted()) {
            message = "This room has already started";
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
