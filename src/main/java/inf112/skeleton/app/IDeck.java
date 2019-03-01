package inf112.skeleton.app;

import java.util.HashMap;

public interface IDeck {

    public HashMap<Cards, Integer> getDeckList();

    public void addCard(Cards card);

   // public Cards getCard(Cards card);

}
