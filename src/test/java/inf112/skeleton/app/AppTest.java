package inf112.skeleton.app;

import Grid.Direction;
import Grid.IGrid;
import Grid.MyGrid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import map.GameMap;
import map.IGameMap;
import map.MapTile;
import map.MovePlayerException;
import org.junit.Test;

import static org.junit.Assert.*;

//import javax.smartcardio.Card;

/**
 * Unit test for simple App.
 */
public class AppTest{
    //private Texture cardTexture = new Texture("Models/AlleBevegelseKortUtenPrioritet/genericCard.png");
    //private Sprite sprite = new Sprite(cardTexture);
    //RoboRallyDemo roboRallyDemo = new RoboRallyDemo();

    Cards testCard = new Cards(10,10, "testNavn", 100);
    CardSlots testCardSlot = new CardSlots(10,10);
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


    //creating a grid, and sets an element to a location on that grid, checks if the get method returns same element
    @Test
    public void gridSetGetTest(){
        IGrid grid = new MyGrid(12,12, null);
        grid.set(1,1, MapTile.PLAYER);
        assertEquals(MapTile.PLAYER, grid.get(1,1));
    }

    @Test
    public void mapGetCellTest(){
        IGrid grid = new MyGrid(12,12, MapTile.OPEN);
        grid.set(2, 2, MapTile.PLAYER);
        IGameMap map = new GameMap(grid);
        assertEquals(map.getCell(2,2),grid.get(2,2));
    }

    //checks that moveplayer method fails when moving into a wall
    @Test
    public void mapBadMoveTest(){
        IGrid grid = new MyGrid(12,12,MapTile.OPEN);
        //sets player position
        grid.set(1,1,MapTile.PLAYER);

        //sets walls where player can not move
        grid.set(1,2,MapTile.WALL);
        grid.set(1,0,MapTile.WALL);
        grid.set(2,1,MapTile.WALL);
        grid.set(0,1,MapTile.WALL);

        //sets up map
        IGameMap map = new GameMap(grid);

        //assert that you cannot move in this direction
        assertFalse(map.playerCanGo(Direction.NORTH));
        assertFalse(map.playerCanGo(Direction.SOUTH));
        assertFalse(map.playerCanGo(Direction.WEST));
        assertFalse(map.playerCanGo(Direction.EAST));

        try {
            map.movePlayer(Direction.NORTH);
            fail("Moving north should fail");
        } catch (MovePlayerException e) {
            assertTrue(true);
        }

        try {
            map.movePlayer(Direction.SOUTH);
            fail("Moving south should fail");
        } catch (MovePlayerException e) {
            assertTrue(true);
        }

        try {
            map.movePlayer(Direction.EAST);
            fail("Moving east should fail");
        } catch (MovePlayerException e) {
            assertTrue(true);
        }

        try {
            map.movePlayer(Direction.WEST);
            fail("Moving west should fail");
        } catch (MovePlayerException e) {
            assertTrue(true);
        }
    }

    @Test
   public void mapGoodMoveTest() throws MovePlayerException{
        IGrid grid = new MyGrid(12,12, MapTile.OPEN);
        //sets player position
        grid.set(2,3,MapTile.PLAYER);

        //sets up map
        IGameMap map = new GameMap(grid);

        //should all succeed
        map.movePlayer(Direction.NORTH);
        map.movePlayer(Direction.WEST);
        map.movePlayer(Direction.EAST);
        map.movePlayer(Direction.SOUTH);
        assertEquals(MapTile.PLAYER, map.getCell(2, 3));
    }




}
