package Server;

import com.jcraft.jogg.Packet;
import inf112.skeleton.app.Cards;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class ClientBackend {

    private String name, address;
    private int port;
    private DatagramSocket socket;
    private InetAddress ip;
    private Thread send;
    private int id = -1;

    public ClientBackend(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
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

    public String setName(String newName){
        this.name = newName;
        return this.name;
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

    public String receive() {
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

    public void close() {
        new Thread() {
            public void run() {
                synchronized (socket){
                    socket.close();
                }
            }
        }.start();
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
