package Server;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.CardHandler;
import inf112.skeleton.app.Robot;

import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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

    public Server(int port) {
        this.port = port;
        try{
            socket = new DatagramSocket(port);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        run = new Thread(this, "Server");
    }

    @Override
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

                }
            }
        };
        receive.start();
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
}
