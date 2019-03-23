package inf112.skeleton.app;

import Grid.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import java.util.regex.*;

public class Robot {
    private Sprite sprite;
    private int posX = 1;
    private int posY = 1;
    private int[] checkpoint = {posX, posY};
    private int flagsPassed = 0;
    private int lives = 3;
    private int damage = 0;
    private Direction dir = Direction.NORTH;
    private float w = Gdx.graphics.getWidth() * 6;
    private float h = Gdx.graphics.getHeight() * 6;
    private TiledMap tiledMap = RoboRallyDemo.getTiledMap();
    private MapProperties prop = tiledMap.getProperties();
    private int mapWidth = prop.get("width", Integer.class);
    private int mapHeight = prop.get("height", Integer.class);
    private int tilePixelWidth = prop.get("tilewidth", Integer.class);
    private int tilePixelHeight = prop.get("tileheight", Integer.class);
    private int x1 = (((Math.round(w) - (tilePixelWidth * mapWidth)) / 2) + (tilePixelWidth / 2)) / 10;
    private int y1 = (((Math.round(h) - (tilePixelHeight * mapHeight)) / 2) + (tilePixelHeight / 2)) / 10 * 3 - 9;
    public Robot(Sprite sprite){
        this.sprite = sprite;
    }

    public Robot(Sprite sprite, int[] checkpoint, int flagsPassed){
        this.sprite=sprite;
        this.checkpoint[0]=posX;
        this.checkpoint[1]=posY;
        this.flagsPassed = flagsPassed;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public int[] getCheckpoint() {
        return this.checkpoint;
    }

    public int getFlagsPassed() {
        return this.flagsPassed;
    }

    public Direction getDirection() {
        return dir;
    }

    public int getLives() {
        return this.lives;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getX1(){
        System.out.println(this.x1);
        System.out.println(this.mapWidth);
        System.out.println(this.mapHeight);
        System.out.println(this.tilePixelWidth);
        System.out.println(this.tilePixelHeight);
        System.out.println(this.w);
        System.out.println(this.h);
        return this.x1;
    }

    public int getY1(){
        System.out.println(this.y1);
        return this.y1;
    }

    // a bunch of set functions

    public void setCheckpoint(int[] checkpoint){
        this.checkpoint = checkpoint;
    }

    public void setFlagsPassed(int flagsPassed){
        this.flagsPassed = flagsPassed;
    }

    public void setPosX(int newX) {
        this.posX = newX;
    }

    public void setPosY(int newY) {
        this.posY = newY;
    }

    public void rotate_right() {
        if(this.getDirection() == Direction.WEST){
            this.dir = Direction.NORTH;
        }
        else if (this.getDirection() == Direction.NORTH){
            this.dir = Direction.EAST;
        }
        else if (this.getDirection() == Direction.EAST){
            this.dir = Direction.SOUTH;
        }
        else if (this.getDirection() == Direction.SOUTH){
            this.dir = Direction.WEST;
        }
        this.sprite.rotate(90);
    }

    public void rotate_left() {
        if(this.getDirection() == Direction.NORTH){
            this.dir = Direction.WEST;
        }
        else if (this.getDirection() == Direction.WEST){
            this.dir = Direction.SOUTH;
        }
        else if (this.getDirection() == Direction.SOUTH){
            this.dir = Direction.EAST;
        }
        else if (this.getDirection() == Direction.EAST){
            this.dir = Direction.NORTH;
        }
        this.sprite.rotate(-90);
    }

    public void moveForward(int amount){
        Direction current_direction = this.getDirection();
        if (current_direction == Direction.NORTH) {
            int newY = this.getPosY() + amount;
            this.setPosY(newY);
            this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() + (amount * (this.tilePixelWidth / 6))); // temp moving sprite
        }
        else if (current_direction == Direction.EAST) {
            int newX = this.getPosX() + amount;
            this.setPosX(newX);
            this.sprite.setPosition(this.sprite.getX() + (amount * (this.tilePixelWidth / 6)), this.sprite.getY());
        }
        else if (current_direction == Direction.SOUTH) {
            int newY = this.getPosY() - amount;
            this.setPosY(newY);
            this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() - (amount * (this.tilePixelWidth / 6)));
        }
        else if (current_direction == Direction.WEST) {
            int newX = this.getPosX() - amount;
            this.setPosX(newX);
            this.sprite.setPosition(this.sprite.getX() - (amount * (this.tilePixelWidth / 6)), this.sprite.getY());
        }
        else {
            System.out.println("Something went terribly wrong");
        }
    }

    public void move(Cards card){ // gets the command from a card and figures out which command to execute
        String command = card.getCardSprite().getTexture().toString();
        switch (command){
            case "Models/AlleBevegelseKortUtenPrioritet/BackUp.png":
                this.moveForward(-1);
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-1.png":
                this.moveForward(1);
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-2.png":
                this.moveForward(1); // added twice so we can incrementally check for collisions
                this.moveForward(1); // along the robots move-path
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-3.png":
                this.moveForward(1);
                this.moveForward(1);
                this.moveForward(1);
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png":
                this.rotate_right();
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png":
                this.rotate_right();
                this.rotate_right();
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png":
                this.rotate_left();
                break;
            default:
                System.out.println("Something went wrong");
        }
        // need check if robot is on map, and check for hazard, should integrate with grid
    }
    /*
    //future move method with regex to allow cards with priority
    public void move(Cards card){ // gets the command from a card and figures out which command to execute
        String command = card.getCardSprite().getTexture().toString();
        String path = "Models/Movement Cards/";
        Pattern backup = Pattern.compile(path + "card backwards - [0-9][0-9][0-9].png");
        Pattern move1 = Pattern.compile(path + "card template forward 1 - [0-9][0-9][0-9].png");
        Pattern move2 = Pattern.compile(path + "card template forward 2 - [0-9][0-9][0-9].png");
        Pattern move3 = Pattern.compile(path + "card template forward 3 - [0-9][0-9][0-9].png");
        Pattern rotater = Pattern.compile(path + "card template right - [0-9][0-9][0-9].png");
        Pattern rotatel = Pattern.compile(path + "card template left - [0-9][0-9][0-9].png");
        Pattern rotateu = Pattern.compile(path + "card template u-turn - [0-9][0-9][0-9].png");
            if(backup.matcher(command).matches()) {
                this.moveForward(-1);
                }
            else if (move1.matcher(command).matches()) {
                this.moveForward(1);
                }
            else if (move2.matcher(command).matches()) {
                this.moveForward(1); // added twice so we can incrementally check for collisions
                this.moveForward(1); // along the robots move-path
            }
            else if (move3.matcher(command).matches()) {
                this.moveForward(1);
                this.moveForward(1);
                this.moveForward(1);
            }
            else if (rotater.matcher(command).matches()) {
                this.rotate_right();
            }
            else if (rotateu.matcher(command).matches()) {
                this.rotate_right();
                this.rotate_right();
            }
            else if (rotatel.matcher(command).matches()) {
                this.rotate_left();
            }
            else{
                System.out.println("Something went wrong");
        }
        // need check if robot is on map, and check for hazard, should integrate with grid
    } */

    public void move(String command){ // added for use with conveyor belts etc
        switch (command){
            case "BackUp":
                this.moveForward(-1);
                break;
            case "Move-1":
                this.moveForward(1);
                break;
            case "Move-2":
                this.moveForward(1); // added twice so we can incrementally check for collisions
                this.moveForward(1); // along the robots move-path
                break;
            case "Move-3":
                this.moveForward(1);
                this.moveForward(1);
                this.moveForward(1);
                break;
            case "Rotate-90":
                this.rotate_right();
                break;
            case "Rotate-180":
                this.rotate_right();
                this.rotate_right();
                break;
            case "Rotate-C90":
                this.rotate_left();
                break;
            default:
                System.out.println("Something went wrong");
            }
        }

    public void died() {
        this.lives -= 1; // loose an option card of the players choice
        if (this.lives == 0) {
            // the robot needs to be deleted from the game.
        }
        else { // moves the sprite the appropriate amount in both x and y direction to the robots backup
            if(this.getPosX() <= this.getCheckpoint()[0] && this.getPosY() <= this.getCheckpoint()[1]) {
                this.sprite.setPosition(this.sprite.getX() + 75 * (this.getCheckpoint()[0] - this.getPosX()), this.sprite.getY() + 75 * (this.getCheckpoint()[1] - this.getPosY()));
            }
            else if(this.getPosX() >= this.getCheckpoint()[0] && this.getPosY() <= this.getCheckpoint()[1]) {
                this.sprite.setPosition(this.sprite.getX() - 75 * (this.getPosX() - this.getCheckpoint()[0]), this.sprite.getY() + 75 * (this.getCheckpoint()[1] - this.getPosY()));
            }
            else if(this.getPosX() <= this.getCheckpoint()[0] && this.getPosY() >= this.getCheckpoint()[1]) {
                this.sprite.setPosition(this.sprite.getX() + 75 * (this.getCheckpoint()[0] - this.getPosX()), this.sprite.getY() - 75 * (this.getPosY() - this.getCheckpoint()[1]));
            }
            else if(this.getPosX() >= this.getCheckpoint()[0] && this.getPosY() >= this.getCheckpoint()[1]) {
                this.sprite.setPosition(this.sprite.getX() - 75 * (this.getPosX() - this.getCheckpoint()[0]), this.sprite.getY() - 75 * (this.getPosY() - this.getCheckpoint()[1]));
            }
            else {
                System.out.println("Should definitely not be possible");
            }

            this.setPosX(this.getCheckpoint()[0]); //update internal numbers of robot location
            this.setPosY(this.getCheckpoint()[1]);
        }
    }

    public void takeDamage() {
        this.damage += 1;
        switch(this.damage) {
            case 5:
                //lock slot 5
                break;
            case 6:
                //lock slot 4
                break;
            case 7:
                //lock slot 3
                break;
            case 8:
                //lock slot 2
                break;
            case 9:
                //lock slot 1
                break;
            case 10:
                this.died();
                break;
        }
    }
}
