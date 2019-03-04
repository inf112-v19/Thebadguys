package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CardSlots {

    private Texture cardSlotTexture;
    private Sprite cardSlotSprite1;
    private Sprite cardSlotSprite2;
    private float posX, posY;
    private Batch batch;



    public CardSlots(Batch batch, float posX, float posY){
        this.batch=batch;
        this.posX=posX;
        this.posY=posY;
        cardSlotTexture = new Texture(Gdx.files.internal("Models/cardSlotTest.png"));
        cardSlotSprite1= new Sprite(cardSlotTexture);
        cardSlotSprite1.setPosition(posX, posY);
    }

    public Sprite getCardSlotSprite(){ return cardSlotSprite1;}

    public Float getPosX(){
        return posX;
    }


    public Float getPosY(){
        return posY;
    }
}
