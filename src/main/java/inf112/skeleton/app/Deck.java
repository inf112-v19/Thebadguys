package inf112.skeleton.app;

import java.util.HashMap;

public class Deck implements IDeck{
    private HashMap<Cards, Integer> DeckList;
    public Deck(){
        DeckList = new HashMap<>();
    }

    public HashMap<Cards, Integer> getDeckList(){
        return DeckList;
    }

    public void addCard(Cards card){
        //sjekker om kortet er i listen, inkrementer key om den finst
        if(DeckList.containsKey(card)){
            Integer count = DeckList.get(card);
            DeckList.put(card, count++);
        }
        DeckList.put(card, 1);
    }

    /*
    public Cards getCard(Cards card) {
        if(DeckList.containsKey(card)){

        }
    }*/

}
