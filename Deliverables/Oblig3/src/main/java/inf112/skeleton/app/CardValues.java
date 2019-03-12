package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

// Enum representing card value
public enum CardValues {
    BACKUP(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png")), 10, "BackUp")
    , MOVE1(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png")), 10, "BackUp")
    , MOVE2(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-2.png")), 10, "BackUp")
    , MOVE3(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-3.png")), 10, "BackUp")
    , ROTATE90(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png")), 10, "BackUp")
    , ROTATE180(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png")), 10, "BackUp")
    , ROTATEC90(new Sprite(new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png")), 10, "BackUp");

    private final Sprite spr;
    private final int priority;
    private final String name;

    private CardValues(Sprite spr, int priority, String name){
        this.spr = spr;
        this.priority = priority;
        this.name = name;
    }
}
