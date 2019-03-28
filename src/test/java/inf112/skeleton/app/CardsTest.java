package inf112.skeleton.app;

import Grid.Direction;
import Grid.IGrid;
import Grid.MyGrid;
import map.GameMap;
import map.IGameMap;
import map.MapTile;
import map.MovePlayerException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardsTest {
    Cards testCard = new Cards(10,10, "testNavn", 100);
    CardSlots testCardSlot = new CardSlots(10,10);
    Deck deck= new Deck();
    float test=10;
    //trying to make a card and checking that the values are what I expect
    @Test
    public void createCardTest(){
        assertEquals(testCard.getName(), "testNavn");
        assertEquals(testCard.getPriority(), 100);
        assertEquals(testCard.getDefaultPosX(), test, 0);
        assertEquals(testCard.getDefaultPosY(), test, 0);
        assertEquals(testCard.getDefaultPosY(), testCard.getDefaultPosY(), 0);
        assertEquals(testCard.getDefaultPosX(), testCard.getDefaultPosX(), 0);
    }

    //creating a cardslot and checking if the paramaters are what I expect
    @Test
    public void createCardSlotTest(){
        assertEquals(testCardSlot.getPosX(),  test, 0);
        assertEquals(testCardSlot.getPosY(),  test, 0);
    }

    //creating a deck, and checking if it creates a empty arrayList
    @Test
    public void createDeckTest(){
        assertEquals(deck.getDeckList().size(), 0);
    }

    //adding a card to the deck and checking if the size is 1, and if the card is the same as I added.
    @Test
    public void addCardToDeckTest(){
        deck.getDeckList().add(testCard);
        assertEquals(deck.getDeckList().size(), 1);
        assertEquals(deck.getDeckList().get(0), testCard);
    }
    //trying to put a card into a decklist and check if it exists in the HashMap

}
