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
    private ArrayList<CardSlots> cardSlotPos;
    private ArrayList<Sprite> randomSpriteList;
    private ArrayList<Sprite> spritePos;
    private Deck Deck;
    private SpriteBatch batch;
    private Texture texture;
    private Texture buttonTexture;
    private Sprite buttonSprite;
    private Sprite sprite;
    private Sprite cardSprite10;
    private Texture cardTexture;
    private float posX, posY;

    //create the initial state of the game
    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        //camera that is for scaling viewpoint
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w * 4  ,h * 4);
        camera.update();

        //creation of the map
        tiledMap = new TmxMapLoader().load("Models/roborallymap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //creation of the robot
        texture = new Texture(Gdx.files.internal("Models/tank.png"));
        sprite = new Sprite(texture);
        posX = -4;
        posY = 6;

        sprite.setPosition(posX+300,posY+300);

        //create the card that Is clicked
        cardTexture = new Texture(Gdx.files.internal("Models/AlleBevegelseKortUtenPrioritet/genericCard.png"));
        cardSprite10 = new Sprite(cardTexture);
        clickedCard=new Cards(batch, 0,0, "",0, cardSprite10);

        buttonTexture = new Texture(Gdx.files.internal("Models/Button.png"));
        buttonSprite= new Sprite(buttonTexture);
        buttonSprite.setPosition(500,500);
        CardButton = new Cards(batch, 500, 500, "", 0 ,buttonSprite);

        //creation of all arrays containing positions or cards
        spritePos= new ArrayList<>();
        cardSlotPos= new ArrayList<>();
        randomSpriteList=new ArrayList();
        Deck = new Deck();

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
            //clockwise rotation
            sprite.rotate(90);
            //System.out.println(Deck.getDeckList().size());
        }

        //draw the cardslots
        drawCardSlots();

        //draw button
        //CardButton.getCardSprite().draw(batch);

        //draw Cards
        drawCards();

        //if the center of the card is inside the cardslot then it is inside the slot and its new default cordinates will be in the middle of the cardslot
        if(insideCardSlot(clickedCard, cardSlotPos.get(0))){
            cardSlotPos.get(0).setInsideCardslot(true);
        }
        else if(insideCardSlot(clickedCard, cardSlotPos.get(1))){
            cardSlotPos.get(1).setInsideCardslot(true);
        }
        else if(insideCardSlot(clickedCard, cardSlotPos.get(2))){
            cardSlotPos.get(2).setInsideCardslot(true);
        }
        else if(insideCardSlot(clickedCard, cardSlotPos.get(3))){
            cardSlotPos.get(3).setInsideCardslot(true);
        }
        else if(insideCardSlot(clickedCard, cardSlotPos.get(4))){
            cardSlotPos.get(4).setInsideCardslot(true);
        }else{
            cardSlotPos.get(0).setInsideCardslot(false);
            cardSlotPos.get(1).setInsideCardslot(false);
            cardSlotPos.get(2).setInsideCardslot(false);
            cardSlotPos.get(3).setInsideCardslot(false);
            cardSlotPos.get(4).setInsideCardslot(false);
        }
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

    //this method is used to click and move a card around on the screen
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(insideCard(screenX, screenY,Deck.getCard(0)) && button == Buttons.LEFT){
            clickedCard=Deck.getCard(0);
        }
        if(insideCard(screenX, screenY, Deck.getCard(1)) && button == Buttons.LEFT){
            clickedCard=Deck.getCard(1);
        }
        if(insideCard(screenX, screenY, Deck.getCard(2)) && button == Buttons.LEFT){
            clickedCard=Deck.getCard(2);
        }
        if(insideCard(screenX, screenY, Deck.getCard(3)) && button == Buttons.LEFT){
            clickedCard=Deck.getCard(3);
        }
        if(insideCard(screenX, screenY, Deck.getCard(4)) && button == Buttons.LEFT){
            clickedCard=Deck.getCard(4);
        }
        if(insideCard(screenX, screenY, Deck.getCard(5)) && button == Buttons.LEFT){
            clickedCard=Deck.getCard(5);
        }
        if(insideCard(screenX, screenY, Deck.getCard(6)) && button == Buttons.LEFT){
            clickedCard=Deck.getCard(6);
        }
        if(insideCard(screenX, screenY, Deck.getCard(7)) && button == Buttons.LEFT){
            clickedCard=Deck.getCard(7);
        }
        if(insideCard(screenX, screenY, Deck.getCard(8)) && button == Buttons.LEFT){
            clickedCard=Deck.getCard(8);
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
        if(cardSlotPos.get(0).getIsInsideSlot()){
            clickedCard.getCardSprite().setPosition(cardSlotPos.get(0).getCardSlotSprite().getX()+getCardSlotCenterX(cardSlotPos.get(0))-getCardCenterX(clickedCard), cardSlotPos.get(0).getCardSlotSprite().getY()+getCardSlotCenterY(cardSlotPos.get(0))-getCardCenterY(clickedCard));
        }
        else if(cardSlotPos.get(1).getIsInsideSlot()){
            clickedCard.getCardSprite().setPosition(cardSlotPos.get(1).getCardSlotSprite().getX()+getCardSlotCenterX(cardSlotPos.get(1))-getCardCenterX(clickedCard),cardSlotPos.get(1).getCardSlotSprite().getY()+getCardSlotCenterY(cardSlotPos.get(1))-getCardCenterY(clickedCard));
        }
        else if(cardSlotPos.get(2).getIsInsideSlot()){
            clickedCard.getCardSprite().setPosition(cardSlotPos.get(2).getCardSlotSprite().getX()+getCardSlotCenterX(cardSlotPos.get(2))-getCardCenterX(clickedCard), cardSlotPos.get(2).getCardSlotSprite().getY()+getCardSlotCenterY(cardSlotPos.get(2))-getCardCenterY(clickedCard));
        }
        else if(cardSlotPos.get(3).getIsInsideSlot()){
            clickedCard.getCardSprite().setPosition(cardSlotPos.get(3).getCardSlotSprite().getX()+getCardSlotCenterX(cardSlotPos.get(3))-getCardCenterX(clickedCard),  cardSlotPos.get(3).getCardSlotSprite().getY()+getCardSlotCenterY(cardSlotPos.get(3))-getCardCenterY(clickedCard));
        }
        else if(cardSlotPos.get(4).getIsInsideSlot()){
            clickedCard.getCardSprite().setPosition(cardSlotPos.get(4).getCardSlotSprite().getX()+getCardSlotCenterX(cardSlotPos.get(4))-getCardCenterX(clickedCard),  cardSlotPos.get(4).getCardSlotSprite().getY()+getCardSlotCenterY(cardSlotPos.get(4))-getCardCenterY(clickedCard));
        }
        else{
            clickedCard.getCardSprite().setPosition(clickedCard.getDefaultPosX(), clickedCard.getDefaultPosY());
        }
        return false;
    }

    @Override
    //if a card is clicked on and draged, then move that clicked card
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
        System.out.println(randomSpriteList.size());
        for (int i = 0; i < 9; i++) {
            //"Models"+(i+1)+".png";
            String path = "Models/AlleBevegelseKortUtenPrioritet/genericCard.png";
            //spritePos.add(setSprite(path));
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
            listCard=new Cards(batch, x, 250, "card"+i, i,spritePos.get(i));
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
            temp = new CardSlots(batch, x, posY, false);
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
}