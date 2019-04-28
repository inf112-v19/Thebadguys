package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;
import java.util.Random;

// Enum representing card value
public enum CardValues implements Serializable {
     BACKUP10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png")), 14, "BCKUP")
    ,BACKUP20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png")), 24, "BCKUP")
    ,BACKUP30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png")), 34, "BCKUP")
    ,MOVE1Pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png")), 15, "MOVE1")
    ,MOVE1Pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png")), 25, "MOVE1")
    ,MOVE1Pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png")), 35, "MOVE1")
    ,MOVE2pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-2.png")), 16, "MOVE2")
    ,MOVE2pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-2.png")), 26, "MOVE2")
    ,MOVE2pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-2.png")), 36, "MOVE2")
    ,MOVE3pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-3.png")), 17, "MOVE3")
    ,MOVE3pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-3.png")), 27, "MOVE3")
    ,MOVE3pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-3.png")), 37, "MOVE3")
    ,ROTATE90pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png")), 11, "ROE90")
    ,ROTATE90pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png")), 21, "ROE90")
    ,ROTATE90pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png")), 31, "ROE90")
    ,ROTATE180pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png")), 12, "RE180")
    ,ROTATE180pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png")), 22, "RE180")
    ,ROTATE180pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png")), 32, "RE180")
    ,ROTATEC90pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png")), 13, "REC90")
    ,ROTATEC90pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png")), 23, "REC90")
    ,ROTATEC90pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png")), 33, "REC90");


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
