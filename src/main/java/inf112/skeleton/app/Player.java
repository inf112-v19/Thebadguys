package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Player implements Runnable {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Texture texture;
    private Sprite sprite;
    private Robot robot;
    private int[][] starts = {{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}};

    public Player (int number, ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
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
