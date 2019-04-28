package Server;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.CardHandler;
import inf112.skeleton.app.Robot;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{
    //private static int clientCount = 0;
    private static boolean listening = true;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static Socket s;
    private static Texture[] textures;
    private static Sprite[] sprites;
    private static Robot[] robots;
    private static CardHandler[] cardHandlers;

    private static Player[] players;
    private static boolean start = false;

    private DatagramSocket socket;
    private int port;
    private Thread run, manage, send, receive;
    private boolean running = false;
    private boolean started = false;

    private List<ServerClient> clients = new ArrayList<ServerClient>();

    private int[][] positions;
    private boolean[] ready = {true, true, true, true, true, true, true, true};
    private String[][] clientCards = new String[8][10];
    private String moves;
    private String moveOrder;

    public Server(int port) {
        this.port = port;
        try{
            socket = new DatagramSocket(port);
        }
        catch (SocketException e) {
            e.printStackTrace();
            return;
        }
        run = new Thread(this, "Server");
        run.start();
        System.out.println("Started server on port: " + port);
    }

    public void run() {
        running = true;
        manageClients();
        receive();
    }

    private void manageClients() {
        manage = new Thread("Manage"){
            public void run() {
                while(running) {

                }
            }
        };
        manage.start();
    }

    private void receive() {
        receive = new Thread("Receive") {
            public void run() {
                while(running) {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    process(packet);
                    //System.out.println(clients.get(0).address.toString() + ":" + clients.get(0).port);
                }
            }
        };
        receive.start();
    }

    private void sendToAll(String message){
        for (int i = 0; i < clients.size(); i++) {
            ServerClient client = clients.get(i);
            send(message.getBytes(), client.address, client.port);
        }
    }

    private void sendToAllButMe(String message, int id){
        for (int i = 0; i < clients.size(); i++) {
            if(i == id) {
                continue;
            }
            else {
                ServerClient client = clients.get(i);
                send(message.getBytes(), client.address, client.port);
            }
        }
    }

    private void send(final byte[] data, final InetAddress address, final int port) {
        send = new Thread("Send") {
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                try {
                    socket.send(packet);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        send.start();
    }

    private void send(String message, InetAddress address, int port) {
        message += "/e/";
        send(message.getBytes(), address, port);
    }

    private void process(DatagramPacket packet) {
        String string = new String(packet.getData());
        if (string.startsWith("/c/") && !started && clientCount() < 8) { // client connects
            int id = Identifier.getIdentifier();
            clients.add(new ServerClient(string.split("/c/|/e/")[1], packet.getAddress(), packet.getPort(), id));
            String ID = "/c/" + id + "/e/";
            send(ID.getBytes(), packet.getAddress(), packet.getPort());
            sendToAllButMe(("/m/" + id +"/e/"), id);
        }
        else if (string.startsWith("/m/")) {
            sendToAll(string);
        }
        else if(string.startsWith("/r/")) { // a player is ready
            string = string.split("/r/|/e/")[1];
            int id = Integer.parseInt(string.substring(0, 1));
            ready[id] = true;
            System.out.println("player " + id + " is ready");
            String[] cards = string.substring(1).split("~");
            clientCards[id] = cards;
            /*for(int i = 0; i < cards.length; i++){
                System.out.println(clientCards[id][i]);
            }*/
            string = "/r/" + id + "/e/";
            sendToAllButMe(string, id);
            /*positions = getPositions();
            string = "/r/" + positions.toString() + "/e/";
            sendToAll(string);*/
        }
        else if(string.startsWith("/o/")) {
            orderMoves();
            string = "/o/" + moves + "~" + moveOrder + "/e/";
            System.out.println(moves);
            send(string.getBytes(),packet.getAddress(), packet.getPort());
        }
        else if (string.startsWith("/a/")) {
            if(getReady()) {
                send("/a//e/".getBytes(), packet.getAddress(), packet.getPort());
            }
            else {
                send("/b//e/".getBytes(), packet.getAddress(), packet.getPort());
            }
        }
        else if (string.startsWith("/c/") || started || clientCount() == 8) {
            string = "/f/" + "server is full or started" + "/e/";
            send(string.getBytes(), packet.getAddress(), packet.getPort());
        }
        else {
            System.out.println(string);
        }
    }

    public int[][] getPositions() {
        for(int i = 0; i < clients.size(); i++) {
            ServerClient client = clients.get(i);
            positions[i] = client.getPos();
        }
        return positions;
    }

    public int clientCount(){
        return clients.size();
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean getReady(){
        for(int i = 0; i < clientCount(); i++) {
            if(ready[i] = false) {
                return false;
            }
        }
        return true;
    }

    public boolean getReadyClient(int i) {
        if (ready[i]) {
            return true;
        }
        return false;
    }

    public void roundStart() {
        for(int i = 0; i < clientCount(); i++) {
            ready[i] = false;
        }
    }

    public void orderMoves() {
        int [][] priorities = new int[clientCount()][5];
        int k = 0;
        moveOrder = "";
        int[] tempPrio = new int[clientCount()];
        for(int i = 1; i < 10; i+=2) {
            for(int j = 0; j < clientCount(); j++) {
                priorities[j][k] = Integer.parseInt(clientCards[j][i]);
            }
            k++;
        }
        for (int i = 0; i < 5; i++){
            for(int j = 0; j < clientCount(); j++) {
                tempPrio[j] = priorities[j][i];
            }
            for(int l = 0; l < tempPrio.length; l++) {
                int maxIndex = indexOfMax(tempPrio);
                moveOrder += Integer.toString(maxIndex) + "#";
                tempPrio[maxIndex] = 0;
            }
        }
        moves = "";
        for(int j = 0; j < clientCount(); j++) {
            for(int i = 0; i < 9; i+=2) {
                moves += clientCards[j][i] + "#";
            }
            moves += " ";
        }
    }

    public int indexOfMax(int[] arr) {
        int max = arr[0];
        int maxIndex = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] > max) {
                maxIndex = i;
                max = arr[i];
            }
        }
        return maxIndex;
    }

    public void start() {
        sendToAll("/s/" + clientCount() + "/e/");
    }
}
    
    /*public static void main(String []args) {
        while (listening && !start) {
            try {
                ServerSocket ss = new ServerSocket(5556);
                Socket s = ss.accept();
                System.out.println("client connected!");
                new Player(clientCount, s).start();

                clientCount++;

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (clientCount == 7) {
                listening = false;
            }
        }
    }*/
