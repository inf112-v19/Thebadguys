package inf112.skeleton.app;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.regex.*;

public class Robot {
    private Sprite sprite;
    private int posX = -4;
    private int posY = 6;
    private int[] checkpoint = {posX, posY};
    private int flagsPassed = 0;
    private int direction = 0;
    private int lives = 3;
    private int damage = 0;

    public Robot(Sprite sprite){
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

    public int getLives() {
        return this.lives;
    }

    public int getDamage() {
        return this.damage;
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
            this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() + 75 * amount); // temp moving sprite
        }
        else if (current_direction == 1) {
            int newX = this.getPosX() + amount;
            this.setPosX(newX);
            this.sprite.setPosition(this.sprite.getX() + 75 * amount, this.sprite.getY());
        }
        else if (current_direction == 2) {
            int newY = this.getPosY() - amount;
            this.setPosY(newY);
            this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() - 75 * amount);
        }
        else if (current_direction == 3) {
            int newX = this.getPosX() - amount;
            this.setPosX(newX);
            this.sprite.setPosition(this.sprite.getX() - 75 * amount, this.sprite.getY());
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
        String path = "Models/AlleBevegelseKortUtenPrioritet/"; // temp path
        Pattern backup = Pattern.compile(path + "Backup[0-9][0-9][0-9].png");
        Pattern move1 = Pattern.compile(path + "Move-1-[0-9][0-9][0-9].png");
        Pattern move2 = Pattern.compile(path + "Move-2-[0-9][0-9][0-9].png");
        Pattern move3 = Pattern.compile(path + "Move-3-[0-9][0-9][0-9].png");
        Pattern rotater = Pattern.compile(path + "Rotate-90-[0-9][0-9][0-9].png");
        Pattern rotatel = Pattern.compile(path + "Rotate-C90-[0-9][0-9][0-9].png");
        Pattern rotateu = Pattern.compile(path + "Rotate-180-[0-9][0-9][0-9].png");
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
    /*
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
    */
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
