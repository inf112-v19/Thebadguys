package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CardGUI {

    private Texture card;
    private Texture cardSlot;
    private Sprite cardSprite;
    private Sprite cardSlotSprite;
    private float posX, posY;
    private Batch batch;


    public CardGUI(Batch batch){
        this.batch=batch;

    }

    public void create(float posX, float posY){
        card = new Texture(Gdx.files.internal("Models/cardTest.png"));
        cardSlot = new Texture(Gdx.files.internal("Models/cardSlotTest.png"));
        cardSprite = new Sprite(card);
        cardSlotSprite = new Sprite(cardSlot);
        cardSprite.setPosition(posX, posY);
        cardSlotSprite.setPosition(posX, posY);
    }

    public Sprite getCardSprite(){
        return cardSprite;
    }

    public Sprite getCardSlotSprite(){ return cardSlotSprite;}

    public void render(float posX, float posY){
        //cardSprite.setPosition(posX, posY);
        //batch.begin();
        //cardSprite.draw(batch);
        //System.out.println(cardGUI.getCardSprite());
        //cardGUI.getCardSprite().draw(batch);
        //batch.end();
    }
}
