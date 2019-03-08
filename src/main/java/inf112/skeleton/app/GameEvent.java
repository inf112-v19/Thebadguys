package inf112.skeleton.app;



public class GameEvent {
    // temp static flag possitions
    private int flag1X = 2;
    private int flag1Y = 2;
    private int flag2X = 2;
    private int flag2Y = 9;
    private int flag3X = 7;
    private int flag3Y = 8;
    private int flag4X = 11;
    private int flag4Y = 4;

    public void updateCheckpoint(Robot robot){
            if (robot.getPosX()== flag1X && robot.getPosY() == flag1Y && robot.getFlagsPassed() == 0 ) {
                robot.setFlagsPassed(1);
                int[] checkpoint = {flag1X, flag1Y};
                robot.setCheckpoint(checkpoint);
            }
            else if (robot.getPosX()== flag2X && robot.getPosY() == flag2Y && robot.getFlagsPassed() == 1 ) {
                robot.setFlagsPassed(2);
                int[] checkpoint = {flag2X, flag2Y};
                robot.setCheckpoint(checkpoint);
            }
            else if (robot.getPosX()== flag3X && robot.getPosY() == flag3Y && robot.getFlagsPassed() == 2 ) {
                robot.setFlagsPassed(3);
                int[] checkpoint = {flag3X, flag3Y};
                robot.setCheckpoint(checkpoint);
            }
            else if (robot.getPosX()== flag4X && robot.getPosY() == flag4Y && robot.getFlagsPassed() == 3 ) {
                robot.setFlagsPassed(4);
                int[] checkpoint = {flag4X, flag4Y};
                robot.setCheckpoint(checkpoint);
            }
    }

}
