package inf112.skeleton.app;

import java.util.ArrayList;

public class Deck implements IDeck{
    private static ArrayList<Cards> DeckList;
    public Deck(){
        DeckList = new ArrayList<>();
    }

    //returns decklist
    public ArrayList<Cards> getDeckList(){
        return DeckList;
    }
}
