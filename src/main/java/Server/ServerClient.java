package Server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.Robot;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ServerClient {

    public String name;
    public InetAddress address;
    public int port;
    private final int ID;
    public int attempt = 0;

    private Texture texture;
    private Sprite sprite;
    private Robot robot;
    private int[][] starts = {{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}};

    public ServerClient(String name, InetAddress address, int port, final int ID) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.ID = ID;

        texture = new Texture(Gdx.files.internal("Models/tank"+ (ID)+".png"));
        sprite = new Sprite(texture);
        robot = new Robot(sprite, starts[ID]);
        sprite.setPosition(robot.getSpriteX(),robot.getSpriteY());

    }

    public int getID() {
        return ID;
    }

    public int[] getPos() {
        int[] pos = {this.robot.getPosX(),this.robot.getPosY()};
        return pos;
    }

}
