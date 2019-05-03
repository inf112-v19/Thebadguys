package inf112.skeleton.app;

import Grid.IGrid;
import Grid.MyGrid;
import Server.Client;
import Server.Server;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import java.util.ArrayList;
import java.util.Scanner;
public class RoboRallyDemo implements ApplicationListener, InputProcessor {
    private static TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private int i = 0;
    private int tick = 0;
    private static int turn = 0;
    private static Boolean isEndOfTurn = false;

    private Button endTurnButton;
    private Button powerdownButton;
    private static Robot robot;
    private FitViewport viewPort;
    private static CardHandler cardHandler;
    private Cards statBoard0;
    private static Cards selectedCards[];
    private boolean firstRund = true;
    private mainMenu mainMenu;
    private ArrayList<Sprite> statBoardList = new ArrayList<>();

    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private BitmapFont font;
    private Sprite statBoardSprite;
    private static GameMap map;
    private IGrid grid;
    private int[][] starts = {{0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}};
    private Server server;
    private static Client client;
    private static Robot[] robots = new Robot[8];
    private Sprite[] sprites = new Sprite[8];
    private Texture[] textures = new Texture[8];
    private static int clientCount;
    private static String[] colors = {"Gold", "Cyan", "Green", "Red", "Blue", "Purple", "Basil", "Lemon"};
    private static int ID;
    private static boolean ready[] = {false, false, false, false, false, false, false, false};
    private String[][] moves;
    private int[] order;
    private static boolean singlePlayerMode = false;
    private static AIRobot[] AIs = new AIRobot[3];
    private int[][] AIstarts = {{3,0}, {6,0}, {9,0}};

    public static boolean getSinglePlayerMode() {
        return singlePlayerMode;
    }

    public static void setReady(int id) {
        ready[id] = true;
    }

    public static void killMe(int id, boolean AI) {
        if(singlePlayerMode && !AI) {
            robot = null;
        }
        else if (AI){
            AIs[id] = null;
        }
        else {
            robots[id] = null;
            String died = "/d/" + id + "/e/";
            client.getBackendClient().send(died.getBytes());
        }
    }

    public static boolean amIAlive() {
        if(singlePlayerMode && robot != null) {
            return true;
        }
        else if (!singlePlayerMode && robots[getID()] != null) {
            return true;
        }
        return false;
    }

    public static boolean amIAliveAI(int id) {
        if(AIs[id] != null) {
            return true;
        }
        return false;
    }

    public static void setDead(int id) {
        robots[id] = null;
    }

    public static String getColors(int id) {
        return colors[id];
    }

    public static void setClientCount(int count) {
        clientCount = count;
    }

    public  void createv2(){
        batch = new SpriteBatch();
        tiledMap = new TmxMapLoader().load("Models/roborallymap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        createGrid();
        map = new GameMap(grid);

        if (singlePlayerMode) {
            texture = new Texture(Gdx.files.internal("Models/tank0.png"));
            sprite = new Sprite(texture);
            robot = new Robot(sprite, starts[0]);
            for (int i = 0; i < AIs.length; i++) {
                textures[i] = new Texture(Gdx.files.internal("Models/tank" + (i+1) + ".png"));
                sprites[i] = new Sprite(textures[i]);
                AIs[i] = new AIRobot(sprites[i], AIstarts[i], i);
                System.out.println("created AI" + i);
            }
            for(int i = 0; i < AIs.length; i++) {
                if (AIs[i] != null) {
                    sprites[i].setPosition(AIs[i].getSpriteX(), AIs[i].getSpriteY());
                }
            }
            if (robot != null) {
                sprite.setPosition(robot.getSpriteX(), robot.getSpriteY());
            }
        }

        if (!singlePlayerMode) {
            clientCount = client.getClientCount();
            order = new int[clientCount * 5];
            moves = new String[clientCount][5];
            for (int i = 0; i < clientCount; i++) {
                textures[i] = new Texture(Gdx.files.internal("Models/tank" + (i) + ".png"));
                sprites[i] = new Sprite(textures[i]);
                robots[i] = new Robot(sprites[i], starts[i]);
                System.out.println("created robot" + i);
            }
            for (int i = 0; i < clientCount; i++) {
                if (robots[i] != null) {
                    sprites[i].setPosition(robots[i].getSpriteX(), robots[i].getSpriteY());
                }
            }
        }

        if (singlePlayerMode) {
            cardHandler = new CardHandler(batch, robot, map);
        } else if (!singlePlayerMode) {
            cardHandler = new CardHandler(batch, robots[ID], map);
        }
        font = new BitmapFont();
        //create the end turn button
        endTurnButtonCreation(700, 510);
        statBoardCreation(700, 1030);

        //set the position of all the cardsprites
        cardHandler.setCardSprites();

        //create the 9 cards cards
        cardHandler.createInitialDecklist();

        //creation of the 5 cardSlots
        cardHandler.createCardSlots();

        Gdx.input.setInputProcessor(this);
    }


    //create the initial state of the game
    @Override
    public void create() {
        if (firstRund) {
            batch = new SpriteBatch();
            float w = Gdx.graphics.getWidth();
            float h = Gdx.graphics.getHeight();
            //set the camera
            setCamera(w, h);
            mainMenu = new mainMenu(batch);
            firstRund = false;
        }
        if (mainMenu.getMainRunning()) {
            mainMenu.startMenu();
        }
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    //rendering of the map and all the sprites
    @Override
    public void render() {
        Gdx.gl.glClearColor(128 / 255f, 128 / 255f, 128 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (mainMenu.getMainRunning()) {
            batch.begin();
            mainMenu.render();
        }else{
            selectedCards = cardHandler.getSelectedCards();
            if(selectedCards[0]!=null){
                if(selectedCards[0].getName()=="clickedCard"){
                    cardHandler.crushBug();
                }
            }
            camera.update();
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
            batch.begin();
            if (singlePlayerMode) {
                for (int i = 0; i < AIs.length; i++){
                    if (AIs[i] != null) {
                        sprites[i].draw(batch);
                    }
                }
                if (robot != null) {
                    sprite.draw(batch);
                }
            } else {
                for (int i = 0; i < clientCount; i++) {
                    if (robots[i] != null) {
                        sprites[i].draw(batch);
                    }
                }
            }
            doTurn();
            //draw the cardslots
            if (amIAlive()) {
                cardHandler.drawCardSlots();
                cardHandler.drawLockedList();
                //draw button
                powerdownButtonCreation(700, 710);

                endTurnButton.getSprite().draw(batch);

                powerdownButton.getSprite().draw(batch);

                //draw Cards
                cardHandler.drawCards();
            }

            drawStats();

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
        if (mainMenu.getMainRunning()) {
            if (mainMenu.getClientBtn().buttonClicked(screenX, screenY, mainMenu.getClientBtn())) {
                if(client != null) {
                    System.out.println("You can only have one client per computer.");
                } else {
                    System.out.println("Skriv inn host ip som streng(ipV4 || ipV6)! eks:(10.10.12.31): ");
                    Scanner inn = new Scanner(System.in);
                    String ip = inn.nextLine();
                    System.out.println(ip);
                    client = new Client("Player", ip, 55557);
                    boolean wait = false;
                    while (!wait) {
                        System.out.println("Waiting for the server to start the game.");
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (client.getStarted()) {
                            createv2();
                        }
                        wait = client.getStarted();
                    }
                }
            }
            if (mainMenu.getServerBtn().buttonClicked(screenX, screenY, mainMenu.getServerBtn())) {
                if (server == null) {
                    server = new Server(55557);
                    client = new Client("Player", "localhost", 55557);
                }
                else {
                    System.out.println("You already have a server running!");
                    System.out.println("Wait for clients to connect, and start the server.");
                    System.out.println("For singleplayer mode (with 3 AIs) please restart the game and press start directly.");
                }
            }
            if (mainMenu.getStartBtn().buttonClicked(screenX, screenY, mainMenu.getStartBtn())) {
                if (server != null) {
                    System.out.println("Starting the multiplayer game!");
                    server.setStarted(true);
                    client.getBackendClient().send("/s//e/".getBytes());
                    mainMenu.setMainRunning(false);
                    createv2();
                }
                else if (server == null) {
                    singlePlayerMode = true;
                    System.out.println("Starting singleplayer game!");
                    setID(0);
                    createv2();
                    mainMenu.setMainRunning(false);
                }
            }

        } else {
            endTurnButton.buttonClicked(screenX, screenY, endTurnButton);
            powerdownButton.buttonClicked(screenX, screenY, powerdownButton);
            cardHandler.click(Input.Buttons.LEFT, screenX, screenY);
        }
        return false;
    }

    public static Robot getRobot() {
        return robot;
    }

    public static AIRobot[] getAIs() {
        return AIs;
    }

    public static Robot[] getRobots() {return robots;}

    //if a card is inside a cardslot and it is released move it into the middle of the slot,
    //if it is outside then move it back to its default pos
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!mainMenu.getMainRunning()) {
            cardHandler.letGo(screenX, screenY);
        }
        return false;
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
        grid.set(3,8, MapTile.HOLE);
        grid.set(9,7, MapTile.HOLE);
        grid.set(10,2, MapTile.HOLE);

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
        endTurnButton = new Button(x, y, "endRoundButton", buttonSprite);
    }


    public void powerdownButtonCreation(int x, int y) {
        if (singlePlayerMode) {
            if (robot != null && !robot.getInitPowerdown()) {
                Texture powerdownbuttonTexture = new Texture(Gdx.files.internal("Models/Powerdown_inactive.jpg"));
                Sprite powerdownbuttonSprite = new Sprite(powerdownbuttonTexture);
                powerdownbuttonSprite.setPosition(x, y);
                this.powerdownButton = new Button(x, y, "powerDown_inactive", powerdownbuttonSprite);

            } else if (robot != null && robot.getInitPowerdown()){
                Texture powerdownbuttonTexture = new Texture(Gdx.files.internal("Models/Powerdown_active.jpg"));
                Sprite powerdownbuttonSprite = new Sprite(powerdownbuttonTexture);
                powerdownbuttonSprite.setPosition(x, y);
                this.powerdownButton = new Button(x, y, "powerDown_active", powerdownbuttonSprite);
            }
        }
        if (!singlePlayerMode) {
            if (robots[ID] != null && !robots[ID].getInitPowerdown()) {
                Texture powerdownbuttonTexture = new Texture(Gdx.files.internal("Models/Powerdown_inactive.jpg"));
                Sprite powerdownbuttonSprite = new Sprite(powerdownbuttonTexture);
                powerdownbuttonSprite.setPosition(x, y);
                powerdownButton = new Button(x, y, "powerDown_inactive", powerdownbuttonSprite);
            }
            else if (robots[ID] != null && robots[ID].getInitPowerdown()){
                Texture powerdownbuttonTexture = new Texture(Gdx.files.internal("Models/Powerdown_active.jpg"));
                Sprite powerdownbuttonSprite = new Sprite(powerdownbuttonTexture);
                powerdownbuttonSprite.setPosition(x, y);
                powerdownButton = new Button(x, y, "powerDown_active", powerdownbuttonSprite);
            }
        }
    }

    //creation og the stat-board
    public void statBoardCreation ( float x, float y){
        float v=y;
        Texture statTexture = new Texture(Gdx.files.internal("Models/Topofstatboard.PNG"));
        statBoardSprite = new Sprite(statTexture);
        statBoardSprite.setPosition(x, y);
        statBoard0 = new Cards(x, y, "statBoard", 0, statBoardSprite);
        statBoardList.add(statBoardSprite);

        if(!singlePlayerMode){
            for(int i =0; i<clientCount; i++){
                v-=27;
                Texture statTexture0 = new Texture(Gdx.files.internal("Models/actualstatboard.PNG"));
                statBoardSprite = new Sprite(statTexture0);
                statBoardSprite.setPosition(x, v);
                statBoardList.add(statBoardSprite);
            }
        }else{
            for(int i =0; i<AIs.length+1; i++){
                v-=27;
                Texture statTexture0 = new Texture(Gdx.files.internal("Models/actualstatboard.PNG"));
                statBoardSprite = new Sprite(statTexture0);
                statBoardSprite.setPosition(x, v);
                statBoardList.add(statBoardSprite);
            }
        }

    }

    //draw the stat font on top of the board
    public void drawStats(){
        if (!singlePlayerMode) {
            for(int i=0; i<statBoardList.size(); i++){
                statBoardList.get(i).draw(batch);
            }
            for(int i = 0; i < clientCount; i++){
                if (robots[i] != null) {
                    int hp = 9 - robots[i].getDamage();
                    font.setColor(1, 0, 0, 1);
                    if(ready[i]) {
                        drawReady(i, ID);
                    }
                    else if (i == ID) {
                        String you = colors[i]+ " (you)";
                        font.draw(batch, you + ":", statBoard0.getCardSprite().getX() + 10, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25);
                    }
                    else {
                        font.draw(batch, colors[i] + ":", statBoard0.getCardSprite().getX() + 10, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25);
                    }
                    font.setColor(0,0,0,1);
                    font.draw(batch, "" + hp, statBoard0.getCardSprite().getX() + 90, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25);
                    font.draw(batch, "" + robots[i].getLives(), statBoard0.getCardSprite().getX() + 175, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25);
                    font.draw(batch, "" + robots[i].getFlagsPassed(), statBoard0.getCardSprite().getX() + 250, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25);
                }
            }
        } else {
            for (int i=0; i<statBoardList.size(); i++){
                statBoardList.get(i).draw(batch);
            }
            if (robot != null) {
                int hp = 9 - robot.getDamage();
                font.setColor(0, 0, 0, 1);

                String you = colors[0]+ " (you)";
                font.draw(batch, you + ":", statBoard0.getCardSprite().getX() + 10, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30);
                font.draw(batch, "" + hp, statBoard0.getCardSprite().getX() + 90, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30);
                font.draw(batch, "" + robot.getLives(), statBoard0.getCardSprite().getX() + 175, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30);
                font.draw(batch, "" + robot.getFlagsPassed(), statBoard0.getCardSprite().getX() + 250, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30);
            }

            for(int i = 0; i < AIs.length; i++) {
                if (AIs[i] != null) {
                    int hpAI = 9 - AIs[i].getDamage();
                    String AI = colors[i+1] + " (AI)";
                    font.draw(batch, AI + ":", statBoard0.getCardSprite().getX() + 10, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25 - 35);
                    font.draw(batch, "" + hpAI, statBoard0.getCardSprite().getX() + 90, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25 - 35);
                    font.draw(batch, "" + AIs[i].getLives(), statBoard0.getCardSprite().getX() + 175, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25 - 35);
                    font.draw(batch, "" + AIs[i].getFlagsPassed(), statBoard0.getCardSprite().getX() + 250, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25 - 35);
                }
            }
        }
    }


    public void drawReady(int i, int ID){
        font.setColor(0, 1, 0, 1);
        if (i == ID) {
            String you = colors[i] + " (you)";
            font.draw(batch, you + ":", statBoard0.getCardSprite().getX() + 10, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25);
        } else {
            font.draw(batch, colors[i] + ":", statBoard0.getCardSprite().getX() + 10, statBoard0.getCardSprite().getY() + statBoard0.getCardSprite().getHeight() - 30 - i * 25);
        }
    }

    public void doTurn () {
        if (singlePlayerMode) {
            if (robot != null &&robot.getExecPowerdown() && turn == 0) {
                robot.doPowerdown();
            }
        }
        if (!singlePlayerMode) {
            if (robots[getID()] != null && robots[getID()].getExecPowerdown() && turn == 0) {
                robots[i].doPowerdown();
            }
        }

        selectedCards = cardHandler.getSelectedCards();

        if (areCardSlotsFull() && cardHandler.getisDone() && checkMode()) {
            if (!singlePlayerMode) {
                client.getBackendClient().send("/o//e/".getBytes());
                moves = client.getMoves();
                order = client.getOrder();
            }
            if (turn >= 5) {
                if (!singlePlayerMode) {
                    for (int i = 0; i < clientCount; i++) {
                        if (robots[i] != null) {
                            robots[i].setAlive(true);
                        }
                    }
                }
                else {
                    if (robot != null) {
                        robot.setAlive(true);
                    }
                    for(int i = 0; i < AIs.length; i++) {
                        if (AIs[i] != null) {
                            AIs[i].setAlive(true);
                        }
                    }
                }
                turn = 0;
                cardHandler.setNotFirst(true);
                cardHandler.nullyFy();
                checkLock(selectedCards);
                if (singlePlayerMode) {
                    if (robot != null && !robot.getInitPowerdown()) {
                        cardHandler.setisDone(false);
                    }
                }
                else {
                    if (robots[ID] != null && !robots[ID].getInitPowerdown()) {
                        cardHandler.setIsDone(false);
                    }
                }
                cardHandler.setCardSprites();

                if (!singlePlayerMode ) {
                    for(int i = 0; i < clientCount; i++) {
                        ready[i] = false;
                    }
                    if (server != null) {
                        server.roundStart();
                    }
                    drawStats();
                }
            }
            if (tick % 40 == 0) {
                if (singlePlayerMode) {
                    if (robot != null && robot.getAlive()) {
                        robot.move(selectedCards[turn].getName());
                        mapElements(singlePlayerMode);
                    }
                    for (int i = 0; i < AIs.length; i++) {
                        if (AIs[i] != null && AIs[i].getAlive()) {
                            AIs[i].doTurn(turn);
                            if (AIs[i] != null && AIs[i].getAlive()) {
                                AIs[i].robotFireLasers(AIs);
                            }
                            if (robot != null && robot.getAlive() && AIs[i] != null) {
                                AIs[i].robotFireLasers(robot);
                            }
                        }
                    }
                    turn++;
                    if (robot != null) {
                        robot.getSprite().draw(batch);
                    }
                    for (int i = 0; i < AIs.length; i++) {
                        if (AIs[i] != null) {
                            AIs[i].getSprite().draw(batch);
                        }
                    }
                    if (robot != null && robot.getInitPowerdown() && turn == 5) {
                        robot.setExecPowerdown(true);
                    }

                }
                else if (!singlePlayerMode) {
                    int currentOrderdMove;
                    for (int j = 0; j < clientCount; j++) {
                        if(turn==0){
                            currentOrderdMove = order[j];
                        }   else {
                            currentOrderdMove = order[clientCount*turn +j];
                        }

                        if (robots[currentOrderdMove] != null && robots[currentOrderdMove].getAlive()) {
                            robots[currentOrderdMove].move(moves[currentOrderdMove][turn]);
                            System.out.println(currentOrderdMove);
                            System.out.println(moves[currentOrderdMove][turn]);
                        }
                    }
                    mapElements(singlePlayerMode);
                    turn++;

                    for (int i = 0; i < clientCount; i++) {
                        if (turn == 5 && robots[i] != null && robots[i].getInitPowerdown()) {
                            robots[i].setExecPowerdown(true);
                        }
                        if (robots[i] != null) {
                            robots[i].getSprite().draw(batch);
                        }
                    }
                }
            }
        }
    }

    private void mapElements(boolean singlePlayerMode) {
        if(singlePlayerMode && robot != null) {
            ExpressBelt.doExpressBelt(robot);
            Belt.doBelt(robot);
            Spin.doSpin(robot);
            if (robot != null && robot.getAlive()) {
                map.fireLasers(robot);
            }
            if (robot != null && robot.getAlive() && !robot.getExecPowerdown()) {
                robot.robotFireLasers(AIs);
            }
            if (robot != null && map.isCheckpoint(robot.getPosX(), robot.getPosY(), robot.getFlagsPassed())) {
                robot.setFlagsPassed(robot.getFlagsPassed() +1 );
                robot.setCheckpoint(robot.getPosX(), robot.getPosY());
                System.out.println(colors[0] + " made it to backup number " + robot.getFlagsPassed());
            }
            if (robot != null && map.isRepairSite(robot.getPosX(), robot.getPosY(), turn) == 1) {
                robot.setCheckpoint(robot.getPosX(), robot.getPosY());
            } else if (robot != null && map.isRepairSite(robot.getPosX(), robot.getPosY(), turn) == 2) {
                robot.setCheckpoint(robot.getPosX(), robot.getPosY());
                if (robot.getDamage() != 0) {
                    robot.setDamage(robot.getDamage() - 1);
                }
            } else if (robot != null && map.isRepairSite(robot.getPosX(), robot.getPosY(), turn) == 3) {
                robot.setCheckpoint(robot.getPosX(), robot.getPosY());
                if (robot.getDamage() > 1) {
                    robot.setDamage(robot.getDamage() - 2); // put in choice for option cards.
                } else if (robot.getDamage() == 1) {
                    robot.setDamage(0);
                }
                else if(robot.getDamage() == 1) {robot.setDamage(0);}
            }

        }
        else {
            for (int i = 0; i < clientCount; i++) {
                if (robots[i] != null) {
                    ExpressBelt.doExpressBelt(robots[i]);
                    Belt.doBelt(robots[i]);
                    Spin.doSpin(robots[i]);
                }
            }

            for (int i = 0; i < clientCount; i++) {
                if (robots[i] != null && robots[i].getAlive()) {
                    map.fireLasers(robots[i]);
                }
            }

            for (int i = 0; i < clientCount; i++) {
                if (robots[i] != null && robots[i].getAlive() && !robots[i].getExecPowerdown()) {
                    map.robotFireLasers(robots, robots[i], i);
                }
            }

            for (int i = 0; i < clientCount; i++) {
                if (robots[i] != null && map.isCheckpoint(robots[i].getPosX(), robots[i].getPosY(), robots[i].getFlagsPassed())) {
                    robots[i].setFlagsPassed(robots[i].getFlagsPassed() +1 );
                    robots[i].setCheckpoint(robots[i].getPosX(), robots[i].getPosY());
                    System.out.println(colors[i] + " made it to backup number " + robots[i].getFlagsPassed());
                }
            }
            for (int i = 0; i < clientCount; i++) {
                if (robots[i] != null && map.isRepairSite(robots[i].getPosX(), robots[i].getPosY(), turn) == 1) {
                    robots[i].setCheckpoint(robots[i].getPosX(), robots[i].getPosY());
                } else if (robots[i] != null && map.isRepairSite(robots[i].getPosX(), robots[i].getPosY(), turn) == 2) {
                    robots[i].setCheckpoint(robots[i].getPosX(), robots[i].getPosY());
                    if (robots[i].getDamage() != 0) {
                        robots[i].setDamage(robots[i].getDamage() - 1);
                    }
                } else if (robots[i] != null && map.isRepairSite(robots[i].getPosX(), robots[i].getPosY(), turn) == 3) {
                    robots[i].setCheckpoint(robots[i].getPosX(), robots[i].getPosY());
                    if (robots[i].getDamage() > 1) {
                        robots[i].setDamage(robots[i].getDamage() - 2); // put in choice for option cards.
                    } else if (robots[i].getDamage() == 1) {
                        robots[i].setDamage(0);
                    }
                    else if(robots[i].getDamage() == 1) {robots[i].setDamage(0);}
                }
            }
        }
    }

    public static boolean areCardSlotsFull() {
        if(selectedCards[0] != null && selectedCards[1] != null && selectedCards[2] != null && selectedCards[3] != null && selectedCards[4] != null && amIAlive()) {
            return true;
        }
        else if(!amIAlive()) {
            return true;
        }
        return false;
    }

    public static CardHandler getCardHandler(){
        return cardHandler;
    }

    //cleans up the screen, by removing sprites no longer in use
    public void checkLock(Cards[] selectedCards){
        for (int v = 0; v < cardHandler.getSpritePos().size(); v++) {
            boolean locked=false;
            for(int i=0; i<selectedCards.length; i++){
                if(selectedCards[i]!=null){
                    if(cardHandler.getSpritePos().get(v)==selectedCards[i].getCardSprite()){
                        locked=true;
                        break;
                    }
                }
            }
            if(!locked){
                cardHandler.getSpritePos().get(v).setPosition(10000, 10000);
            }
        }
    }

    public static boolean getEndOfTurn() {
        return isEndOfTurn;
    }


    public static int getTurn() {
        return turn;
    }

    public static void setID(int id){
        ID = id;
    }

    public static int getID() {
        return ID;
    }

    public static Client getClient() {
        return client;
    }

    public boolean checkMode() {
        if(singlePlayerMode) {
            return true;
        } else {
            return client.askReady();
        }
    }
}




