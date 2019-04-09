package inf112.skeleton.app;

import Grid.IGrid;
import Grid.MyGrid;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Pools;
import map.GameMap;
import map.IGameMap;
import map.MapTile;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class RoboRallyDemo implements ApplicationListener, InputProcessor {
    private static TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private int i = 0;
    private Cards CardButton;
    private Robot robot;
    private FitViewport viewPort;

    private static CardHandler cardHandler;

    private int tick = 0;
    private int turn = 0;


    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private float posX, posY;
    private TiledMapTileSet mapSet;
    private BitmapFont font;

    private static GameMap map;
    private IGrid grid;

    //create the initial state of the game
    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        //set the camera
        setCamera(w, h);
        //creation of the map
        tiledMap = new TmxMapLoader().load("Models/roborallymap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        createGrid();
        texture = new Texture(Gdx.files.internal("Models/tank.png"));
        sprite = new Sprite(texture);

        map = new GameMap(grid);
        posX = 0;
        posY = 0;
        int[] startpos = {Math.round(posX), Math.round(posY)};
        robot = new Robot(sprite, startpos);

        grid.set(robot.getPosX(), robot.getPosY(), MapTile.PLAYER);

        //creation of the robot


        sprite.setPosition(robot.getX1(), robot.getY1());

        //create the card that Is clicked
        Texture cardTexture = new Texture(Gdx.files.internal("Models/AlleBevegelseKortUtenPrioritet/genericCard.png"));
        cardHandler = new CardHandler(batch, robot, map);

        //loads map with elements and robot


        //create the end turn button
        buttonCreation(700, 500);

        font = new BitmapFont();

        //set the position of all the cardsprites
        cardHandler.setCardSprites();

        //create the 9 cards cards
        cardHandler.createDecklist();

        //creation of the 5 cardSlots
        cardHandler.createCardSlots();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    //rendering of the map and all the sprites
    @Override
    public void render() {
        //Gray background color
        Gdx.gl.glClearColor(128 / 255f, 128 / 255f, 128 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        Cards selectedCards[] = cardHandler.getSelectedCards();
        batch.begin();
        sprite.draw(batch);


        //check how mutch damage a robot has taken

        //check how mutch damage a robot has taken
        //rotation of sprite, rotate 90 degrees every 100th gametick
        if (i % 100 == 0) {
            for (int i = 0; i < selectedCards.length; i++) {
                System.out.println(selectedCards[i]);
            }
            System.out.println("\n");
        }

        doTurn();
        //draw the cardslots
        cardHandler.drawCardSlots();

        //draw button
        CardButton.getCardSprite().draw(batch);

        //draw Cards
        cardHandler.drawCards();

        i++;
        tick++;
        batch.end();
    }

    @Override
    //an atempt on an resize method
    public void resize(int width, int height) {
        viewPort.update(width, height);
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    //method for general movment. Keyboard at the moment, but will be cards in the future
    @Override
    public boolean keyDown(int keycode) {
        float moveAmount = 75.0f;
        if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
            moveAmount = 150.0f;

        if (keycode == Keys.LEFT)
            posX -= moveAmount;
        if (keycode == Keys.RIGHT)
            posX += moveAmount;
        if (keycode == Keys.UP)
            posY += moveAmount;
        if (keycode == Keys.DOWN)
            posY -= moveAmount;
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

    //this method is used to click and move a card around on the screen. if you click a card, the clickedCard, will get the right card from the Deck
    //if tou click a card and it is inside a cardSlot a boolean will change (isClicked=true), this I will use in the touchUp method
    // And if you click the Execute button the it will change a boolean value
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        cardHandler.click(button, screenX, screenY, CardButton);
        return false;
    }

    //if a card is inside a cardslot and it is released move it into the middle of the slot,
    //if it is outside then move it back to its default pos
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        cardHandler.letGo(screenX, screenY, CardButton);
        return false;
    }

    @Override
    //if a card is clicked on and draged, then move that clicked card
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        cardHandler.dragged(screenX, screenY, CardButton);
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

    private void createGrid() {
        grid = new MyGrid(12, 12, MapTile.OPEN);
        //sets conveyerbelt element on map
        grid.set(6, 11,MapTile.EXPRESSCONVEYERBELTNORTHTOEAST);
        grid.set(7, 11,MapTile.EXPRESSCONVEYERBELTEAST);
        grid.set(8, 11,MapTile.EXPRESSCONVEYERBELTEAST);
        grid.set(9, 11,MapTile.EXPRESSCONVEYERBELTEASTTOSOUTH);
        grid.set(9, 10,MapTile.EXPRESSCONVEYERBELTSOUTH);
        grid.set(9, 9, MapTile.EXPRESSCONVEYERBELTSOUTHTOWEST);
        grid.set(8, 9, MapTile.EXPRESSCONVEYERBELTWEST);
        grid.set(7, 9, MapTile.EXPRESSCONVEYERBELTWEST);
        grid.set(6, 9, MapTile.EXPRESSCONVEYERBELTWESTTONORTH);
        grid.set(6, 10,MapTile.EXPRESSCONVEYERBELTNORTH);

        grid.set(2, 5, MapTile.EXPRESSCONVEYERBELTEAST);
        grid.set(3, 5, MapTile.EXPRESSCONVEYERBELTEAST);
        grid.set(4, 5, MapTile.EXPRESSCONVEYERBELTEASTTONORTH);

        grid.set(4, 6, MapTile.EXPRESSCONVEYERBELTEASTTONORTH);
        grid.set(4, 7, MapTile.EXPRESSCONVEYERBELTNORTHTOWEST);
        grid.set(3, 7, MapTile.EXPRESSCONVEYERBELTWESTTOSOUTH);
        grid.set(3, 6, MapTile.EXPRESSCONVEYERBELTSOUTHTOEAST);

        grid.set(8, 4, MapTile.CONVEYERBELTNORTHTOEAST);
        grid.set(8, 3, MapTile.CONVEYERBELTWESTTONORTH);
        grid.set(9, 4, MapTile.CONVEYERBELTEASTTOSOUTH);
        grid.set(9, 3, MapTile.CONVEYERBELTSOUTHTOWEST);

        //setting repairsite elements on map
        grid.set(11, 0,MapTile.REPAIRSITE);
        grid.set(2, 10,MapTile.REPAIRSITE);

        grid.set(1,3,  MapTile.SPINLEFT);
        grid.set(1,6,  MapTile.SPINLEFT);
        grid.set(7,10, MapTile.SPINLEFT);
        grid.set(8,2,  MapTile.SPINRIGHT);
        grid.set(8,6 , MapTile.SPINRIGHT);
        grid.set(8,10, MapTile.SPINRIGHT);

        grid.set(3, 0, MapTile.LASERNORTH);

        grid.set(9,0,  MapTile.LASERNORTH);

        grid.set(10, 5,MapTile.LASERNORTH);

        grid.set(7, 5, MapTile.LASERNORTH);

        grid.set(0, 9, MapTile.LASEREAST);

        grid.set(1, 1, MapTile.CHECKPOINT1);
        grid.set(1, 8, MapTile.CHECKPOINT2);
        grid.set(6, 7, MapTile.CHECKPOINT3);
        grid.set(10, 3,MapTile.CHECKPOINT4);

        grid.set(2, 2, MapTile.HOLE);

        /*grid.set(3.0, -0.5, MapTile.WALL);
        grid.set(9.0, -0.5, MapTile.WALL);*/
        grid.set(-0.5, 9.0, MapTile.WALL);

        grid.set(4.5,2.0,MapTile.WALL);
        grid.set(4.5,3.0,MapTile.WALL);
        grid.set(4.5,4.0,MapTile.WALL);
        grid.set(4.5,5.0,MapTile.WALL);
        grid.set(4.5,6.0,MapTile.WALL);
        grid.set(4.5,7.0,MapTile.WALL);
        grid.set(4.5,8.0,MapTile.WALL);
        grid.set(4.5,9.0,MapTile.WALL);

        grid.set(0.0,4.5,MapTile.WALL);
        grid.set(1.0,4.5,MapTile.WALL);
        grid.set(2.0,4.5,MapTile.WALL);
        grid.set(3.0,4.5,MapTile.WALL);
        grid.set(4.0,4.5,MapTile.WALL);
        grid.set(5.0,4.5,MapTile.WALL);
        grid.set(6.0,4.5,MapTile.WALL);
        grid.set(7.0,4.5,MapTile.WALL);
        grid.set(8.0,4.5,MapTile.WALL);
        grid.set(9.0,4.5,MapTile.WALL);
        grid.set(10.0,4.5,MapTile.WALL);



    }

    public static TiledMap getTiledMap() {
        return tiledMap;
    }

    public static GameMap getIGameMap() {
        return map;
    }

    public void setCamera(float w, float h) {
        //camera that is for scaling viewpoint
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w * 6, h * 6);
        camera.translate(-400, -2700);
        viewPort = new FitViewport(w * 6, h * 6);
    }

    public void buttonCreation(float x, float y) {
        Texture buttonTexture = new Texture(Gdx.files.internal("Models/Button.png"));
        Sprite buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setPosition(x, y);
        CardButton = new Cards(x, y, "", 0, buttonSprite);
    }

    public void doTurn() {
        Cards selectedCards[] = cardHandler.getSelectedCards();
        if (selectedCards[0] != null && selectedCards[1] != null && selectedCards[2] != null && selectedCards[3] != null && selectedCards[4] != null && cardHandler.getisDone()) {
            robot.setTurn(0);
            if (turn >= 5) {
                    System.out.println("Ferdig med ein heil runde!");
                    for (int h = 0; h < 5; h++) {
                        //cardHandler.lockDown();
                    }
                        for (int v = 0; v < cardHandler.getSpritePos().size(); v++) {
                            cardHandler.getSpritePos().get(v).setPosition(10000, 10000);
                        }
                        turn = 0;
                        cardHandler.setNotFirst(true);
                        cardHandler.nullyFy();
                        cardHandler.setisDone(false);
                        cardHandler.setCardSprites();

                System.out.println("\n");
            }
            if (tick % 40 == 0) {
                if (robot.getTurn() < 4) {
                robot.move(selectedCards[turn]);
                map.move(selectedCards[turn]);
                System.out.println("DidTURN "+(turn+1));
                robot.getSprite().draw(batch);
                }
                turn++;
            }
        }
    }
    public static CardHandler getCardHandler(){
        System.out.println(cardHandler);
        return cardHandler;
    }

}