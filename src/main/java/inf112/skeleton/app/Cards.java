package inf112.skeleton.app;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;

public class Cards implements ICard, Serializable {

    private Sprite cardSprite;
    private float defaultPosX;
    private float defaultPosY;
    private float posX, posY;
    private String name;
    private int priority;

    public Cards(float posX, float posY, String name, int priority, Sprite cardSprite){
        this.posX=posX;
        this.posY=posY;
        this.name=name;
        this.cardSprite=cardSprite;
        this.priority=priority;
        defaultPosX=posX;
        defaultPosY=posY;
    }

    //extra constructor for testing
    public Cards(float posX, float posY, String name, int priority){
        this.posX=posX;
        this.posY=posY;
        this.name=name;
        this.priority=priority;
        defaultPosX=posX;
        defaultPosY=posY;
    }

    //returns the name
    public String getName(){
        return name;
    }

    //returns the priority
    public int getPriority(){
        return priority;
    }

    //return cardSprite
    public Sprite getCardSprite(){
        return cardSprite;
    }

    public float getPosX(){
        return posX;
    }

    public float getPosY(){
        return posY;
    }

    public float getDefaultPosX(){
        return defaultPosX;
    }

    public float getDefaultPosY(){
        return defaultPosY;
    }

    //makes it possible to change the sprite of a Card
    public void setCardSprite(Sprite sprite){this.cardSprite=sprite;}
}
