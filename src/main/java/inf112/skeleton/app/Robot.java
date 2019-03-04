package inf112.skeleton.app;

public class Robot {
    private String sprite;
    private float posX = -4;
    private float posY = 6;
    private float[] checkpoint = {posX, posY};
    private int flagsPassed = 0;

    public Robot(String sprite) {
        this.sprite = sprite;
    }

    public Robot(String sprite, float[] checkpoint, int flagsPassed){
        this.sprite=sprite;
        this.checkpoint[0]=posX;
        this.checkpoint[1]=posY;
        this.flagsPassed = flagsPassed;
    }

    public float getPosX(){
        return posX;
    }

    public float getPosY(){
        return posY;
    }

    public String getSprite(){
        return sprite;
    }

    public float[] getCheckpoint() {
        return checkpoint;
    }

    public int getFlagsPassed() {
        return flagsPassed;
    }

    public void setCheckpoint(float[] checkpoint){
        this.checkpoint = checkpoint;
    }

    public void setFlagsPassed(int flagsPassed){
        this.flagsPassed = flagsPassed;
    }
}
