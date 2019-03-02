package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CardGUI implements  ICardGUI{

    private Texture cardTexture;
    private Texture cardSlotTexture;
    private Sprite cardSprite;
    private Sprite cardSlotSprite1;
    private Sprite cardSlotSprite2;
    private float posX, posY;
    private Batch batch;

    //cards and deck
    private Cards Dummycard0;
    private Cards Dummycard1;
    private Cards Dummycard2;
    private Cards Dummycard3;
    private Cards Dummycard5;

    private Deck deck;



    public CardGUI(Batch batch){
        this.batch=batch;
        /*
        this.posX=posX;
        this.posY=posY;
        */
    }

    //create a card
    public void createCard(float posX, float posY){
        //textures
        cardTexture = new Texture(Gdx.files.internal("Models/cardTest.png"));

        //sprites
        cardSprite = new Sprite(cardTexture);


        //Object creation
        deck = new Deck();

        Dummycard0 = new Cards(cardSprite, "DummyNavn", 1);
        deck.addCard(Dummycard0);

        //setting the positions for the card
        //Dummycard0.getSprite().setPosition(posX, posY+200);

    }

    //create a cardslot
    public void createCardSlots(float posX, float posY){
        cardSlotTexture = new Texture(Gdx.files.internal("Models/cardSlotTest.png"));
        cardSlotSprite1= new Sprite(cardSlotTexture);
        cardSlotSprite1.setPosition(posX, posY);
    }

    //return cardSprite
    public Sprite getCardSprite(){
        return cardSprite;
    }

    //return cardSlotSprite
    public Sprite getCardSlotSprite(){ return cardSlotSprite1;}

    /*
    public Float getPosX(){
        return posX;
    }*/

    /*
    public Float getPosY(){
        return posY;
    }*/
}
