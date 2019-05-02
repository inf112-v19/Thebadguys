package inf112.skeleton.app;

import Grid.Direction;
import Grid.IGrid;
import Grid.MyGrid;
import map.GameMap;
import map.IGameMap;
import map.MapTile;
import map.MovePlayerException;
import org.junit.Test;

import static org.junit.Assert.*;

public class GridTest {



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
        assertEquals(grid.get(2,2),grid.get(2,2));
    }
}
