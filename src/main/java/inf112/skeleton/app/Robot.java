package inf112.skeleton.app;


import Grid.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import map.GameMap;


public class Robot {
    private CardHandler cardHandler;
    private Sprite sprite;
    private int posX = 0;
    private int posY = 0;
    private int[] checkpoint = {posX, posY};
    private int flagsPassed = 0;
    private int lives = 3;
    private int damage = 0;
    private Direction dir = Direction.NORTH;
    private float w = Gdx.graphics.getWidth() * 6;
    private float h = Gdx.graphics.getHeight() * 6;
    private TiledMap tiledMap = RoboRallyDemo.getTiledMap();
    private GameMap gameMap = RoboRallyDemo.getIGameMap();
    private MapProperties prop = tiledMap.getProperties();
    private int mapWidth = prop.get("width", Integer.class);
    private int mapHeight = prop.get("height", Integer.class);
    private int tilePixelWidth = prop.get("tilewidth", Integer.class);
    private int tilePixelHeight = prop.get("tileheight", Integer.class);
    private int x1 = (((Math.round(w) - (tilePixelWidth * mapWidth)) / 2) + (tilePixelWidth / 2)) / 10 -100;
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

    public void setCheckpoint(int x, int y){
        this.checkpoint[0] = x;
        this.checkpoint[1] = y;
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
        this.sprite.rotate(-90);
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
        this.sprite.rotate(90);
    }

    public void moveForward(int amount){
        Direction current_direction = this.getDirection();
        if (current_direction == Direction.NORTH) {
            this.setPosY(this.getPosY() + amount);
            this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() + (amount * (this.tilePixelWidth / 6))); // temp moving sprite
        }
        else if (current_direction == Direction.EAST) {
            this.setPosX(this.getPosX() + amount);
            this.sprite.setPosition(this.sprite.getX() + (amount * (this.tilePixelWidth / 6)), this.sprite.getY());
        }
        else if (current_direction == Direction.SOUTH) {
            this.setPosY(this.getPosY() - amount);
            this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() - (amount * (this.tilePixelWidth / 6)));
        }
        else if (current_direction == Direction.WEST) {
            this.setPosX(this.getPosX() - amount);
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
                for (int i = 0; i < 1; i++){
                    if (checkNext()) {
                        this.moveForward(-1);
                    }
                    else {
                        this.died();
                        break;
                    }
                }
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-1.png":
                for (int i = 0; i < 1; i++){
                    if (checkNext()) {
                        this.moveForward(1);
                    }
                    else {
                        this.died();
                        break;
                    }
                }
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-2.png":
                for (int i = 0; i < 2; i++){
                    if (checkNext()) {
                        this.moveForward(1);
                    }
                    else {
                        this.died();
                        break;
                    }
                }
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-3.png":
                for (int i = 0; i < 3; i++){
                    if (checkNext()) {
                        this.moveForward(1);
                    }
                    else {
                        this.died();
                        break;
                    }
                }
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
        if (gameMap.isCheckpoint(this.getPosX(), this.getPosY(), this.flagsPassed)) {
            this.flagsPassed += 1;
            this.setCheckpoint(this.getPosX(), this.getPosY());
            System.out.println("You made it to backup number " + this.flagsPassed);
        }
        if (gameMap.isLaser(this.getPosX(),this.getPosY())){
            this.takeDamage();
        }
        if(gameMap.isHole(this.getPosX(), this.getPosY())){
            System.out.println("You fell into a hole!");
            died();
        }
        if(gameMap.isSpinLeft(this.getPosX(), this.getPosY())){
            System.out.println("SPIN!");
            this.rotate_left();
        }
    }

    public void died() {
        this.lives -= 1; // loose an option card of the players choice
        if (this.lives == 0) {
            // the robot needs to be deleted from the game.
            System.out.println("You lost the game");
            System.exit(0);
        }
        else {
            System.out.println("you died");
            // moves the sprite the appropriate amount in both x and y direction to the robots backup
            if(this.getPosX() <= this.getCheckpoint()[0] && this.getPosY() <= this.getCheckpoint()[1]) {
                this.sprite.setPosition(this.sprite.getX() + ((this.tilePixelWidth / 6) * (this.getCheckpoint()[0] - this.getPosX())), this.sprite.getY() + ((this.tilePixelWidth / 6) * (this.getCheckpoint()[1] - this.getPosY())));
            }
            else if(this.getPosX() >= this.getCheckpoint()[0] && this.getPosY() <= this.getCheckpoint()[1]) {
                this.sprite.setPosition(this.sprite.getX() - ((this.tilePixelWidth / 6) * (this.getPosX() - this.getCheckpoint()[0])), this.sprite.getY() + ((this.tilePixelWidth / 6) * (this.getCheckpoint()[1] - this.getPosY())));
            }
            else if(this.getPosX() <= this.getCheckpoint()[0] && this.getPosY() >= this.getCheckpoint()[1]) {
                this.sprite.setPosition(this.sprite.getX() + ((this.tilePixelWidth / 6) * (this.getCheckpoint()[0] - this.getPosX())), this.sprite.getY() - ((this.tilePixelWidth / 6) * (this.getPosY() - this.getCheckpoint()[1])));
            }
            else if(this.getPosX() >= this.getCheckpoint()[0] && this.getPosY() >= this.getCheckpoint()[1]) {
                this.sprite.setPosition(this.sprite.getX() - ((this.tilePixelWidth / 6) * (this.getPosX() - this.getCheckpoint()[0])), this.sprite.getY() - ((this.tilePixelWidth / 6) * (this.getPosY() - this.getCheckpoint()[1])));
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
        cardHandler = RoboRallyDemo.getCardHandler();
        cardHandler.lockDown();
        System.out.println(this.damage);
    }

    public Boolean checkNext() {
        if (this.dir == Direction.NORTH && this.getPosY() + 1 == 12) {
            return false;
        }
        else if (this.dir == Direction.EAST && this.getPosX() + 1 == 12) {
            return false;
        }
        else if (this.dir == Direction.SOUTH && this.getPosY() -1 == -1) {
            return false;
        }
        else if (this.dir == Direction.WEST && this.getPosX() -1 == -1) {
            return false;
        }
        else {
            return true;
        }
    }
}
