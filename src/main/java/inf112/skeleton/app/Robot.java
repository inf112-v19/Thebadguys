package inf112.skeleton.app;

public class Robot {
    private String sprite;
    private int posX = -4;
    private int posY = 6;
    private int[] checkpoint = {posX, posY};
    private int flagsPassed = 0;
    private int direction = 0;

    public void Robot(String sprite){
        this.sprite = sprite;
    }

    public Robot(String sprite, int[] checkpoint, int flagsPassed){
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

    public String getSprite(){
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
        //this.sprite.rotate(90);
    }

    public void rotate_left() {
        if(this.getDirection() == 0){
            this.direction = 3;
        }
        else{
            this.direction -= 1;
        }
        //this.sprite.rotate(-90);
    }

    public void moveForward(int amount){
        int current_direction = this.getDirection();
        if (current_direction == 0) {
            int newY = this.getPosY() + amount;
            this.setPosY(newY);
        }
        else if (current_direction == 1) {
            int newX = this.getPosX() + amount;
            this.setPosX(newX);
        }
        else if (current_direction == 2) {
            int newY = this.getPosY() - amount;
            this.setPosY(newY);
        }
        else if (current_direction == 3) {
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
