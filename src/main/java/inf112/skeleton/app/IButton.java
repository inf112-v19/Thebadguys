package inf112.skeleton.app;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface IButton {

    void initButton(int posX, int posY, String name, Sprite sprite, RoboRallyDemo game, CardHandler cardHandler, Robot robot);

    boolean buttonClicked(int screenX, int screenY, Button button);
}
