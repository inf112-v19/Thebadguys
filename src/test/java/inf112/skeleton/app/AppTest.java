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
public class AppTest
{

    private Texture cardTexture = new Texture(Gdx.files.internal("Models/cardTest.PNG"));
    private Sprite sprite = new Sprite(cardTexture);
    private Deck deck = new Deck();


    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    //trying to make a card and checking that the values are true
    @Test
    public void createCardTest(){
     //       assertEquals(testCard.getSprite(), sprite);
       //     assertEquals(testCard.getName(), "Move-1");
         //   assertEquals(testCard.getPriority(), 100);
    }

    //trying to put a card into a decklist and check if it exists in the HashMap

}
