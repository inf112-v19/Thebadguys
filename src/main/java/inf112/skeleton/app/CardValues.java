package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;
import java.util.Random;

// Enum representing card value
public enum CardValues implements Serializable {
     BACKUP10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png")), 10, "BCKUP")
    ,BACKUP20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png")), 20, "BCKUP")
    ,BACKUP30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png")), 30, "BCKUP")
    ,MOVE1Pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png")), 10, "MOVE1")
    ,MOVE1Pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png")), 20, "MOVE1")
    ,MOVE1Pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png")), 30, "MOVE1")
    ,MOVE2pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-2.png")), 10, "MOVE2")
    ,MOVE2pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-2.png")), 20, "MOVE2")
    ,MOVE2pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-2.png")), 30, "MOVE2")
    ,MOVE3pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-3.png")), 10, "MOVE3")
    ,MOVE3pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-3.png")), 20, "MOVE3")
    ,MOVE3pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-3.png")), 30, "MOVE3")
    ,ROTATE90pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png")), 10, "ROE90")
    ,ROTATE90pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png")), 20, "ROE90")
    ,ROTATE90pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png")), 30, "ROE90")
    ,ROTATE180pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png")), 10, "RE180")
    ,ROTATE180pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png")), 20, "RE180")
    ,ROTATE180pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png")), 30, "RE180")
    ,ROTATEC90pri10(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png")), 10, "REC90")
    ,ROTATEC90pri20(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png")), 20, "REC90")
    ,ROTATEC90pri30(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png")), 30, "REC90");


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
