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

    public Client(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
        boolean connect = openConnection(address, port);
        if(!connect) {
            System.err.println("Connection failed!");
        }
    }

    private boolean openConnection(String address, int port) {
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

    private String receive() {
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try{
            socket.receive(packet);
        }catch (IOException e) {
            e.printStackTrace();
        }
        String message = new String(packet.getData());
        return message;
    }

    private void send(final byte[] data) {
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

    private void send(String message) {
        if(message.equals("")) return;
        message = name + ": " + message;
        message = "/m/" + message;
        send(message.getBytes());
    }
}
