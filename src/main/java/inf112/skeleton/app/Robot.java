package inf112.skeleton.app;

import Grid.Direction;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Robot {
    private Sprite sprite;
    private int posX = -4;
    private int posY = 6;
    private int[] checkpoint = {posX, posY};
    private int flagsPassed = 0;
    private Direction dir = Direction.NORTH;

    public void Robot(Sprite sprite){
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
        //this.sprite.rotate(90);
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
        //this.sprite.rotate(-90);
    }

    public void moveForward(int amount){
        Direction current_direction = this.getDirection();
        if (current_direction == Direction.NORTH) {
            int newY = this.getPosY() + amount;
            this.setPosY(newY);
        }
        else if (current_direction == Direction.EAST) {
            int newX = this.getPosX() + amount;
            this.setPosX(newX);
        }
        else if (current_direction == Direction.SOUTH) {
            int newY = this.getPosY() - amount;
            this.setPosY(newY);
        }
        else if (current_direction == Direction.WEST) {
            int newX = this.getPosX() - amount;
            this.setPosX(newX);
        }
        else {
            System.out.println("Something went terribly wrong");
        }
    }

    public void move(Cards card){
        String command = card.getName();
        switch (command){
            case "BackUp":
                this.moveForward(-1);
                break;
            case "Move-1":
                this.moveForward(1);
                break;
            case "Move-2":
                this.moveForward(1); // added twice so we can incrimentally check for collisions
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
}
