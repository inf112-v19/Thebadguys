package inf112.skeleton.app;

import Grid.Direction;
import map.GameMap;
import map.IGameMap;

public abstract class BoardElement {

    protected  static GameMap map;

    public BoardElement(GameMap map) {

        this.map = map;

    }

    public static void rotate_right(Robot robot) {
        if (robot.getDirection() == Direction.WEST) {
            robot.setDirection(Direction.NORTH);
        } else if (robot.getDirection() == Direction.NORTH) {
            robot.setDirection(Direction.EAST);
        } else if (robot.getDirection() == Direction.EAST) {
            robot.setDirection(Direction.SOUTH);
        } else if (robot.getDirection() == Direction.SOUTH) {
            robot.setDirection(Direction.WEST);
        }
        robot.sprite.rotate(-90);
    }

    public static void rotate_left(Robot robot) {
        if (robot.getDirection() == Direction.NORTH) {
            robot.setDirection(Direction.WEST);
        } else if(robot.getDirection()==Direction.WEST) {
            robot.setDirection(Direction.SOUTH);
        } else if(robot.getDirection()==Direction.SOUTH) {
            robot.setDirection(Direction.EAST);
        } else if(robot.getDirection()==Direction.EAST) {
            robot.setDirection(Direction.NORTH);
        }
        robot.sprite.rotate(90);
    }


}
