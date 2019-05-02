package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class mainMenu {

    private static boolean mainRunning;
    Button serverButton;
    Button clientButton;
    Button startButton;


    SpriteBatch batch;

    private BitmapFont font;

    public mainMenu(SpriteBatch batch){
        mainRunning = true;
        this.batch=batch;
        font = new BitmapFont();
        font.setColor(255, 255, 255,1);

    }

    public void startMenu(){
        Texture clientTexture = new Texture(Gdx.files.internal("Models/black.jpg"));
        Sprite clientSprite = new Sprite(clientTexture);
        clientSprite.setPosition(150, 500);
        clientButton = new Button(150, 500, "clientButton", clientSprite);

        Texture serverTexture = new Texture(Gdx.files.internal("Models/black.jpg"));
        Sprite serverSprite = new Sprite(serverTexture);
        serverSprite.setPosition(400, 500);
        serverButton = new Button(400, 500, "serverButton", serverSprite);


        Texture startTexture = new Texture(Gdx.files.internal("Models/black.jpg"));
        Sprite startSprite = new Sprite(startTexture);
        startSprite.setPosition(650, 500);
        startButton = new Button(650, 500, "startButton", startSprite);
    }

    public void render(){
        clientButton.getSprite().draw(batch);
        font.draw(batch,"CLIENT", clientButton.getSprite().getX()+75, clientButton.getSprite().getY()+getCenterY(clientButton));

        serverButton.getSprite().draw(batch);
        font.draw(batch,"SERVER", serverButton.getSprite().getX()+75, serverButton.getSprite().getY()+getCenterY(serverButton));

        startButton.getSprite().draw(batch);
        font.draw(batch,"START", startButton.getSprite().getX()+75, startButton.getSprite().getY()+getCenterY(clientButton));
    }

    protected Float getCenterY(Button btn){
        return btn.getSprite().getHeight()/2;
    }

    protected Button getClientBtn(){
        return clientButton;
    }

    protected Button getServerBtn(){
        return serverButton;
    }

    protected Button getStartBtn(){
        return startButton;
    }

    public static boolean getMainRunning(){
        return mainRunning;
    }

    public static void setMainRunning(boolean isrunning){
        mainRunning=isrunning;
    }
}
