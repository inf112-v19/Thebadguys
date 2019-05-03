package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Button implements IButton {


    CardHandler cardHandler;
    Robot robot;

    private int posX;
    private int posY;

    private String name;
    private Sprite buttonSprite;

    public Button(int posX, int posY, String name, Sprite buttonSprite) {
        this.posX = posX;
        this.posY = posY;
        this.buttonSprite = buttonSprite;
        this.name = name;
        this.cardHandler = RoboRallyDemo.getCardHandler();
        this.robot = RoboRallyDemo.getRobot();
        createButton(posX, posY);
    }

    public void createButton(int x, int y) {
        this.buttonSprite.setPosition(x, y);
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public Sprite getSprite() {
        return buttonSprite;
    }

    public boolean buttonClicked(int screenX, int screenY, Button button) {

        float NewScreenY = Gdx.graphics.getHeight() - screenY;
        if ((screenX > getPosX()) && (screenX < (getPosX() + buttonSprite.getWidth()) && (NewScreenY > getPosY())) && (NewScreenY < (getPosY() + buttonSprite.getHeight()))) {
            switch (name) {
                case "powerDown_inactive":
                    if (!RoboRallyDemo.getSinglePlayerMode()) {
                        if (!RoboRallyDemo.getRobots()[RoboRallyDemo.getID()].getInitPowerdown()) {
                            RoboRallyDemo.getRobots()[RoboRallyDemo.getID()].setInitPowerdown(true);
                            String message = "/p/" + RoboRallyDemo.getID() + "/e/";
                            RoboRallyDemo.getClient().getBackendClient().send(message.getBytes());
                        }
                    }

                    else if (!robot.getInitPowerdown()) { // perform check if single or multiplayer
                        robot.setInitPowerdown(true);
                        return true;
                    }

                case "endRoundButton":
                    if (RoboRallyDemo.areCardSlotsFull()) {
                        cardHandler.setisDone(true);
                        if (!RoboRallyDemo.getSinglePlayerMode()) {
                            Cards selectedCards[] = cardHandler.getSelectedCards();
                            String sendCards = "";
                            for (int i = 0; i < 5; i++) {
                                if (selectedCards[i] != null) {
                                    sendCards += selectedCards[i].getName() + "~" + selectedCards[i].getPriority() + "~";
                                }
                            }
                            String string = "/r/" + RoboRallyDemo.getID() + sendCards + "/e/";
                            RoboRallyDemo.getClient().getBackendClient().send(string.getBytes());
                        }
                    }
                case "clientButton":
                    return true;
                case "startButton":
                    return true;
                case "serverButton":
                    return true;
            }
        }
        return false;
    }
}
