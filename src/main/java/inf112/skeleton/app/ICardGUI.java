package inf112.skeleton.app;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface ICardGUI {

    //create the cardSprites in the GUI
    public void create(float posX, float posY);

    //return the Card Sprite
    public Sprite getCardSprite();

    //return the Cardslot Sprite
    public Sprite getCardSlotSprite();
}
