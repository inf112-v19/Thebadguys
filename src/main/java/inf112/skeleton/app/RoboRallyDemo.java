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

import java.util.ArrayList;

public class RoboRallyDemo implements ApplicationListener, InputProcessor {
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private int i = 0;
    private Cards clickedCard;
    private Cards CardButton;
    private Robot robot;
    private int counter;
    private boolean isDone=false;

    //lister
    private ArrayList<CardSlots> cardSlotPos;
    private ArrayList<Sprite> randomSpriteList;
    private ArrayList<Sprite> spritePos;
    private Deck Deck;
    protected Cards[] selectedCards;

    private SpriteBatch batch;
    private Texture texture;
    private Texture buttonTexture;
    private Sprite buttonSprite;
    private Sprite sprite;
    private Sprite cardSprite10;
    private Texture cardTexture;
    private float posX, posY;
    private boolean test=false;

    //create the initial state of the game

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        counter=0;
        //camera that is for scaling viewpoint
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w * 6  ,h * 6);
        camera.translate(-1000,-2700);

        //creation of the map
        tiledMap = new TmxMapLoader().load("Models/roborallymap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //creation of the robot
        texture = new Texture(Gdx.files.internal("Models/tank.png"));
        sprite = new Sprite(texture);
        posX = -4;
        posY = 6;
        int[] startpos = {Math.round(posX), Math.round(posY)};
        robot = new Robot(sprite, startpos, 0);
        sprite.setPosition(posX+300,posY+600);

        //create the card that Is clicked
        cardTexture = new Texture(Gdx.files.internal("Models/AlleBevegelseKortUtenPrioritet/genericCard.png"));
        cardSprite10 = new Sprite(cardTexture);
        clickedCard=new Cards(0,0, "",0, cardSprite10);

        buttonTexture = new Texture(Gdx.files.internal("Models/Button.png"));
        buttonSprite= new Sprite(buttonTexture);
        buttonSprite.setPosition(800,500);
        CardButton = new Cards(800, 500, "", 0 ,buttonSprite);

        //creation of all arrays containing positions or cards
        spritePos= new ArrayList<>();
        cardSlotPos= new ArrayList<>();
        randomSpriteList=new ArrayList();
        Deck = new Deck();
        selectedCards = new Cards[5];

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
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();
        sprite.draw(batch);

        //rotation of sprite, rotate 90 degrees every 100th gametick
        if(i%100==0){
            for(int i=0; i<selectedCards.length; i++){
                System.out.println(selectedCards[i]);
            }
            System.out.println("\n");
            if(selectedCards[0]!=null && selectedCards[1]!=null && selectedCards[2]!=null && selectedCards[3]!=null && selectedCards[4]!=null && isDone){
              for(int i=0; i<selectedCards.length; i++) {
                  robot.move(selectedCards[i]);
                  if (i == selectedCards.length - 1) {
                      isDone = false;
                      //set the position of all the cardsprites
                      setCardSprites();

                      //create the 9 cards cards
                      createDecklist();

                      nullyFy();
                  }
              }
            }
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
    public void resize(int width, int height) {
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
    //if tou click a card and it is inside a cardSlot a boolean will change (test=true), this I will use in the touchUp method
    // And if you click the Execute button the it will change a boolean value
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        counter=0;
        if( insideCard(screenX, screenY, CardButton)){
            isDone=true;
            return false;
        }
        for(int i=0; i<9; i++){
            if(insideCard(screenX, screenY,Deck.getCard(i)) && button == Buttons.LEFT){
                clickedCard=Deck.getCard(i);
                for(int j=0; j<5; j++){
                    if(insideCardSlot(clickedCard, cardSlotPos.get(j))){
                        test=true;
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
            isDone=true;
            return false;
        }
        //if a card is inside a cardslot and it is released move it into the middle of the slot
        for(int i=0; i<5; i++){
            if(insideCardSlot(clickedCard, cardSlotPos.get(i)) && selectedCards[i]==null){
                if(test){
                    selectedCards[counter]=null;
                    test=false;
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
            isInside=false;
            if(test){
                selectedCards[counter]=null;
                test=false;
            }
        }
        //create a new clickedCard so that a card doesent stick to the mouse when let go of
        clickedCard=new Cards(0,0, "",0, cardSprite10);
        return false;
    }

    @Override
    //if a card is clicked on and draged, then move that clicked card
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if( insideCard(screenX, screenY, CardButton)){
            isDone=true;
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
    public Float getCardCenterX(Cards card){
        return card.getCardSprite().getWidth()/2;
    }

    //the y cordinate at the centre of a card
    public Float getCardCenterY(Cards card){
        return card.getCardSprite().getHeight()/2;
    }

    //the x cordinate at the centre of a cardSlot
    public Float getCardSlotCenterX(CardSlots card){
        return card.getCardSlotSprite().getWidth()/2;
    }

    //the y cordinate at the center of a cardSlot
    public Float getCardSlotCenterY(CardSlots card){
        return card.getCardSlotSprite().getHeight()/2;
    }

    //metode for å sjekke om midten av et kort er innenfor ein cardSlot
    public boolean insideCardSlot(Cards card, CardSlots cardSlotX){
        if((card.getCardSprite().getX()+getCardCenterX(card)> cardSlotX.getCardSlotSprite().getX() && card.getCardSprite().getX()+getCardCenterX(card)<(cardSlotX.getCardSlotSprite().getX()+cardSlotX.getCardSlotSprite().getWidth()))&&(card.getCardSprite().getY()+getCardCenterY(card)> cardSlotX.getCardSlotSprite().getY() && card.getCardSprite().getY()+getCardCenterY(card)<(cardSlotX.getCardSlotSprite().getY()+cardSlotX.getCardSlotSprite().getHeight()))){
            return true;
        }
        return false;
    }

    //metode for å sjekke om muspeikeren er over et kort
    public boolean insideCard(float screenX, float screenY, Cards card){
        float NewscreenY=Gdx.graphics.getHeight() - screenY;
        if((screenX>card.getCardSprite().getX()) && (screenX<(card.getCardSprite().getX()+card.getCardSprite().getWidth())) && (NewscreenY>card.getCardSprite().getY()) && (NewscreenY<(card.getCardSprite().getY()+card.getCardSprite().getHeight()))){
            return true;
        }
        return false;
    }

    //method to get a sprite
    private Sprite setSprite(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        return new Sprite(texture);
    }

    //method to set the position of sprites
    private void setCardSprites() {
        int x=0;
        addSprites();
        for (int i = 0; i < 9; i++) {
            spritePos.add(getRandomSprite());
            spritePos.get(i).setPosition(x, 250);
            x+=105;
        }
    }

    //method to create the card-Objects
    private void createDecklist(){
        Cards listCard;
        int x=0;
        for(int i=0; i<9; i++){
            listCard=new Cards(x, 250, "card"+i, i,spritePos.get(i));
            Deck.addCard(listCard);
            x+=105;
        }
    }

    //method to draw the cards
    private void drawCards(){
        Cards listCard;
        for(int i=0; i<spritePos.size();i++){
            listCard=Deck.getCard(i);
            listCard.getCardSprite().draw(batch);
        }
    }

    //method to create and place cardslots
    private void createCardSlots(){
        CardSlots temp;
        int x=0;
        for(int i=0; i<5; i++){
            temp = new CardSlots(batch, x, posY);
            cardSlotPos.add(temp);
            x+=185;
        }
    }

    //method to draw the cardslots
    private void drawCardSlots(){
        CardSlots temp;
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
        Texture texture0 = new Texture("Models/AlleBevegelseKortUtenPrioritet/BackUp.png");
        Texture texture1 = new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png");
        Texture texture2 = new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-2.png");
        Texture texture3 = new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-3.png");
        Texture texture4 = new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png");
        Texture texture5 = new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png");
        Texture texture6 = new Texture("Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png");
        Texture texture7 = new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-1.png");
        Texture texture8 = new Texture("Models/AlleBevegelseKortUtenPrioritet/Move-2.png");

        Sprite sprite0 = new Sprite(texture0);
        Sprite sprite1 = new Sprite(texture1);
        Sprite sprite2 = new Sprite(texture2);
        Sprite sprite3 = new Sprite(texture3);
        Sprite sprite4 = new Sprite(texture4);
        Sprite sprite5 = new Sprite(texture5);
        Sprite sprite6 = new Sprite(texture6);
        Sprite sprite7 = new Sprite(texture7);
        Sprite sprite8 = new Sprite(texture8);

        randomSpriteList.add(sprite0);
        randomSpriteList.add(sprite1);
        randomSpriteList.add(sprite2);
        randomSpriteList.add(sprite3);
        randomSpriteList.add(sprite4);
        randomSpriteList.add(sprite5);
        randomSpriteList.add(sprite6);
        randomSpriteList.add(sprite7);
        randomSpriteList.add(sprite8);
    }

    //method that empties the selectedCards array, that is used when an turn is over
    public void nullyFy(){
        for(int i=0; i<selectedCards.length; i++){
            selectedCards[i]=null;
        }
    }
}