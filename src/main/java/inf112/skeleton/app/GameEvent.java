package inf112.skeleton.app;



public class GameEvent {
    // temp static flag possitions
    private float flag1X;
    private float flag1Y;
    private float flag2X;
    private float flag2Y;
    private float flag3X;
    private float flag3Y;
    private float flag4X;
    private float flag4Y;

    public void updateCheckpoint(Robot robot){
            if (robot.getPosX()== flag1X && robot.getPosY() == flag1Y && robot.getFlagsPassed() == 0 ) {
                robot.setFlagsPassed(1);
                float[] checkpoint = {flag1X, flag1Y};
                robot.setCheckpoint(checkpoint);
            }
            else if (robot.getPosX()== flag2X && robot.getPosY() == flag2Y && robot.getFlagsPassed() == 1 ) {
                robot.setFlagsPassed(2);
                float[] checkpoint = {flag2X, flag2Y};
                robot.setCheckpoint(checkpoint);
            }
            else if (robot.getPosX()== flag3X && robot.getPosY() == flag3Y && robot.getFlagsPassed() == 2 ) {
                robot.setFlagsPassed(3);
                float[] checkpoint = {flag3X, flag3Y};
                robot.setCheckpoint(checkpoint);
            }
            else if (robot.getPosX()== flag4X && robot.getPosY() == flag4Y && robot.getFlagsPassed() == 3 ) {
                robot.setFlagsPassed(4);
                float[] checkpoint = {flag4X, flag4Y};
                robot.setCheckpoint(checkpoint);
            }

    }

}
