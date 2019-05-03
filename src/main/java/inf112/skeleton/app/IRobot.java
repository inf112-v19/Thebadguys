package inf112.skeleton.app;

import Grid.Direction;
import com.badlogic.gdx.graphics.g2d.Sprite;

public interface IRobot {

    Boolean getAlive();

    int getPosX();

    int getPosY();

    Sprite getSprite();

    int[] getCheckpoint();

    int getFlagsPassed();

    Direction getDirection();

    int getLives();

    int getDamage();

    void died();

    void takeDamage();

    int checkNext(int amount);

    void canMove(int loops, int amount);

    void moveForward(int amount);

    void setPosX(int newX);

    void setPosY(int newY);

    void moveSprite(float x, float y);

    void rotateSprite(float z);

    int getTilePixelWidth();

    int getTilePixelHeight();

    void setDamage(int newDamage);

    void setAlive(boolean alive);

    void setCheckpoint(int x, int y);

    void setDirection(Direction dir);

    void rotate_right();

    void rotate_left();

}
