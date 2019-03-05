package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CardGUI implements ICardGUI{

    private Texture cardTexture;
    private Texture cardSlotTexture;
    private Sprite cardSprite;
    private float defaultPosX;
    private float defaultPosY;
    private float posX, posY;
    private Batch batch;

    //cards and deck
    private Cards Dummycard0;
    private Deck deck;



    public CardGUI(Batch batch, float posX, float posY){
        this.batch=batch;
        this.posX=posX;
        this.posY=posY;
        defaultPosX=posX;
        defaultPosY=posY;
        //textures
        cardTexture = new Texture(Gdx.files.internal("Models/cardTest.png"));

        //sprites
        cardSprite = new Sprite(cardTexture);


        //Object creation
        deck = new Deck();

        Dummycard0 = new Cards(cardSprite, "DummyNavn", 1);
        deck.addCard(Dummycard0);

    }

    //create a cardslot
    public void createCardSlots(){

    }

    //return cardSprite
    public Sprite getCardSprite(){
        return cardSprite;
    }

    //return cardSlotSprite



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
