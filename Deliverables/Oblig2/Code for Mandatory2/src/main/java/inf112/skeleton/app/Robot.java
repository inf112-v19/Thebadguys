package inf112.skeleton.app;

public class Robot {
    private String sprite;
    private float posX;
    private float posY;

    public Robot(String sprite, float posX, float posY){
        this.sprite=sprite;
        this.posX=posX;
        this.posY=posY;
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
}
