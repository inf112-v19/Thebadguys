package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class mainMenu {

    private static boolean mainRunning;
    Cards serverButton;
    Cards clientButton;
    Cards startButton;
    Batch batch;
    private BitmapFont font;

    public mainMenu(Batch batch){
        mainRunning = true;
        this.batch=batch;
        font = new BitmapFont();
        font.setColor(255, 255, 255,1);

    }

    public void startMenu(){
        Texture clientTexture = new Texture(Gdx.files.internal("Models/black.jpg"));
        Sprite clientSprite = new Sprite(clientTexture);
        clientSprite.setPosition(150, 500);
        clientButton = new Cards(150, 500, "", 0, clientSprite);

        Texture serverTexture = new Texture(Gdx.files.internal("Models/black.jpg"));
        Sprite serverSprite = new Sprite(serverTexture);
        serverSprite.setPosition(400, 500);
        serverButton = new Cards(400, 500, "", 0, serverSprite);


        Texture startTexture = new Texture(Gdx.files.internal("Models/black.jpg"));
        Sprite startSprite = new Sprite(startTexture);
        startSprite.setPosition(650, 500);
        startButton = new Cards(650, 500, "", 0, startSprite);
    }

    public void render(){
        clientButton.getCardSprite().draw(batch);
        font.draw(batch,"CLIENT", clientButton.getCardSprite().getX()+75, clientButton.getCardSprite().getY()+getCardCenterY(clientButton));

        serverButton.getCardSprite().draw(batch);
        font.draw(batch,"SERVER", serverButton.getCardSprite().getX()+75, serverButton.getCardSprite().getY()+getCardCenterY(serverButton));

        startButton.getCardSprite().draw(batch);
        font.draw(batch,"START", startButton.getCardSprite().getX()+75, startButton.getCardSprite().getY()+getCardCenterY(clientButton));
    }

    protected Float getCardCenterY(Cards card){
        return card.getCardSprite().getHeight()/2;
    }

    protected Cards getClientBtn(){
        return clientButton;
    }

    protected Cards getServerBtn(){
        return serverButton;
    }

    protected Cards getStartBtn(){
        return startButton;
    }

    public static boolean getMainRunning(){
        return mainRunning;
    }

    public static void setMainRunning(boolean isrunning){
        mainRunning=isrunning;
    }
}
