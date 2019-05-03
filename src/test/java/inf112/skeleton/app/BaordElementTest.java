package inf112.skeleton.app;

import Grid.Direction;
import Grid.IGrid;
import Grid.MyGrid;
import map.GameMap;
import map.MapTile;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import inf112.skeleton.app.Robot;


import java.util.Random;

public class BaordElementTest {

    int posX = 0;
    int posY = 0;
    int[] startpos;
    IRobot robot;
    Random random = new Random();
    int randInt;
    boolean randBool;
    Direction randDir;

    private GameMap gameMap = createGrid();

    //Initiating Board element objects.
    ExpressBelt ebelt = new ExpressBelt(gameMap);
    Belt belt = new Belt(gameMap);
    Spin spin = new Spin(gameMap);

    private GameMap createGrid() {
        IGrid grid = new MyGrid(12, 12, MapTile.OPEN);
        //sets conveyerbelt element on map
        grid.set(6, 11,MapTile.EXPRESSCONVEYERBELTNORTHTOEAST);
        grid.set(7, 11,MapTile.EXPRESSCONVEYERBELTEAST);
        grid.set(8, 11,MapTile.EXPRESSCONVEYERBELTEAST);
        grid.set(9, 11,MapTile.EXPRESSCONVEYERBELTEASTTOSOUTH);
        grid.set(9, 10,MapTile.EXPRESSCONVEYERBELTSOUTH);
        grid.set(9, 9, MapTile.EXPRESSCONVEYERBELTSOUTHTOWEST);
        grid.set(8, 9, MapTile.EXPRESSCONVEYERBELTWEST);
        grid.set(7, 9, MapTile.EXPRESSCONVEYERBELTWEST);
        grid.set(6, 9, MapTile.EXPRESSCONVEYERBELTWESTTONORTH);
        grid.set(6, 10,MapTile.EXPRESSCONVEYERBELTNORTH);

        grid.set(2, 5, MapTile.EXPRESSCONVEYERBELTEAST);
        grid.set(3, 5, MapTile.EXPRESSCONVEYERBELTEAST);
        grid.set(4, 5, MapTile.EXPRESSCONVEYERBELTEASTTONORTH);

        grid.set(4, 6, MapTile.EXPRESSCONVEYERBELTEASTTONORTH);
        grid.set(4, 7, MapTile.EXPRESSCONVEYERBELTNORTHTOWEST);
        grid.set(3, 7, MapTile.EXPRESSCONVEYERBELTWESTTOSOUTH);
        grid.set(3, 6, MapTile.EXPRESSCONVEYERBELTSOUTHTOEAST);

        grid.set(8, 4, MapTile.CONVEYERBELTNORTHTOEAST);
        grid.set(8, 3, MapTile.CONVEYERBELTWESTTONORTH);
        grid.set(9, 4, MapTile.CONVEYERBELTEASTTOSOUTH);
        grid.set(9, 3, MapTile.CONVEYERBELTSOUTHTOWEST);

        //setting repairsite elements on map
        grid.set(11, 0,MapTile.REPAIRSITE);
        grid.set(2, 10,MapTile.REPAIRSITE);

        grid.set(1,3,  MapTile.SPINLEFT);
        grid.set(1,6,  MapTile.SPINLEFT);
        grid.set(7,10, MapTile.SPINLEFT);
        grid.set(8,2,  MapTile.SPINRIGHT);
        grid.set(8,6 , MapTile.SPINRIGHT);
        grid.set(8,10, MapTile.SPINRIGHT);

        grid.set(9,0,  MapTile.LASERNORTH);

        grid.set(10, 5,MapTile.LASERNORTH);

        grid.set(7, 5, MapTile.LASERNORTH);

        grid.set(0, 9, MapTile.LASEREAST);

        grid.set(1, 1, MapTile.CHECKPOINT1);
        grid.set(1, 8, MapTile.CHECKPOINT2);
        grid.set(6, 7, MapTile.CHECKPOINT3);
        grid.set(10, 3,MapTile.CHECKPOINT4);

        grid.set(2, 2, MapTile.HOLE);
        grid.set(3,8, MapTile.HOLE);
        grid.set(9,7, MapTile.HOLE);
        grid.set(10,2, MapTile.HOLE);

        grid.set(9.0, -0.5, MapTile.WALL);
        grid.set(-0.5, 9.0, MapTile.WALL);

        grid.set(4.5,2.0,MapTile.WALL);
        grid.set(4.5,3.0,MapTile.WALL);
        grid.set(4.5,4.0,MapTile.WALL);
        grid.set(4.5,5.0,MapTile.WALL);
        grid.set(4.5,6.0,MapTile.WALL);
        grid.set(4.5,7.0,MapTile.WALL);
        grid.set(4.5,8.0,MapTile.WALL);
        grid.set(4.5,9.0,MapTile.WALL);

        grid.set(0.0,4.5,MapTile.WALL);
        grid.set(1.0,4.5,MapTile.WALL);
        grid.set(4.0,4.5,MapTile.WALL);
        grid.set(5.0,4.5,MapTile.WALL);
        grid.set(6.0,4.5,MapTile.WALL);
        grid.set(7.0,4.5,MapTile.WALL);
        grid.set(8.0,4.5,MapTile.WALL);
        grid.set(9.0,4.5,MapTile.WALL);
        grid.set(10.0,4.5,MapTile.WALL);

        return new GameMap(grid);

    }

    @Test
    public void WallTest(){

        startpos = new int[]{Math.round(posX), Math.round(posY)};
        robot = new TestBot(startpos);

        robot.setPosX(0);
        robot.setPosY(4);

        robot.moveForward(2);

        assertEquals(robot.getPosX(), 0);
        assertEquals(robot.getPosY(), 4);

    }

    @Test
    public void expressConveyorTest(){
        startpos = new int[]{Math.round(posX), Math.round(posY)};
        robot = new TestBot(startpos);

        GameMap map = createGrid();

        ExpressBelt ebelt = new ExpressBelt(map);

        ExpressBelt.doExpressBelt(robot);

        System.out.println(robot.getPosX());
        assertEquals(robot.getPosX(), 6);

    }

    @Test
    public void conveyorTest(){

    }

    @Test
    public void spinTest(){

    }
}
