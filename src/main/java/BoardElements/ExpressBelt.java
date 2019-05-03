package BoardElements;

import BoardElements.BeltElement;
import Grid.Direction;

import inf112.skeleton.app.IRobot;
import map.GameMap;

public class ExpressBelt extends BeltElement {

    public ExpressBelt(GameMap map){
        super(map);
    }

    public static void doExpressBelt(IRobot robot){

        int posX = robot.getPosX();
        int posY = robot.getPosY();

        switch(map.isExpressConveyerBelt(posX, posY)) {
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
                if (canMoveConveyor(Direction.NORTH, robot) == 1)
                {rotate_right(robot);}
                break;
            case "northLeft":
                if (canMoveConveyor(Direction.NORTH, robot) == 1)
                {rotate_left(robot);}
                break;
            case "eastRight":
                if (canMoveConveyor(Direction.EAST, robot) == 1)
                {rotate_right(robot);}
                break;
            case "eastLeft":
                if (canMoveConveyor(Direction.EAST, robot) == 1)
                {rotate_left(robot);}
                break;
            case "southRight":
                if (canMoveConveyor(Direction.SOUTH, robot) == 1)
                {rotate_right(robot);}
                break;
            case "southLeft":
                if (canMoveConveyor(Direction.SOUTH, robot) == 1)
                {rotate_left(robot);}
                break;
            case "westRight":
                if (canMoveConveyor(Direction.WEST, robot) == 1)
                {rotate_right(robot);}
                break;
            case "westLeft":
                if (canMoveConveyor(Direction.WEST, robot) == 1)
                {rotate_left(robot);}
                break;
            case "noBelt":
                break;
        }
    }
}
