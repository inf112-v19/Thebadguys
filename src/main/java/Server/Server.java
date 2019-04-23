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
    private static int clientCount = 0;
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

    private List<ServerClient> clients = new ArrayList<ServerClient>();

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
                    System.out.println(clients.get(0).address.toString() + ":" + clients.get(0).port);
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

    private void process(DatagramPacket packet) {
        String string = new String(packet.getData());
        if (string.startsWith("/c/")) {
            int id = Identifier.getIdentifier();
            clients.add(new ServerClient(string.substring(3, string.length()), packet.getAddress(), packet.getPort(), id));
        }
        else if (string.startsWith("/m/")) {
            sendToAll(string);
        }
        else {
            System.out.println(string);
        }
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
