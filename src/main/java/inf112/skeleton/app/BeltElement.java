package inf112.skeleton.app;

import Grid.Direction;
import map.GameMap;


public abstract class BeltElement extends BoardElement{


    public BeltElement(GameMap map){
         super (map);
    }

    public int checkConveyor(Direction dir, Robot robot) {

        int posX = robot.getPosX();
        int posY = robot.getPosY();

        if (dir == Direction.NORTH && posY + 1 == 12) {
            return -1;
        }
        else if (dir == Direction.EAST && posX + 1 == 12) {
            return -1;
        }
        else if (dir == Direction.SOUTH && posY - 1 == -1) {
            return -1;
        }
        else if (dir == Direction.WEST && posX - 1 == -1) {
            return -1;
        }
        else if (map.wallNearby(dir, posX, posY)) {
            return 0;
        }
        else {
            return 1;
        } // add check for a second robot on the same conveyer target, if so move them both to original position
    }

    public int canMoveConveyor(Direction dir, Robot robot) {
        if (checkConveyor(dir, robot) == 1) {
            moveConveyor(dir, robot);
            return 1;
        }
        else if (checkConveyor(dir, robot) == -1) {
            robot.died();
            System.out.println("Moved off the map");
            return 0;
        }
        else if (checkConveyor(dir, robot) == 0) {
            System.out.println("You hit a wall!");
            return 0;
        }
        else {
            return 0;
        }
    }

    public void moveConveyor(Direction dir, Robot robot) {

        int posX = robot.getPosX();
        int posY = robot.getPosY();

        if (dir == Direction.NORTH) {
            posY += 1;
            robot.sprite.setPosition(robot.sprite.getX(), robot.sprite.getY() + (1 * (robot.getTilePixelWidth() / 6)));
        }
        else if (dir == Direction.EAST) {
            posX += 1;
            robot.sprite.setPosition(robot.sprite.getX() + (1 * (robot.getTilePixelWidth() / 6)), robot.sprite.getY());
        }
        else if (dir == Direction.SOUTH) {
            posY -= 1;
            robot.sprite.setPosition(robot.sprite.getX(), robot.sprite.getY() - (1 * (robot.getTilePixelWidth() / 6)));
        }
        else if (dir == Direction.WEST) {
            posX -= 1;
            robot.sprite.setPosition(robot.sprite.getX() - (1 * (robot.getTilePixelWidth() / 6)), robot.sprite.getY());
        }
        if (map.isHole(posX, posY)) {
            System.out.println("You fell into a hole!");
            robot.died();
        }
    }
}
