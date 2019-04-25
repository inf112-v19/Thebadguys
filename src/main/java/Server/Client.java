package Server;

import com.jcraft.jogg.Packet;
import inf112.skeleton.app.Cards;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class Client {

    private String name, address;
    private int port;
    private DatagramSocket socket;
    private InetAddress ip;
    private Thread send;
    private int id;

    public Client(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
        boolean connect = openConnection(address);
        if(!connect) {
            System.err.println("Connection failed!");
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public boolean openConnection(String address) {
        try {
            socket = new DatagramSocket();
            ip = InetAddress.getByName(address);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        catch (SocketException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void receive() {
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try{
            socket.receive(packet);
        }catch (IOException e) {
            e.printStackTrace();
        }
        process(packet);
    }

    public void send(final byte[] data) {
        send = new Thread("Send") {
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
                try {
                    socket.send(packet);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        send.start();
    }

    public void send(String message) {
        if(message.equals("")) return;
        message = name + ": " + message;
        message = "/m/" + message;
        send(message.getBytes());
    }

    public void process(DatagramPacket packet) {
        String string = new String(packet.getData());
        if (string.startsWith("/c/")) {
            this.id = Integer.parseInt(string.split("/c/|/e/")[1]);
            System.out.println("Connected to the server! ID: " + this.id);
        }
        else if (string.startsWith("/m/")) {

        }
        else {
            System.out.println(string);
        }
    }

    public int getID() {
        return ID;
    }
}
