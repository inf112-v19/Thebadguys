package inf112.skeleton.app;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import map.GameMap;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
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
    
    public static void main(String []args) {
        while (listening && !start) {
            try {
                ServerSocket ss = new ServerSocket(5556);
                Socket s = ss.accept();
                System.out.println("client connected!");
                out = new ObjectOutputStream(s.getOutputStream());
                in = new ObjectInputStream(s.getInputStream());
                if(players[clientCount] == null) {
                    players[clientCount] = new Player(clientCount, in, out);
                }
                clientCount++;

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (clientCount == 8) {
                listening = false;
            }
        }
    }
}
