package inf112.skeleton.app;

import Grid.Direction;
import map.GameMap;

public class Belt extends BeltElement {


    public Belt(GameMap map) {
        super(map);
    }

    public void doBelt(Robot robot) {
        int posX = robot.getPosX();
        int posY = robot.getPosY();

        switch (RoboRallyDemo.getIGameMap().isConveyerBelt(posX, posY)) {
            case "northNoTurn":
                canMoveConveyor(Direction.NORTH, robot);
                break;
            case "eastNoTurn":
                canMoveConveyor(Direction.EAST, robot);
                break;
            case "southNoTurn":
                canMoveConveyor(Direction.SOUTH, robot);
                break;
            case "westNoTurn":
                canMoveConveyor(Direction.WEST, robot);
                break;
            case "northRight":
                if (canMoveConveyor(Direction.NORTH, robot) == 1) {
                    this.rotate_right(robot);
                }
                break;
            case "northLeft":
                if (canMoveConveyor(Direction.NORTH, robot) == 1) {
                    robot.rotate_left();
                }
                break;
            case "eastRight":
                if (canMoveConveyor(Direction.EAST, robot) == 1) {
                    robot.rotate_right();
                }
                break;
            case "eastLeft":
                if (canMoveConveyor(Direction.EAST, robot) == 1) {
                    robot.rotate_left();
                }
                break;
            case "southRight":
                if (canMoveConveyor(Direction.SOUTH, robot) == 1) {
                    robot.rotate_right();
                }
                break;
            case "southLeft":
                if (canMoveConveyor(Direction.SOUTH, robot) == 1) {
                    robot.rotate_left();
                }
                break;
            case "westRight":
                if (canMoveConveyor(Direction.WEST, robot) == 1) {
                    robot.rotate_right();
                }
                break;
            case "westLeft":
                if (canMoveConveyor(Direction.WEST, robot) == 1) {
                    robot.rotate_left();
                }
                break;
            case "noBelt":
                break;
        }
    }
}



