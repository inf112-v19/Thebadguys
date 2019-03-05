package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public interface ICard  {

    public Sprite getSprite();

    //public Texture getTexture();

    public String getName();

    public int getPriority();
}
