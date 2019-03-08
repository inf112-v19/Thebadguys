package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public interface IRobot {

    float getPosX();

    float getPosY();

    // Returns the strin ascociated with the robot texture
    String getTextureStr();

    // Returns robot texture
    Texture getTexture();

    // Returns the robot sprite
    Sprite getSprite();

}
