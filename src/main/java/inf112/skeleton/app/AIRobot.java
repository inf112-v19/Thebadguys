package inf112.skeleton.app;

import Grid.Direction;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import map.GameMap;

public class AIRobot {
    private Sprite sprite;
    private int lives = 3;
    private int damage = 0;
    private int flagsPassed = 0;
    private int[][] checkpoint;
    private TiledMap tiledMap = RoboRallyDemo.getTiledMap();
    private GameMap gameMap = RoboRallyDemo.getIGameMap();



    public void doTurn(){

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

    public void move(Cards card){
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




}
