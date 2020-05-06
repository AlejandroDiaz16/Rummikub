

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;


public class Server extends WebSocketServer{
	private static Map<String,WebSocket> Users = new HashMap<>();
	private static List<WebSocket> clients=new ArrayList<WebSocket>();

    public Server() {
        super(new InetSocketAddress(30001));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New client connected: " + conn.getRemoteSocketAddress() + " hash " + conn.getRemoteSocketAddress().hashCode());
        clients.add(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        JSONObject obj = new JSONObject(message);
        try {
            if (obj.getString("type").equalsIgnoreCase("login")) {
                String username = obj.getString("username");
                Users.put(username, conn);
                System.out.println(username + " se ha conectado");
            } else if (obj.getString("type").equalsIgnoreCase("alive")) {
                System.out.println(conn.getLocalSocketAddress().getHostName() + " is alive");
            }
            else {
                //String receiver = obj.getString("receiver");
                String username = obj.getString("username");
                String mensaje = obj.getString("message");
                //WebSocket receiverSocket = Users.get(receiver);
                conn.send("Tú: " + mensaje);
                sendToAll(conn,username,mensaje);
                // receiverSocket.send(mensaje);
            }
        } catch (Exception e) {
        }
//        Set<WebSocket> s;
//        try {
//            String msgtype = obj.getString("type");
//            switch (msgtype) {
//                case "GETROOM":
//                    myroom = generateRoomNumber();
//                    s = new HashSet<>();
//                    s.add(conn);
//                    Rooms.put(myroom, s);
//                    System.out.println("Generated new room: " + myroom);
//                    conn.send("{\"type\":\"GETROOM\",\"value\":" + myroom + "}");
//                    break;
//                case "ENTERROOM":
//                    myroom = obj.getInt("value");
//                    System.out.println("New client entered room " + myroom);
//                    s = Rooms.get(myroom);
//                    s.add(conn);
//                    Rooms.put(myroom, s);
//                    break;
//                default:
//                    sendToAll(conn, message);
//                    break;
//            }
//        } catch (JSONException e) {
//            sendToAll(conn, message);
//        }
    	
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Client disconnected: " + reason);
    }

    @Override
    public void onError(WebSocket conn, Exception exc) {
        System.out.println("Error happened: " + exc);
    }
    
    private void sendMessage (WebSocket source, WebSocket receiver, String message) {
        
    }
    
    private int generateRoomNumber() {
        return new Random(System.currentTimeMillis()).nextInt();
    }

    private void sendToAll(WebSocket conn, String username, String message) {
       /*
    	Iterator it = Rooms.get(myroom).iterator();
        while (it.hasNext()) {
            WebSocket c = (WebSocket)it.next();
            if (c != conn) c.send(message);
        }
        */
    	for(int i =0;i<clients.size();i++) {
            try {
                WebSocket c = (WebSocket)clients.get(i);
                if (c != conn) c.send(username + ": " + message);
            } catch (Exception e) {
            }
    	}
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }


}
