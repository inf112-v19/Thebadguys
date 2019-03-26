package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

// Enum representing card value
public enum CardValues {
    BACKUP1(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png")), 10, "BACKUP")
    , MOVE12(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png")), 10, "MOVE1")
    , BACKUP(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png")), 10, "BACKUP")
    , MOVE1(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png")), 10, "MOVE1")
    , MOVE2(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-2.png")), 10, "MOVE2")
    , MOVE3(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-3.png")), 10, "MOVE3")
    , ROTATE90(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png")), 10, "ROTATE90")
    , ROTATE180(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png")), 10, "ROTATE180")
    , ROTATEC90(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png")), 10, "ROTATEC90");

    private final Sprite spr;
    private final int priority;
    private final String name;

    CardValues(Sprite spr, int priority, String name){
        this.spr = spr;
        this.priority = priority;
        this.name = name;
    }

    public Sprite getSprite(){
        return spr;
    }

    public int getPriority() {
        return priority;
    }

    public String getName(){
        return name;
    }

    public static CardValues getRandomCard(){
        Random random = new Random();
        return CardValues.values()[random.nextInt(CardValues.values().length)];
    }

}
