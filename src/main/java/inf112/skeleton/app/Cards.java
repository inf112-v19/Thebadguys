package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Cards implements ICard {

    private Texture cardTexture;
    private Texture cardSlotTexture;
    private Sprite cardSprite;
    private float defaultPosX;
    private float defaultPosY;
    private float posX, posY;
    private Batch batch;
    private String name;
    private int priority;

    //cards and deck
    private Cards Dummycard0;
    private Deck deck;



    public Cards(float posX, float posY, String name, int priority, Sprite cardSprite){
        this.batch=batch;
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
        this.batch=batch;
        this.posX=posX;
        this.posY=posY;
        this.name=name;
        //this.cardSprite=cardSprite;
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
}
