package Grid;

import java.util.Random;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public static Direction getRandomDir(){
        Random random = new Random();
        return Direction.values()[random.nextInt(Direction.values().length)];
    }

}


