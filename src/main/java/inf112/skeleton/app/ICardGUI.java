package inf112.skeleton.app;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface ICardGUI {

    //create the cardSprites in the GUI
    //public void createCard(float posX, float posY);

    //return the Card Sprite
    public Sprite getCardSprite();

    //return the Cardslot Sprite
    //public Sprite getCardSlotSprite();

    public float getPosX();

    public float getPosY();

    public float getDefaultPosX();

    public float getDefaultPosY();

   // public void createCardSlots(float posX, float posY);
}
