package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class RoboRallyDemo implements ApplicationListener, InputProcessor {
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    OrthographicCamera camera;
    CardGUI cardGUI;

    CardGUI cardSlot0;
    CardGUI cardSlot1;
    CardGUI cardSlot2;
    CardGUI cardSlot3;
    CardGUI cardSlot4;


    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private float posX, posY;


    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w * 4  ,h * 4);
        camera.update();

        tiledMap = new TmxMapLoader().load("Models/roborallymap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        texture = new Texture(Gdx.files.internal("Models/tank.png"));
        sprite = new Sprite(texture);


        posX = -4;
        posY = 6;
        sprite.setPosition(posX,posY);

        cardGUI = new CardGUI(batch);
        cardGUI.createCard(posX, posY);

        cardSlot0 = new CardGUI(batch);
        cardSlot1 = new CardGUI(batch);
        cardSlot2 = new CardGUI(batch);
        cardSlot3 = new CardGUI(batch);
        cardSlot4 = new CardGUI(batch);

        cardSlot0.createCardSlots(posX, posY);
        cardSlot1.createCardSlots(posX+185, posY);
        cardSlot2.createCardSlots(posX+370, posY);
        cardSlot3.createCardSlots(posX+555, posY);
        cardSlot4.createCardSlots(posX+740, posY);


        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        //sprite.setPosition(posX,posY);

        //denne kontrolerer movment
        cardGUI.getCardSprite().setPosition(posX, posY);
        //cardGUI.getCardSlotSprite().setPosition(posX,posY+100);
        batch.begin();
        sprite.draw(batch);


        cardSlot0.getCardSlotSprite().draw(batch);
        cardSlot1.getCardSlotSprite().draw(batch);
        cardSlot2.getCardSlotSprite().draw(batch);
        cardSlot3.getCardSlotSprite().draw(batch);
        cardSlot4.getCardSlotSprite().draw(batch);

        cardGUI.getCardSprite().draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public boolean keyDown(int keycode) {
        float moveAmount = 75.0f;
        if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
            moveAmount = 150.0f;

        if(keycode == Keys.LEFT)
            posX-=moveAmount;
        if(keycode == Keys.RIGHT)
            posX+=moveAmount;
        if(keycode == Keys.UP)
            posY+=moveAmount;
        if(keycode == Keys.DOWN)
            posY-=moveAmount;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        /*
        if(button == Buttons.LEFT){
            posX = screenX - sprite.getWidth()/2;
            posY = Gdx.graphics.getHeight() - screenY - sprite.getHeight()/2;
        }
        if(button == Buttons.RIGHT){
            posX = Gdx.graphics.getWidth()/2 - sprite.getWidth()/2;
            posY = Gdx.graphics.getHeight()/2 - sprite.getHeight()/2;
        }
        */

        if(button == Buttons.LEFT){
            posX = screenX - cardGUI.getCardSprite().getWidth()/2;
            posY = Gdx.graphics.getHeight() - screenY - cardGUI.getCardSprite().getHeight()/2;
            //cardGUI.render(posX, posY);
        }
        if(button == Buttons.RIGHT){
            posX = Gdx.graphics.getWidth()/2 - cardGUI.getCardSprite().getWidth()/2;
            posY = Gdx.graphics.getHeight()/2 - cardGUI.getCardSprite().getHeight()/2;
            //cardGUI.render(posX, posY);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        posX = screenX - cardGUI.getCardSprite().getWidth()/2;
        posY = Gdx.graphics.getHeight() - screenY - cardGUI.getCardSprite().getHeight()/2;

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}