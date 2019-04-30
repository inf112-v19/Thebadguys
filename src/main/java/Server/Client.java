package Server;

import inf112.skeleton.app.RoboRallyDemo;
import inf112.skeleton.app.mainMenu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Client extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField txtMessage;
    private JTextArea history;
    private DefaultCaret caret;
    private Thread run, listen;
    private ClientBackend client;
    private boolean ready = false;
    private String orde;
    private String move;
    private int[] order;
    private String[][] moves;
    private int clientCount;
    public boolean started = false;

    private boolean running = false;
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenuItem mntmOnlineUsers;
    private JMenuItem mntmExit;

    //private OnlineUsers users;

    public Client(String name, String address, int port) {
        client = new ClientBackend(name, address, port);
        boolean connect = client.openConnection(address);
        if (!connect) {
            System.err.println("Connection failed!");
        }
        //createWindow();
        String connection = "/c/" + name + "/e/";
        client.send(connection.getBytes());
        System.out.println("Attempting a connection to " + address + ":" + port + ", user: " + client.getName());
        //users = new OnlineUsers();
        running = true;
        run = new Thread(this, "Running");
        run.start();
    }

    public int getID() {
        return client.getID();
    }

    public void run() {
        listen();
    }

    public void send(final byte[] message) {
        client.send(message);
    }

    public void listen() {
        listen = new Thread("Listen") {
            public void run() {
                while (running) {
                    String message = client.receive();
                    if (message.startsWith("/c/")) {
                        client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
                        client.setName(client.getName() + client.getID());
                        RoboRallyDemo.setID(client.getID());
                        System.out.println("Successfully connected to server! user: " + client.getName() + " ID: " + client.getID());
                    } else if (message.startsWith("/m/")) {
                        int player = Integer.parseInt(message.split("/m/|/e/")[1]);
                        System.out.println("Player" + player + " connected to the game.");
                    } else if (message.startsWith("/i/")) {
                        String text = "/i/" + client.getID() + "/e/";
                        send(text.getBytes());
                    } else if (message.startsWith("/u/")) {
                        String[] u = message.split("/u/|/n/|/e/");
                        //users.update(Arrays.copyOfRange(u, 1, u.length - 1));
                    } else if (message.startsWith("/r/")) {
                        message = message.split("/r/|/e/")[1];
                        System.out.println("player " + Integer.parseInt(message) + " is ready");
                    } else if (message.startsWith("/a/")) {
                        ready = true;
                    } else if (message.startsWith("/b/")) {
                        ready = false;
                    } else if (message.startsWith("/o/")) {
                        message = message.split("/o/|/e/")[1];
                        move = message.split("~")[0];
                        makeMoves(move);
                        orde = message.split("~")[1];
                        makeOrder(orde);
                    } else if(message.startsWith("/s/")) {
                        message = message.split("/s/|/e/")[1];
                        clientCount = Integer.parseInt(message);
                        mainMenu.setMainRunning(false);
                        started = true;
                    } else if (message.startsWith("/w/")) { // may remove
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (message.startsWith("/f/")) {
                        String text = message.split("/f/|/e/")[1];
                        System.out.println("Connection refused, " + text + ".");
                    }
                }
            }
        };
        listen.start();
    }

    public ClientBackend getBackendClient() {
        return client;
    }

    public boolean askReady() {
        client.send("/a//e/".getBytes());
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ready;
    }

    public void makeMoves(String move) {
        moves = new String[clientCount][5];
        for(int j = 0; j < clientCount; j++) {
            for(int i = 0; i < 5; i++) {
                String clientMove = move.split(" ")[j].split("#")[i];
                moves[j][i] = clientMove;
            }
        }
    }

    public void makeOrder(String orde) {
        order = new int[clientCount*5];
        for(int i = 0; i < order.length; i++){
            order[i] = Integer.parseInt(orde.split("#")[i]);
            System.out.println(order[i]);
        }
    }

    public int getClientCount() {
        return clientCount;
    }

    public String[][] getMoves() {
        return moves;
    }

    public int[] getOrder() {
        return order;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean getStarted() {
        return started;
    }
}
