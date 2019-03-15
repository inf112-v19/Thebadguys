package inf112.skeleton.app;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Robot {
    private Sprite sprite;
    private int posX = -4;
    private int posY = 6;
    private int[] checkpoint = {posX, posY};
    private int flagsPassed = 0;
    private int direction = 0;

    public void Robot() {

    }

    public void Robot(Sprite sprite){
        this.sprite = sprite;
    }

    public Robot(Sprite sprite, int[] startingPos, int flagsPassed){
        this.sprite = sprite;
        this.checkpoint = startingPos;
        this.posX = startingPos[0];
        this.posY = startingPos[1];
        this.flagsPassed = flagsPassed;
    }
    // a bunch of get functions
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

    public int getDirection() {
        return this.direction;
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
        if(this.getDirection() == 3){
            this.direction = 0;
        }
        else{
            this.direction += 1;
        }
        this.sprite.rotate(90);
    }

    public void rotate_left() {
        if(this.getDirection() == 0){
            this.direction = 3;
        }
        else{
            this.direction -= 1;
        }
        this.sprite.rotate(-90);
    }

    public void moveForward(int amount){ // does the actual changing of robot position and the robot sprite
        int current_direction = this.getDirection();
        if (current_direction == 0) {
            int newY = this.getPosY() + amount;
            this.setPosY(newY);
            this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() + 300 * amount); // temp moving sprite
        }
        else if (current_direction == 1) {
            int newX = this.getPosX() + amount;
            this.setPosX(newX);
            this.sprite.setPosition(this.sprite.getX() + 300 * amount, this.sprite.getY());
        }
        else if (current_direction == 2) {
            int newY = this.getPosY() - amount;
            this.setPosY(newY);
            this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() - 300 * amount);
        }
        else if (current_direction == 3) {
            int newX = this.getPosX() - amount;
            this.setPosX(newX);
            this.sprite.setPosition(this.sprite.getX() - 300 * amount, this.sprite.getY());
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
                this.moveForward(1); // added twice so we can incrimentally check for collisions
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
}
