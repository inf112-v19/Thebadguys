package inf112.skeleton.app;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface ICard {
    //return the Card Sprite
    Sprite getCardSprite();

    float getPosX();

    float getPosY();

    float getDefaultPosX();

    float getDefaultPosY();

    void setCardSprite(Sprite sprite);
}
