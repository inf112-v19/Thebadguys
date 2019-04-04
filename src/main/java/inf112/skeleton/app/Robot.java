package inf112.skeleton.app;


import Grid.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import map.GameMap;
import map.MapTile;

public class Robot {
    private CardHandler cardHandler;
    private Sprite sprite;
    private int turn = 0;
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

    public Robot(int[] checkpoint) {
        this.checkpoint = checkpoint;
        this.posX = checkpoint[0];
        this.posY = checkpoint[1];
    }

    public Robot(Sprite sprite, int[] checkpoint){
        this.sprite=sprite;
        this.checkpoint = checkpoint;
        this.posX = checkpoint[0];
        this.posY = checkpoint[1];
    }

    public int getTurn() {
        return this.turn;
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

    public void setTurn(int turn) {
        this.turn = turn;
    }

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
        if (gameMap.isHole(this.posX, this.posY)) {
            System.out.println("You fell into a hole!");
            this.died();
        }
    }

    public void move(Cards card){ // gets the command from a card and figures out which command to execute
        String command = card.getCardSprite().getTexture().toString();
        switch (command){
            case "Models/AlleBevegelseKortUtenPrioritet/BackUp.png":
                canMove(1,-1);
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-1.png":
                canMove(1,1);
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-2.png":
                canMove(2,1);
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-3.png":
                canMove(3,1);
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
        switch(gameMap.isExpressConveyerBelt(this.posX, this.posY)) {
            case "northNoTurn":
                canMoveConveyer(Direction.NORTH);
                break;
            case "eastNoTurn":
                canMoveConveyer(Direction.EAST);
                break;
            case "southNoTurn":
                canMoveConveyer(Direction.SOUTH);
                break;
            case "westNoTurn":
                canMoveConveyer(Direction.WEST);
                break;
            case "northRight":
                if (canMoveConveyer(Direction.NORTH) == 1)
                {this.rotate_right();}
                break;
            case "northLeft":
                if (canMoveConveyer(Direction.NORTH) == 1)
                {this.rotate_left();}
                break;
            case "eastRight":
                if (canMoveConveyer(Direction.EAST) == 1)
                {this.rotate_right();}
                break;
            case "eastLeft":
                if (canMoveConveyer(Direction.EAST) == 1)
                {this.rotate_left();}
                break;
            case "southRight":
                if (canMoveConveyer(Direction.SOUTH) == 1)
                {this.rotate_right();}
                break;
            case "southLeft":
                if (canMoveConveyer(Direction.SOUTH) == 1)
                {this.rotate_left();}
                break;
            case "westRight":
                if (canMoveConveyer(Direction.WEST) == 1)
                {this.rotate_right();}
                break;
            case "westLeft":
                if (canMoveConveyer(Direction.WEST) == 1)
                {this.rotate_left();}
                break;
            case "noBelt":
                break;
        }
        switch(gameMap.isConveyerBelt(this.posX, this.posY)) {
            case "northNoTurn":
                canMoveConveyer(Direction.NORTH);
                break;
            case "eastNoTurn":
                canMoveConveyer(Direction.EAST);
                break;
            case "southNoTurn":
                canMoveConveyer(Direction.SOUTH);
                break;
            case "westNoTurn":
                canMoveConveyer(Direction.WEST);
                break;
            case "northRight":
                if (canMoveConveyer(Direction.NORTH) == 1)
                {this.rotate_right();}
                break;
            case "northLeft":
                if (canMoveConveyer(Direction.NORTH) == 1)
                {this.rotate_left();}
                break;
            case "eastRight":
                if (canMoveConveyer(Direction.EAST) == 1)
                {this.rotate_right();}
                break;
            case "eastLeft":
                if (canMoveConveyer(Direction.EAST) == 1)
                {this.rotate_left();}
                break;
            case "southRight":
                if (canMoveConveyer(Direction.SOUTH) == 1)
                {this.rotate_right();}
                break;
            case "southLeft":
                if (canMoveConveyer(Direction.SOUTH) == 1)
                {this.rotate_left();}
                break;
            case "westRight":
                if (canMoveConveyer(Direction.WEST) == 1)
                {this.rotate_right();}
                break;
            case "westLeft":
                if (canMoveConveyer(Direction.WEST) == 1)
                {this.rotate_left();}
                break;
            case "noBelt":
                break;
        }
        if(gameMap.isSpinLeft(this.posX, this.posY)){
            System.out.println("SPIN!");
            this.rotate_left();
        }
        if (gameMap.isSpinRight(this.posX, this.posY)) {
            this.rotate_right();
        }
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
        }
        else if (gameMap.isRepairSite(this.posX, this.posY, this.turn) == 2) {
            this.setCheckpoint(this.posX, this.posY);
            if(this.damage != 0) {this.damage -=1;}
        }
        else if (gameMap.isRepairSite(this.posX, this.posY, this.turn) == 3) {
            this.setCheckpoint(this.posX, this.posY);
            if(this.damage > 1) {
                this.damage -= 2; // put in choice for option cards.
            }
            else if(this.damage == 1) {this.damage = 0;}
        }
    }

    public void died() {
        this.lives -= 1; // loose an option card of the players choice
        this.takeDamage();
        this.takeDamage();
        this.turn = 4;
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
        if (this.damage < 10) {
            this.damage += 1;
            cardHandler = RoboRallyDemo.getCardHandler();
            cardHandler.lockDown();
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
        else if (gameMap.wallNearby(this.dir, this.posX, this.posY)) {
            return 0;
        }
        else {
            return 1;
        }
    }

    public int checkConveyer(Direction dir) {
        if (dir == Direction.NORTH && this.posY + 1 == 12) {
            return -1;
        }
        else if (dir == Direction.EAST && this.posX + 1 == 12) {
            return -1;
        }
        else if (dir == Direction.SOUTH && this.posY - 1 == -1) {
            return -1;
        }
        else if (dir == Direction.WEST && this.posX - 1 == -1) {
            return -1;
        }
        else if (gameMap.wallNearby(dir, this.posX, this.posY)) {
            return 0;
        }
        else {
            return 1;
        } // add check for a second robot on the same conveyer target, if so move them both to original possition
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

    public int canMoveConveyer(Direction dir) {
            if (this.checkConveyer(dir) == 1) {
                this.moveConveyer(dir);
                return 1;
            }
            else if (this.checkConveyer(dir) == -1) {
                this.died();
                System.out.println("Moved off the map");
                return 0;
            }
            else if (this.checkConveyer(dir) == 0) {
                System.out.println("You hit a wall!");
                return 0;
            }
            else {
                return 0;
            }
    }

    public void moveConveyer(Direction dir) {
        if (dir == Direction.NORTH) {
            this.posY += 1;
            this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() + (1 * (this.tilePixelWidth / 6)));
        }
        else if (dir == Direction.EAST) {
            this.posX += 1;
            this.sprite.setPosition(this.sprite.getX() + (1 * (this.tilePixelWidth / 6)), this.sprite.getY());
        }
        else if (dir == Direction.SOUTH) {
            this.posY -= 1;
            this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() - (1 * (this.tilePixelWidth / 6)));
        }
        else if (dir == Direction.WEST) {
            this.posX -= 1;
            this.sprite.setPosition(this.sprite.getX() - (1 * (this.tilePixelWidth / 6)), this.sprite.getY());
        }
        if (gameMap.isHole(this.posX, this.posY)) {
            System.out.println("You fell into a hole!");
            this.died();
        }
    }
}
