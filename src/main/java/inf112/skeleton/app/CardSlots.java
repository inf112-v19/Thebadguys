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
    private boolean insideCardslot;



    public CardSlots(Batch batch, float posX, float posY, boolean insideCardSlot){
        this.batch=batch;
        this.posX=posX;
        this.posY=posY;
        this.insideCardslot=insideCardSlot;
        cardSlotTexture = new Texture(Gdx.files.internal("Models/CardSlot.jpg"));
        cardSlotSprite1= new Sprite(cardSlotTexture);
        cardSlotSprite1.setPosition(posX, posY);
    }

    public Sprite getCardSlotSprite(){ return cardSlotSprite1;}

    public boolean getIsInsideSlot(){
        return insideCardslot;
    }

    public void setInsideCardslot(boolean inside){
        insideCardslot=inside;
    }

    public Float getPosX(){
        return posX;
    }


    public Float getPosY(){
        return posY;
    }
}
