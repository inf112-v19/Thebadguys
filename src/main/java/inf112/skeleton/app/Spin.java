package inf112.skeleton.app;

import map.GameMap;

public class Spin extends BoardElement {

    Spin(GameMap map){
        super(map);
    }

    public static void doSpin(IRobot robot){

        int posX = robot.getPosX();
        int posY = robot.getPosY();

        if(map.isSpinLeft(posX, posY)){
            System.out.println("SPIN!");
            rotate_left( robot);
        }
        if (map.isSpinRight(posX, posY)) {
            System.out.println("SPIN!");
            rotate_right(robot);
        }
    }

}
