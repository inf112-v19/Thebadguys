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

import javax.smartcardio.Card;
import java.util.ArrayList;

public class RoboRallyDemo implements ApplicationListener, InputProcessor {
    private static TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private int i = 0;
    private Cards clickedCard;
    private Cards listCard;
    private Cards CardButton;
    private CardSlots temp;
    private Robot robot;
    private int counter;
    private FitViewport viewPort;

    private int cardDelt=9;
    private int cardSlotLock=5;

    private boolean isDone=false;
    private boolean notFirst=false;
    private boolean isClicked=false;

    //lister
    private ArrayList<CardSlots> cardSlotPos;
    private ArrayList<Sprite> randomSpriteList;
    private ArrayList<Sprite> spritePos;
    private Deck Deck;
    private Cards[] selectedCards;
    private int randomPriority[];


    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private Sprite cardSprite10;
    private float posX, posY;
    private TiledMapTileSet mapSet;
    private BitmapFont font;

    private IGameMap map;
    private IGrid grid;

    //create the initial state of the game
    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        //set the camera
        setCamera(w,h);
        //creation of the map
        tiledMap = new TmxMapLoader().load("Models/roborallymap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        createGrid();

        //creation of the robot
        texture = new Texture(Gdx.files.internal("Models/tank.png"));
        sprite = new Sprite(texture);
        posX = 0;
        posY = 0;
        int[] startpos = {Math.round(posX), Math.round(posY)};
        robot = new Robot(sprite, startpos, 0);
        sprite.setPosition(robot.getX1(),robot.getY1());

        //create the card that Is clicked
        Texture cardTexture = new Texture(Gdx.files.internal("Models/AlleBevegelseKortUtenPrioritet/genericCard.png"));
        //loads map with elements and robot
        grid.set(robot.getPosX(),robot.getPosY(), MapTile.PLAYER);
        map = new GameMap(grid);

        //create the card that Is clicked
        cardTexture = new Texture(Gdx.files.internal("Models/AlleBevegelseKortUtenPrioritet/genericCard.png"));
        cardSprite10 = new Sprite(cardTexture);
        clickedCard=new Cards(0,0, "",0, cardSprite10);

        //create the end turn button
        buttonCreation(700,500);

        //creation of all arrays containing positions or cards
        spritePos= new ArrayList<>();
        cardSlotPos= new ArrayList<>();
        randomSpriteList=new ArrayList<>();
        Deck = new Deck();
        selectedCards = new Cards[5];

        font = new BitmapFont();

        //set the position of all the cardsprites
        setCardSprites();

        //create the 9 cards cards
        createDecklist();

        //creation of the 5 cardSlots
        createCardSlots();

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
        Gdx.gl.glClearColor (128/255f, 128/255f, 128/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();
        sprite.draw(batch);

        if(i==300){
            lockDown(8);
        }

        //check how mutch damage a robot has taken
        //rotation of sprite, rotate 90 degrees every 100th gametick
        if (i % 100 == 0) {
            for (int i = 0; i < selectedCards.length; i++) {
                System.out.println(selectedCards[i]);
            }
            System.out.println("\n");
        }

        if (selectedCards[0] != null && selectedCards[1] != null && selectedCards[2] != null && selectedCards[3] != null && selectedCards[4] != null && isDone) {
            for (int i = 0; i < cardSlotLock; i++) {
                robot.move(selectedCards[i]);
                map.move(selectedCards[i]);
                robot.getSprite().draw(batch);
                if (i == cardSlotLock - 1) {
                    for(int v=0; v<spritePos.size(); v++){
                        spritePos.get(v).setPosition(10000, 10000);
                    }
                    notFirst=true;
                    setCardSprites();
                    nullyFy();
                    isDone = false;
                }
            }
            System.out.println("\n");
        }
        //draw the cardslots
        drawCardSlots();

        //draw button
        CardButton.getCardSprite().draw(batch);

        //draw Cards
        drawCards();

        i++;
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

    //this method is used to click and move a card around on the screen. if you click a card, the clickedCard, will get the right card from the Deck
    //if tou click a card and it is inside a cardSlot a boolean will change (isClicked=true), this I will use in the touchUp method
    // And if you click the Execute button the it will change a boolean value
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        counter=0;
        if( insideCard(screenX, screenY, CardButton)){
            isDone=true;
            return false;
        }
        for(int i=0; i<cardDelt; i++){
            if(insideCard(screenX, screenY,Deck.getDeckList().get(i)) && button == Buttons.LEFT){
                clickedCard=Deck.getDeckList().get(i);
                for(int j=0; j<5; j++){
                    if(insideCardSlot(clickedCard, cardSlotPos.get(j))){
                        isClicked=true;
                        counter=j;
                        break;
                    }
                }
                break;
            }
        }
        /*
        // rigth click moves the card to the middle of the screenc
        if(button == Buttons.RIGHT){
            posX = Gdx.graphics.getWidth()/2 - cardGUI.getCardSprite().getWidth()/2;
            posY = Gdx.graphics.getHeight()/2 - cardGUI.getCardSprite().getHeight()/2;
            //cardGUI.render(posX, posY);
        }*/
        return false;
    }

    //if a card is inside a cardslot and it is released move it into the middle of the slot,
    //if it is outside then move it back to its default pos
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean isInside=false;
        if( insideCard(screenX, screenY, CardButton)){
            isDone=false;
            return false;
        }
        //if a card is inside a cardslot and it is released move it into the middle of the slot
        for(int i=0; i<5; i++){
            if(insideCardSlot(clickedCard, cardSlotPos.get(i)) && selectedCards[i]==null){
                if(isClicked){
                    selectedCards[counter]=null;
                    isClicked=false;
                }
                selectedCards[i]=clickedCard;
                isInside=true;
                clickedCard.getCardSprite().setPosition(cardSlotPos.get(i).getCardSlotSprite().getX()+getCardSlotCenterX(cardSlotPos.get(i))-getCardCenterX(clickedCard), cardSlotPos.get(i).getCardSlotSprite().getY()+getCardSlotCenterY(cardSlotPos.get(i))-getCardCenterY(clickedCard));
                counter=i;
                break;
            }
        }
        //if it is outside then move it back to its default pos
        if(!isInside){
            clickedCard.getCardSprite().setPosition(clickedCard.getDefaultPosX(), clickedCard.getDefaultPosY());
            if(isClicked){
                selectedCards[counter]=null;
                isClicked=false;
            }
        }
        //create a new clickedCard so that a card does not stick to the mouse when let go of
        clickedCard=new Cards(0,0, "",0, cardSprite10);
        return false;
    }

    @Override
    //if a card is clicked on and draged, then move that clicked card
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if( insideCard(screenX, screenY, CardButton)){
            isDone=false;
            return false;
        }
        clickedCard.getCardSprite().setPosition(screenX - clickedCard.getCardSprite().getWidth() / 2, Gdx.graphics.getHeight() - screenY - clickedCard.getCardSprite().getHeight() / 2);
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

    //the x cordinate at the centre of a card
    private Float getCardCenterX(Cards card){
        return card.getCardSprite().getWidth()/2;
    }

    //the y cordinate at the centre of a card
    private Float getCardCenterY(Cards card){
        return card.getCardSprite().getHeight()/2;
    }

    //the x cordinate at the centre of a cardSlot
    private Float getCardSlotCenterX(CardSlots card){
        return card.getCardSlotSprite().getWidth()/2;
    }

    //the y cordinate at the center of a cardSlot
    private Float getCardSlotCenterY(CardSlots card){
        return card.getCardSlotSprite().getHeight()/2;
    }

    //metode for å sjekke om midten av et kort er innenfor ein cardSlot
    private boolean insideCardSlot(Cards card, CardSlots cardSlotX){
        return (card.getCardSprite().getX() + getCardCenterX(card) > cardSlotX.getCardSlotSprite().getX() && card.getCardSprite().getX() + getCardCenterX(card) < (cardSlotX.getCardSlotSprite().getX() + cardSlotX.getCardSlotSprite().getWidth())) && (card.getCardSprite().getY() + getCardCenterY(card) > cardSlotX.getCardSlotSprite().getY() && card.getCardSprite().getY() + getCardCenterY(card) < (cardSlotX.getCardSlotSprite().getY() + cardSlotX.getCardSlotSprite().getHeight()));
    }

    //metode for å sjekke om muspeikeren er over et kort
    private boolean insideCard(float screenX, float screenY, Cards card){
        float NewscreenY=Gdx.graphics.getHeight() - screenY;
        float newscreenX=Gdx.graphics.getHeight()/2 + screenX;
        return (screenX > card.getCardSprite().getX()) && (screenX < (card.getCardSprite().getX() + card.getCardSprite().getWidth())) && (NewscreenY > card.getCardSprite().getY()) && (NewscreenY < (card.getCardSprite().getY() + card.getCardSprite().getHeight()));
    }

    //method to set the position of sprites, if it is the first turn then just set the position of the sprites,
    //if it is not the first turn then I use this method to change the sprites of the cards to get 9 new random cards
    private void setCardSprites() {
        int x=0;
        randomSpriteList.clear();
        addSprites();
        if(notFirst){
            spritePos.clear();
            for (int i = 0; i < cardDelt; i++) {
                spritePos.add(getRandomSprite());
                spritePos.get(i).setPosition(x, 250);
                Deck.getDeckList().get(i).setCardSprite(spritePos.get(i));
                Deck.getDeckList().get(i).setCardName(CardValues.values()[i].getName());
                Deck.getDeckList().get(i).setPriority(CardValues.values()[i].getPriority());
                x+=105;
            }
        }else{
            for (int i = 0; i < cardDelt; i++) {
                spritePos.add(getRandomSprite());
                spritePos.get(i).setPosition(x, 250);

                x+=105;
            }
        }
    }

    //method to create the card-Objects
    private void createDecklist(){
        int x=0;
        for(int i=0; i<cardDelt; i++){
            listCard=new Cards(x, 250, CardValues.values()[i].getName(), CardValues.values()[i].getPriority(),spritePos.get(i));
            Deck.getDeckList().add(listCard);
            x+=105;
        }
    }

    //method to draw the cards
    private void drawCards(){
        for(Cards card: Deck.getDeckList()){
            card.getCardSprite().draw(batch);
            //font.draw(batch,""+card.getPriority(),card.getCardSprite().getX()+card.getCardSprite().getWidth()-30,card.getCardSprite().getY()+card.getCardSprite().getHeight()-10);
        }
    }

    //method to create and place cardslots
    private void createCardSlots(){
        int x=0;
        for(int i=0; i<5; i++){
            temp = new CardSlots(x, posY);
            cardSlotPos.add(temp);
            x+=185;
        }
    }

    //method to draw the cardslots
    private void drawCardSlots(){
        for(int i=0; i<5; i++){
            temp=cardSlotPos.get(i);
            temp.getCardSlotSprite().draw(batch);
        }
    }

    //returns a random sprite form the spritesList , uses the rng method
    private Sprite getRandomSprite(){
        int v= rng();
        Sprite random = randomSpriteList.get(v);
        randomSpriteList.remove(v);
        return random;
    }

    //returns a random index from the spriteList
    private int rng(){
        return (int)(Math.random() * randomSpriteList.size()-1 + 1);
    }

    //add all the sprites into the sprite list, burde finne ein bedre løsning på dette
    private void addSprites(){
        for(int i=0; i<CardValues.values().length;i++){
            randomSpriteList.add(CardValues.values()[i].getSprite());
        }
    }

    private boolean duplicateSprite(Sprite sprite){
        for(int i=0; i<randomSpriteList.size(); i++){
            if(randomSpriteList.get(i)==sprite){
                return true;
            }
        }
        return false;
    }

    //method that empties the selectedCards array, that is used when an turn is over
    private void nullyFy(){
        for(int i=0; i<selectedCards.length; i++){
            selectedCards[i]=null;
        }
    }

    private void createGrid(){
        grid = new MyGrid(12,12, MapTile.OPEN);
        //sets conveyerbelt element on map
        grid.set(0,6, MapTile.CONVEYERBELT);
        grid.set(0,7,MapTile.CONVEYERBELT);
        grid.set(0,8,MapTile.CONVEYERBELT);
        grid.set(0,9,MapTile.CONVEYERBELT);
        grid.set(1,6,MapTile.CONVEYERBELT);
        grid.set(2,6,MapTile.CONVEYERBELT);
        grid.set(2,7,MapTile.CONVEYERBELT);
        grid.set(2,8,MapTile.CONVEYERBELT);
        grid.set(2,9,MapTile.CONVEYERBELT);
        grid.set(1,9,MapTile.CONVEYERBELT);

        //setting repairsite elements on map
        grid.set(1,2,MapTile.REPAIRSITE);
        grid.set(6,6,MapTile.REPAIRSITE);
        //setting lasers on elements on map
        grid.set(2,0,MapTile.LASER);
        grid.set(2,1,MapTile.LASER);
        grid.set(2,2,MapTile.LASER);
        grid.set(2,3,MapTile.LASER);
        grid.set(2,4,MapTile.LASER);
    }

    public static TiledMap getTiledMap() {
        return tiledMap;
    }

    public void setCamera(float w, float h){
        //camera that is for scaling viewpoint
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w*6   ,h*6);
        camera.translate(-400,-2700);
        viewPort= new FitViewport(w*6, h*6);
    }

    public void buttonCreation(float x, float y){
        Texture buttonTexture = new Texture(Gdx.files.internal("Models/Button.png"));
        Sprite buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setPosition(x,y);
        CardButton = new Cards(x, y, "", 0 , buttonSprite);
    }

    public void lockDown(int damage){
        for(int i=0; i<damage; i++){
            robot.takeDamage();
            if(cardDelt>5){
                cardDelt--;
            }else{
                cardSlotLock--;
            }

        }

    }
}