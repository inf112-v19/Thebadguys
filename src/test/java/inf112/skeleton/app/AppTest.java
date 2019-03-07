package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.junit.Test;

import javax.smartcardio.Card;

/**
 * Unit test for simple App.
 */
public class AppTest{
    //private Texture cardTexture = new Texture("Models/AlleBevegelseKortUtenPrioritet/genericCard.png");
    //private Sprite sprite = new Sprite(cardTexture);
    //RoboRallyDemo roboRallyDemo = new RoboRallyDemo();

    Cards testCard = new Cards(10,10, "testNavn", 100);
    CardSlots testCardSlot = new CardSlots(10,10, false);
    Deck deck= new Deck();
    float test=10;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


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
        assertEquals(testCardSlot.getIsInsideSlot(), false);
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
        deck.addCard(testCard);
        assertEquals(deck.getDeckList().size(), 1);
        assertEquals(deck.getCard(0), testCard);
    }
    //trying to put a card into a decklist and check if it exists in the HashMap

}
