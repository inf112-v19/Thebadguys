package inf112.skeleton.app;

import Grid.IGrid;
import Grid.MyGrid;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import map.GameMap;
import map.MapTile;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class RoboRallyDemo implements ApplicationListener, InputProcessor {
    private static TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private int i = 0;
    private int tick = 0;
    private static int turn = 0;
    private static Boolean isEndOfTurn = false;

    private Button endTurnButton;
    //private Cards PowerdownButton;
    private Button powerdownButton;
    private Robot robot;
    private FitViewport viewPort;
    private static CardHandler cardHandler;
    private Cards statBoard0;
    private Cards card;

    private Cards selectedCards[];

    private boolean firstRund=true;
    private mainMenu mainMenu;

    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private float posX, posY;
    private BitmapFont font;
    private Sprite statBoardSprite;
    private static GameMap map;
    private IGrid grid;


    //create the initial state of the game
    @Override
    public void create() {
        if(firstRund){
            batch = new SpriteBatch();
            float w = Gdx.graphics.getWidth();
            float h = Gdx.graphics.getHeight();
            //set the camera
            setCamera(w, h);
            mainMenu = new mainMenu(batch, this);
            tiledMap = new TmxMapLoader().load("Models/roborallymap.tmx");
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
            //Cards selectedCards[] = cardHandler.getSelectedCards();
            //batch = new SpriteBatch();
            createGrid();
            texture = new Texture(Gdx.files.internal("Models/tank.png"));
            sprite = new Sprite(texture);

            map = new GameMap(grid);
            posX = 0;
            posY = 0;
            int[] startpos = {Math.round(posX), Math.round(posY)};
            robot = new Robot(sprite, startpos);

            grid.set(robot.getPosX(), robot.getPosY(), MapTile.PLAYER);
            sprite.setPosition(robot.getX1(), robot.getY1());

            //create the card that Is clicked
            Texture cardTexture = new Texture(Gdx.files.internal("Models/AlleBevegelseKortUtenPrioritet/genericCard.png"));
            cardHandler = new CardHandler(batch, robot, map);

            font = new BitmapFont();

            //create the end turn button
            endTurnButtonCreation(700, 510);
            statBoardCreation(700,910);

            //set the position of all the cardsprites
            cardHandler.setCardSprites();

            //create the 9 cards cards
            cardHandler.createInitialDecklist();

            //creation of the 5 cardSlots
            cardHandler.createCardSlots();
            firstRund=false;
        }
        if(mainMenu.getMainRunning()) {
            mainMenu.startMenu();
            //creation of the map
        }else{

            //creation of the map

        }
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void dispose() {
        batch.dispose();
//        texture.dispose();
    }

    //rendering of the map and all the sprites
    @Override
    public void render() {
        Gdx.gl.glClearColor(128 / 255f, 128 / 255f, 128 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(mainMenu.getMainRunning()){
            batch.begin();
            mainMenu.render();
        }
        else {
            camera.update();
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
            //Cards selectedCards[] = cardHandler.getSelectedCards();
            batch.begin();
            sprite.draw(batch);
            doTurn();
            //draw the cardslots
            cardHandler.drawCardSlots();
            cardHandler.drawLockedList();
            //draw button
            powerdownButtonCreation(700, 710);

            endTurnButton.getSprite().draw(batch);

            statBoard0.getCardSprite().draw(batch);

            drawStats();

            powerdownButton.getSprite().draw(batch);

            //draw Cards
            cardHandler.drawCards();

            if(tick%40==0) printSelectedCards();

            System.out.println("merging");
            tick++;
        }

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
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
    //if you click a card and it is inside a cardSlot a boolean will change (isClicked=true), this I will use in the touchUp method
    // And if you click the Execute button the it will change a boolean value
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(mainMenu.getMainRunning()) {
            System.out.println("buttons created");
            mainMenu.getClientBtn().buttonClicked(screenX, screenY, mainMenu.getClientBtn());
            mainMenu.getServerBtn().buttonClicked(screenX, screenY, mainMenu.getServerBtn());
            mainMenu.getStartBtn().buttonClicked(screenX, screenY, mainMenu.getStartBtn());
        }
        else {
            endTurnButton.buttonClicked(screenX, screenY, endTurnButton);
            powerdownButton.buttonClicked(screenX, screenY, powerdownButton);
            cardHandler.click(Input.Buttons.LEFT, screenX, screenY);
        }
        return false;
    }

    //if a card is inside a cardslot and it is released move it into the middle of the slot,
    //if it is outside then move it back to its default pos
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        cardHandler.letGo(screenX, screenY);

        if (!mainMenu.getMainRunning()){
            cardHandler.letGo(screenX, screenY);
        }

        /*
        else{
            if(insideCard(screenX, screenY, mainMenu.getClientBtn())){
                System.out.println("DU TRYKKET PÅ CLIENT");
            }

            if(insideCard(screenX, screenY, mainMenu.getServerBtn())){
                System.out.println("DU TRYKKET PÅ SERVER");
            }

            if(insideCard(screenX, screenY, mainMenu.getStartBtn())){
                System.out.println("DU TRYKKET PÅ START");
                mainMenu.setMainRunning(false);
                create();
            }
        }
        */
        return false;
    }

    public boolean insideCard(float screenX, float screenY, Cards card){
        float NewscreenY= Gdx.graphics.getHeight() - screenY;
        return (screenX > card.getCardSprite().getX()) && (screenX < (card.getCardSprite().getX() + card.getCardSprite().getWidth())) && (NewscreenY > card.getCardSprite().getY()) && (NewscreenY < (card.getCardSprite().getY() + card.getCardSprite().getHeight()));
    }

    @Override
    //if a card is clicked on and draged, then move that clicked card
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(!mainMenu.getMainRunning()){
            cardHandler.dragged(screenX, screenY);
        }
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

        grid.set(9,0,  MapTile.LASERNORTH);

        grid.set(10, 5,MapTile.LASERNORTH);

        grid.set(7, 5, MapTile.LASERNORTH);

        grid.set(0, 9, MapTile.LASEREAST);

        grid.set(1, 1, MapTile.CHECKPOINT1);
        grid.set(1, 8, MapTile.CHECKPOINT2);
        grid.set(6, 7, MapTile.CHECKPOINT3);
        grid.set(10, 3,MapTile.CHECKPOINT4);

        grid.set(2, 2, MapTile.HOLE);

        grid.set(9.0, -0.5, MapTile.WALL);
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

    public void endTurnButtonCreation(int x, int y) {
        Texture buttonTexture = new Texture(Gdx.files.internal("Models/Button.png"));
        Sprite buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setPosition(x, y);
        endTurnButton = new Button(x, y, "endRoundButton" ,buttonSprite, this);
    }

    public void powerdownButtonCreation(int x, int y) {
        if (!robot.getInitiatePowerdown()) {
            Texture powerdownbuttonTexture = new Texture(Gdx.files.internal("Models/Powerdown_inactive.jpg"));
            Sprite powerdownbuttonSprite = new Sprite(powerdownbuttonTexture);
            powerdownbuttonSprite.setPosition(x, y);
            this.powerdownButton = new Button(x, y, "powerDown_inactive",powerdownbuttonSprite, this);

        } else {
            Texture powerdownbuttonTexture = new Texture(Gdx.files.internal("Models/Powerdown_active.jpg"));
            Sprite powerdownbuttonSprite = new Sprite(powerdownbuttonTexture);
            powerdownbuttonSprite.setPosition(x, y);
            this.powerdownButton = new Button(x,y, "powerDown_active", powerdownbuttonSprite, this);
        }
    }


    //creation og the stat-board
    public void statBoardCreation ( float x, float y){
        Texture statTexture = new Texture(Gdx.files.internal("Models/stats.PNG"));
        statBoardSprite = new Sprite(statTexture);
        statBoardSprite.setPosition(x, y);
        statBoard0 = new Cards(x, y, "statBoard", 0, statBoardSprite);
    }



    //draw the stat font on top of the board
    public void drawStats() {
        int hp = 9 - robot.getDamage();
        font.setColor(0, 0, 0, 1);
        font.draw(batch, "Player1:", statBoard0.getCardSprite().getX() + 10, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30);
        font.draw(batch, "" + hp, statBoard0.getCardSprite().getX() + 90, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30);
        font.draw(batch, "" + robot.getLives(), statBoard0.getCardSprite().getX() + 175, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30);
        font.draw(batch, "" + robot.getFlagsPassed(), statBoard0.getCardSprite().getX() + 250, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30);
    }

    //support metode
    public void printSelectedCards(){
        selectedCards = cardHandler.getSelectedCards();
        for (int i = 0; i<selectedCards.length; i++){
            if(selectedCards[i]!=null){
                System.out.println("Card in cardslot" + i +" "+selectedCards[i].getName());
            }
        }
    }

    public void doTurn() {
        Cards selectedCards[] = cardHandler.getSelectedCards();
        robot.setAlive(true);
        if (!robot.getExecutePowerdown()) {
            if (selectedCards[0] != null && selectedCards[1] != null && selectedCards[2] != null && selectedCards[3] != null && selectedCards[4] != null && cardHandler.getisDone()) {
                if (turn >= 5) {
                    System.out.println("Ferdig med ein heil runde!");
                    for (int h = 0; h < 5; h++) {
                        //cardHandler.lockDown();
                    }
                    turn = 0;
                    cardHandler.setNotFirst(true);
                    cardHandler.nullyFy();
                    checkLock(selectedCards);
                    cardHandler.setisDone(false);
                    cardHandler.setCardSprites();

                    System.out.println("\n");
                }
                if (tick % 40 == 0) {
                    if (robot.getAlive()) {
                        robot.move(selectedCards[turn]);
                    }
                    turn++;
                    System.out.println("DidTURN "+(turn));
                    robot.getSprite().draw(batch);
                }
            }
        } else {
            System.out.println("hello");
            if (turn == 0) {
                robot.doPowerdown();
            }
            turn++;
            if (turn >= 5) {
                robot.setExecutePowerdown(false);
                robot.setInitiatePowerdown(false);
            }
        }
    }

    public static CardHandler getCardHandler() {
        return cardHandler;
    }

    //cleans up the screen, by removing sprites no longer in use
    public void checkLock(Cards[] selectedCards) {
        for (int v = 0; v < cardHandler.getSpritePos().size(); v++) {
            boolean locked = false;
            for (int i = 0; i < selectedCards.length; i++) {
                if (selectedCards[i] != null) {
                    //System.out.println(cardHandler.getSpritePos().get(v).getTexture().toString() + " " + selectedCards[i].getCardSprite().getTexture().toString());
                    if (cardHandler.getSpritePos().get(v) == selectedCards[i].getCardSprite()) {
                        //System.out.println("LOCKED");
                        locked = true;
                        break;
                    }
                }
            }
            if (!locked) {
                //System.out.println("GONE!");
                cardHandler.getSpritePos().get(v).setPosition(10000, 10000);
            }
        }

        System.out.println(cardHandler);
        //return cardHandler;
    }
    public void setEndOfTurn(boolean isItOver){
        this.isEndOfTurn = isItOver;
    }
    public static boolean getEndOfTurn(){
        return isEndOfTurn;
    }


    public static int getTurn() {
        return turn;
    }

    public Robot getRobot() {return robot;}
}