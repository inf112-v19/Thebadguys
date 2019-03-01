package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Cards implements ICard{
    private Texture texture;
    private String name;
    private int priority;
    private Sprite sprite;

    public Cards(Sprite sprite, String name, int priority){
        this.sprite=sprite;
        this.name=name;
        this.priority=priority;
    }

    public Sprite getSprite(){return sprite;}

    //public Texture getTexture(){return texture;}

    public String getName(){
        return name;
    }

    public int getPriority(){
        return priority;
    }


}
