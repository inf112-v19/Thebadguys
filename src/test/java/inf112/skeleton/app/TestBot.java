package inf112.skeleton.app;

import Grid.Direction;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import map.GameMap;

public class TestBot implements IRobot {
    private CardHandler cardHandler;
    public Sprite sprite;
    private Boolean alive = true;
    private int posX = 0;
    private int posY = 0;
    private int[] checkpoint = {posX, posY};
    private int flagsPassed = 0;
    private int lives = 3;
    private int damage = 0;
    private int tilePixelWidth = 10;
    private int tilePixelHeight = 10;
    private Direction dir = Direction.NORTH;
    private TiledMap tiledMap = RoboRallyDemo.getTiledMap();
    private GameMap gameMap = RoboRallyDemo.getIGameMap();

    private int turn = RoboRallyDemo.getTurn();

    //Initiating Board element objects.
    ExpressBelt ebelt = new ExpressBelt(gameMap);
    Belt belt = new Belt(gameMap);
    Spin spin = new Spin(gameMap);

    private boolean powerdown = false;

    public TestBot(int[] checkpoint) {
        this.checkpoint = checkpoint;
        this.posX = checkpoint[0];
        this.posY = checkpoint[1];
    }

    public Boolean getAlive() {
        return this.alive;
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

    public int getTilePixelWidth(){
        return this.tilePixelWidth;
    }

    public int getTilePixelHeight(){
        return this.tilePixelHeight;
    }

    public Boolean getPowerdown() {
        return powerdown;
    }

    public void setPowerdown(boolean Powerdown) {
        this.powerdown = Powerdown;
    }

    public void moveSprite(float x, float y){
        this.sprite.setPosition(x, y);
    }

    public void rotateSprite(float z){
        this.sprite.rotate(z);
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setCheckpoint(int x, int y){
        this.checkpoint[0] = x;
        this.checkpoint[1] = y;
    }

    public void setFlagsPassed(int flagsPassed){
        this.flagsPassed = flagsPassed;
    }

    public void setDirection(Direction dir){
        this.dir = dir;
    }

    public void setPosX(int newX) {
        this.posX = newX;
    }

    public void setPosY(int newY) {
        this.posY = newY;
    }

    public void setDamage(int newDamage) {
        this.damage = newDamage;
    }

    public void setLives(int newLives) {
        this.lives = newLives;
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
        }
        else if (current_direction == Direction.EAST) {
            this.setPosX(this.getPosX() + amount);
        }
        else if (current_direction == Direction.SOUTH) {
            this.setPosY(this.getPosY() - amount);
        }
        else if (current_direction == Direction.WEST) {
            this.setPosX(this.getPosX() - amount);
        }
        else {
            System.out.println("Something went terribly wrong");
        }
        if (gameMap.isHole(this.posX, this.posY)) {
            System.out.println("You fell into a hole!");
            this.died();
        }
    }

    public void move(String move) { // gets the command from a card and figures out which command to execute
        //String command = card.getCardSprite().getTexture().toString();
        switch (move) {
            case "BACKUP":
                canMove(1, -1);
                break;
            case "MOVE1":
                canMove(1, 1);
                break;
            case "MOVE2":
                canMove(2, 1);
                break;
            case "MOVE3":
                canMove(3, 1);
                break;
            case "ROTATE90":
                this.rotate_right();
                break;
            case "ROTATE180":
                this.rotate_right();
                this.rotate_right();
                break;
            case "ROTATEC90":
                this.rotate_left();
                break;
            default:
                System.out.println("Something went wrong");
        }
        ExpressBelt.doExpressBelt(this);
        Belt.doBelt(this);
        Spin.doSpin(this);

        gameMap.fireLasers(this);
        //add method to fire my laser
        if (gameMap.isCheckpoint(this.posX, this.posY, this.flagsPassed)) {
            this.flagsPassed += 1;
            this.setCheckpoint(this.getPosX(), this.getPosY());
            System.out.println("You made it to backup number " + this.flagsPassed);
        }
        if (gameMap.isRepairSite(this.posX, this.posY, this.turn) == 1) {
            this.setCheckpoint(this.posX, this.posY);
            System.out.println("Backup on repairsite!");
        } else if (gameMap.isRepairSite(this.posX, this.posY, this.turn) == 2) {
            this.setCheckpoint(this.posX, this.posY);
            if (this.damage != 0) {
                this.damage -= 1;
            }
        } else if (gameMap.isRepairSite(this.posX, this.posY, this.turn) == 3) {
            this.setCheckpoint(this.posX, this.posY);
            if (this.damage > 1) {
                this.damage -= 2; // put in choice for option cards.
            } else if (this.damage == 1) {
                this.damage = 0;
            }
        }
    }


    public void died() {
        this.lives -= 1; // loose an option card of the players choice
        this.damage = 0;
        this.takeDamage();
        this.takeDamage();
        this.alive = false;
        if (this.lives == 0) {
            // the robot needs to be deleted from the game.
            System.out.println("You lost the game");
            System.exit(0);
        }
        else {
            System.out.println("you died");
            // moves the sprite the appropriate amount in both x and y direction to the robots backup
            if(this.getPosX() <= this.getCheckpoint()[0] && this.getPosY() <= this.getCheckpoint()[1]) {
            }
            else if(this.getPosX() >= this.getCheckpoint()[0] && this.getPosY() <= this.getCheckpoint()[1]) {
            }
            else if(this.getPosX() <= this.getCheckpoint()[0] && this.getPosY() >= this.getCheckpoint()[1]) {
            }
            else if(this.getPosX() >= this.getCheckpoint()[0] && this.getPosY() >= this.getCheckpoint()[1]) {
            }
            else {
                System.out.println("Should definitely not be possible");
            }
            if (this.dir == Direction.EAST) {
                this.rotate_left();
            }
            else if (this.dir == Direction.SOUTH) {
                this.rotate_right();
                this.rotate_right();
            }
            else if (this.dir == Direction.WEST) {
                this.rotate_right();
            }
            this.dir = Direction.NORTH;
            this.setPosX(this.getCheckpoint()[0]); //update internal numbers of robot location
            this.setPosY(this.getCheckpoint()[1]);
        }
    }

    public void takeDamage() {
        if (this.damage < 9) {
            this.damage += 1;
            System.out.println("You now have" + this.damage);
            //cardHandler = RoboRallyDemo.getCardHandler();
            System.out.println(cardHandler);
            //cardHandler.lockDown();
            System.out.println(this.damage);
        }
        else {
            this.died();
        }
    }

    public int checkNext(int amount) {
        if (this.dir == Direction.NORTH && (this.posY + amount == 12 || this.posY + amount == -1)) {
            return -1;
        }
        else if (this.dir == Direction.EAST && (this.posX + amount == 12 || this.posX + amount == -1)) {
            return -1;
        }
        else if (this.dir == Direction.SOUTH && (this.posY - amount == -1 || this.posY - amount == 12)) {
            return -1;
        }
        else if (this.dir == Direction.WEST && (this.posX - amount == -1 || this.posX - amount == 12)) {
            return -1;
        }
        else if (gameMap.convWallNearby(this.dir, this.posX, this.posY)) {
            return 0;
        }
        else {
            return 1;
        }
    }

    public void canMove(int loops, int amount) {
        for (int i = 0; i < loops; i++){
            if (this.checkNext(amount) == 1) {
                this.moveForward(amount);
            }
            else if (this.checkNext(amount) == -1) {
                this.died();
                break;
            }
            else if (this.checkNext(amount) == 0) {
                System.out.println("You hit a wall!");
                break;
            }
            else {
                break;
            }
        }
    }
}

