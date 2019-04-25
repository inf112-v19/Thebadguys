package Server;

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

    /*private void createWindow() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                String disconnect = "/d/" + client.getID() + "/e/";
                send(disconnect, false);
                running = false;

            }
        });

        setVisible(true);

        txtMessage.requestFocusInWindow();
    }*/

    public int getID(){
        return client.getID();
    }

    public void run() {
        listen();
    }

    private void send(String message, boolean text) {
        if (message.equals("")) return;
        if (text) {
            message = client.getName() + ": " + message;
            message = "/m/" + message + "/e/";
            txtMessage.setText("");
        }
        client.send(message.getBytes());
    }

    public void listen() {
        listen = new Thread("Listen") {
            public void run() {
                while (running) {
                    String message = client.receive();
                    if (message.startsWith("/c/")) {
                        client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
                        client.setName(client.getName()+client.getID());
                        System.out.println("Successfully connected to server! user: " + client.getName() + " ID: " + client.getID());
                    } else if (message.startsWith("/m/")) {
                        String text = message.substring(3);
                        text = text.split("/e/")[0];
                    } else if (message.startsWith("/i/")) {
                        String text = "/i/" + client.getID() + "/e/";
                        send(text, false);
                    } else if (message.startsWith("/u/")) {
                        String[] u = message.split("/u/|/n/|/e/");
                        //users.update(Arrays.copyOfRange(u, 1, u.length - 1));
                    }
                }
            }
        };
        listen.start();
    }

}