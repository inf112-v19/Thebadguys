package Server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.Robot;

import java.io.*;
import java.net.Socket;

public class Player extends Thread {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Texture texture;
    private Sprite sprite;
    private Robot robot;
    private int[][] starts = {{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}};

    public Player (int number, Socket clientSocket) {
        try {
            this.socket = clientSocket;
            this.in = new ObjectInputStream(clientSocket.getInputStream());
            this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch(IOException e) {
            System.out.println("error");
        }
        texture = new Texture(Gdx.files.internal("Models/tank"+ (number)+".png"));
        sprite = new Sprite(texture);
        robot = new Robot(sprite, starts[number]);
        sprite.setPosition(robot.getSpriteX(),robot.getSpriteY());

    }

    @Override
    public void run() {
            while(true) {

            }
    }
}
