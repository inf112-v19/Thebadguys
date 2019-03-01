package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CardGUI {

    private Texture cardTexture;
    private Texture cardSlotTexture;
    private Sprite cardSprite;
    private Sprite cardSlotSprite;
    //private float posX, posY;
    private Batch batch;

    //cards and deck
    private Cards card;
    private Deck deck;



    public CardGUI(Batch batch){
        this.batch=batch;

    }

    public void create(float posX, float posY){
        //textures
        cardTexture = new Texture(Gdx.files.internal("Models/cardTest.png"));
        cardSlotTexture = new Texture(Gdx.files.internal("Models/cardSlotTest.png"));

        //sprites
        cardSprite = new Sprite(cardTexture);
        cardSlotSprite = new Sprite(cardSlotTexture);

        //Object creation
        card = new Cards(cardSprite, "DummyNavn", 1);
        deck = new Deck();
        deck.addCard(card);

        //setting the positions for the card and cardslot
        cardSprite.setPosition(posX, posY);
        cardSlotSprite.setPosition(posX, posY);
    }

    public Sprite getCardSprite(){
        return cardSprite;
    }

    public void createCard(){

    }

    public Sprite getCardSlotSprite(){ return cardSlotSprite;}
}
