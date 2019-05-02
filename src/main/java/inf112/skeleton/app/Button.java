package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import map.GameMap;

//import javax.smartcardio.Card;

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

    public boolean buttonClicked(int screenX, int screenY, Button button) { // TODO add parameters for id and singleplayermode boolean

        float NewScreenY = Gdx.graphics.getHeight() - screenY;
        if ((screenX > getPosX()) && (screenX < (getPosX() + buttonSprite.getWidth()) && (NewScreenY > getPosY())) && (NewScreenY < (getPosY() + buttonSprite.getHeight()))) {
            System.out.println("*click*"); // TODO remove
            switch (name) {

                case "powerDown_inactive":
                    System.out.println(robot);
                    if (!RoboRallyDemo.getSinglePlayerMode()) {
                        for (int i = 0; i < RoboRallyDemo.getClientCount(); i++) {
                            if (!RoboRallyDemo.getRobots()[i].getInitPowerdown()) {
                                RoboRallyDemo.getRobots()[i].setInitPowerdown(true);
                                System.out.println("Powerdown initiated for a robot"); // TODO remove
                            }
                        }
                    }

                    else if (!robot.getInitPowerdown()) { // perform check if single or multiplayer
                        System.out.println("in singleplayer");
                        robot.setInitPowerdown(true);
                        System.out.println("Powerdown initiated for player"); // TODO remove
                        return true;
                    }

                case "endRoundButton":
                    cardHandler.setisDone(true);
                    if (!RoboRallyDemo.getSinglePlayerMode()) {
                        Cards selectedCards[] = cardHandler.getSelectedCards();
                        String sendCards = "";
                        for(int i = 0; i < 5; i++) {
                            if (selectedCards[i] != null) {
                                sendCards += selectedCards[i].getName() + "~" + selectedCards[i].getPriority() + "~";
                            }
                        }
                        String string = "/r/"+RoboRallyDemo.getID()+ sendCards + "/e/";
                        System.out.println(sendCards); // TODO remove
                        RoboRallyDemo.getClient().getBackendClient().send(string.getBytes());
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
