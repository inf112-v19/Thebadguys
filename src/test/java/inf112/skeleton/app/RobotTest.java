package inf112.skeleton.app;

import Grid.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class RobotTest {

    int posX = 0;
    int posY = 0;
    int[] startpos;
    Robot robot;


    @Test
    public void createRobotTest() {
        posX = 0;
        posY = 0;
        startpos = new int[]{Math.round(posX), Math.round(posY)};
        robot = new Robot(startpos);
        assertEquals(robot.getCheckpoint(), startpos);
        assertEquals(robot.getPosX(), 0);
        assertEquals(robot.getPosY(), 0);
        assertEquals(robot.getDirection(), Direction.NORTH);
        assertEquals(robot.getDamage(), 0);
        assertEquals(robot.getLives(), 3);
        assertEquals(robot.getFlagsPassed(), 0);
    }

    @Test
    public void damageLivesTest() {
        robot.setDamage(0);
        robot.setDamage(3);
        assertEquals(robot.getDamage(), 3);
        robot.setDamage(9);
        robot.takeDamage();
        assertEquals(robot.getLives(), 2);
    }
}
