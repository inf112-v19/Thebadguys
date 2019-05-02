package Grid;

import java.util.Random;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public static Direction getRandomDir(){
        Random random = new Random();
        return Direction.values()[random.nextInt(Direction.values().length)];
    }

    public static Direction OppositeDirection(Direction dir){
        if (dir == NORTH){
            return SOUTH;
        }
        else if (dir == SOUTH){
            return NORTH;
        }
        else if (dir == WEST){
            return EAST;
        }
        else return WEST;
    }

}


