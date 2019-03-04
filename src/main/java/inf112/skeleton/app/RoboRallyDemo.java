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

import javax.smartcardio.Card;

public class RoboRallyDemo implements ApplicationListener, InputProcessor {
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    OrthographicCamera camera;
    int i = 0;

    private boolean insideSlot0 = false;
    private boolean insideSlot1 = false;
    private boolean insideSlot2 = false;
    private boolean insideSlot3 = false;
    private boolean insideSlot4 = false;

    private CardSlots cardSlot0;
    private CardSlots cardSlot1;
    private CardSlots cardSlot2;
    private CardSlots cardSlot3;
    private CardSlots cardSlot4;

    private CardGUI clickedCard;

    private CardGUI card0;
    private CardGUI card1;
    private CardGUI card2;
    private CardGUI card3;
    private CardGUI card4;
    private CardGUI card5;
    private CardGUI card6;
    private CardGUI card7;
    private CardGUI card8;

    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
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

        //creation of the card
        card0 = new CardGUI(batch, 0, 250);
        card1 = new CardGUI(batch, 105, 250);
        card2 = new CardGUI(batch, 210, 250);
        card3 = new CardGUI(batch, 315, 250);
        card4 = new CardGUI(batch, 420, 250);
        card5 = new CardGUI(batch, 525, 250);
        card6 = new CardGUI(batch, 630, 250);
        card7 = new CardGUI(batch, 735, 250);
        card8 = new CardGUI(batch, 740, 250);

        clickedCard=new CardGUI(batch, 0,0);


        card0.getCardSprite().setPosition(0, 250);
        card1.getCardSprite().setPosition(105,250);
        card2.getCardSprite().setPosition(210,250);
        card3.getCardSprite().setPosition(315,250);
        card4.getCardSprite().setPosition(420,250);
        card5.getCardSprite().setPosition(525,250);
        card6.getCardSprite().setPosition(630,250);
        card7.getCardSprite().setPosition(735,250);
        card8.getCardSprite().setPosition(840,250);



        //creation of the 5 cardSlots
        cardSlot0 = new CardSlots(batch, posX, posY, false);
        cardSlot1 = new CardSlots(batch, posX+185, posY, false);
        cardSlot2 = new CardSlots(batch, posX+370, posY, false);
        cardSlot3 = new CardSlots(batch, posX+555, posY, false);
        cardSlot4 = new CardSlots(batch, posX+740, posY, false);

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

        //sprite.setPosition(posX,posY);

        //denne kontrolerer movment


        //cardGUI.getCardSlotSprite().setPosition(posX,posY+100);
        batch.begin();
        sprite.draw(batch);


        //rotation of sprite, rotate 90 degrees every 100th gametick
        if(i%100==0){
            //clockwise rotation
            sprite.rotate(90);
            //counter-clockwise
            //sprite.rotate90(false);

          //  System.out.println("insideSlot0 "+insideSlot0);
          //  System.out.println("insideSlot1 "+insideSlot1);
           // System.out.println("insideSlot2 "+insideSlot2);
           // System.out.println("insideSlot3 "+insideSlot3);
          //  System.out.println("insideSlot4 "+insideSlot4+ "\n");
          //  System.out.println("Card Positon "+card0.getCardSprite().getX() + " " + card0.getCardSprite().getY());
         //   System.out.println("Tick "+i+"\n");
        }

        //draw the cardslots
        cardSlot0.getCardSlotSprite().draw(batch);
        cardSlot1.getCardSlotSprite().draw(batch);
        cardSlot2.getCardSlotSprite().draw(batch);
        cardSlot3.getCardSlotSprite().draw(batch);
        cardSlot4.getCardSlotSprite().draw(batch);


        //draw the card
        card0.getCardSprite().draw(batch);
        card1.getCardSprite().draw(batch);
        card2.getCardSprite().draw(batch);
        card3.getCardSprite().draw(batch);
        card4.getCardSprite().draw(batch);
        card5.getCardSprite().draw(batch);
        card6.getCardSprite().draw(batch);
        card7.getCardSprite().draw(batch);
        card8.getCardSprite().draw(batch);

        //System.out.println(cardGUI.getCardSprite().getX());

        //denne bruker eg til å sjekke om midten av kortet er inne i kort-sloten
        //cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)

        //trenger denne for å kunne finne den unike kordinaten til X
        //(cardSlot2.getCardSlotSprite().getX()+cardSlot2.getCardSlotSprite().getWidth())

        //if the center of the card is inside the cardslot then it is inside the slot and its new default cordinates will be in the middle of the cardslot
        if(insideCardSlot(clickedCard, cardSlot0)){
            cardSlot0.setInsideCardslot(true);
        }
        else if(insideCardSlot(clickedCard, cardSlot1)){
            cardSlot1.setInsideCardslot(true);
        }
        else if(insideCardSlot(clickedCard, cardSlot2)){
            cardSlot2.setInsideCardslot(true);
        }
        else if(insideCardSlot(clickedCard, cardSlot3)){
            cardSlot3.setInsideCardslot(true);
        }
        else if(insideCardSlot(clickedCard, cardSlot4)){
            cardSlot4.setInsideCardslot(true);
        }else{
            cardSlot0.setInsideCardslot(false);
            cardSlot1.setInsideCardslot(false);
            cardSlot2.setInsideCardslot(false);
            cardSlot3.setInsideCardslot(false);
            cardSlot4.setInsideCardslot(false);
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

        System.out.println(insideCard(screenX, screenY, card0) +" lmao");
        if(insideCard(screenX, screenY, card0) && button == Buttons.LEFT){
            clickedCard=card0;
        }
        if(insideCard(screenX, screenY, card1) && button == Buttons.LEFT){
            clickedCard=card1;
        }
        if(insideCard(screenX, screenY, card2) && button == Buttons.LEFT){
            clickedCard=card2;
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

    //bruk denne til å lage snappe feature
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(cardSlot0.getIsInsideSlot()){
            clickedCard.getCardSprite().setPosition(cardSlot0.getCardSlotSprite().getX()+getCardSlotCenterX(cardSlot0)-getCardCenterX(clickedCard), cardSlot0.getCardSlotSprite().getY()+getCardSlotCenterY(cardSlot0)-getCardCenterY(clickedCard));
        }
        else if(cardSlot1.getIsInsideSlot()){
            clickedCard.getCardSprite().setPosition(cardSlot1.getCardSlotSprite().getX()+getCardSlotCenterX(cardSlot1)-getCardCenterX(card0),cardSlot1.getCardSlotSprite().getY()+getCardSlotCenterY(cardSlot1)-getCardCenterY(card0));
        }
        else if(cardSlot2.getIsInsideSlot()){
            clickedCard.getCardSprite().setPosition(cardSlot2.getCardSlotSprite().getX()+getCardSlotCenterX(cardSlot2)-getCardCenterX(card0), cardSlot2.getCardSlotSprite().getY()+getCardSlotCenterY(cardSlot2)-getCardCenterY(card0));
        }
        else if(cardSlot3.getIsInsideSlot()){
            clickedCard.getCardSprite().setPosition(cardSlot3.getCardSlotSprite().getX()+getCardSlotCenterX(cardSlot3)-getCardCenterX(card0),  cardSlot3.getCardSlotSprite().getY()+getCardSlotCenterY(cardSlot3)-getCardCenterY(card0));
        }
        else if(cardSlot4.getIsInsideSlot()){
            clickedCard.getCardSprite().setPosition(cardSlot4.getCardSlotSprite().getX()+getCardSlotCenterX(cardSlot4)-getCardCenterX(card0),  cardSlot4.getCardSlotSprite().getY()+getCardSlotCenterY(cardSlot4)-getCardCenterY(card0));
        }
        else{
            clickedCard.getCardSprite().setPosition(clickedCard.getDefaultPosX(), clickedCard.getDefaultPosY());
        }
        return false;
    }

    @Override
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
    public Float getCardCenterX(CardGUI card){
        return card.getCardSprite().getWidth()/2;
    }


    //the y cordinate at the centre of a card
    public Float getCardCenterY(CardGUI card){
        return card.getCardSprite().getHeight()/2;
    }

    //the x cordinate at the centre of a card
    public Float getCardSlotCenterX(CardSlots card){
        return card.getCardSlotSprite().getWidth()/2;
    }

    public Float getCardSlotCenterY(CardSlots card){
        return card.getCardSlotSprite().getHeight()/2;
    }

    //metode for å sjekke om midten av et kort er innenfor ein cardSlot
    public boolean insideCardSlot(CardGUI card, CardSlots cardSlotX){
        if((card.getCardSprite().getX()+getCardCenterX(card)> cardSlotX.getCardSlotSprite().getX() && card.getCardSprite().getX()+getCardCenterX(card)<(cardSlotX.getCardSlotSprite().getX()+cardSlotX.getCardSlotSprite().getWidth()))&&(card.getCardSprite().getY()+getCardCenterY(card)> cardSlotX.getCardSlotSprite().getY() && card.getCardSprite().getY()+getCardCenterY(card)<(cardSlotX.getCardSlotSprite().getY()+cardSlotX.getCardSlotSprite().getHeight()))){
            return true;
        }
        return false;
    }

    //metode for å sjekke om muspeikeren er over et kort
    public boolean insideCard(float screenX, float screenY, CardGUI card){
        float NewscreenY=Gdx.graphics.getHeight() - screenY;
        if((screenX>card.getCardSprite().getX()) && (screenX<(card.getCardSprite().getX()+card.getCardSprite().getWidth())) && (NewscreenY>card.getCardSprite().getY()) && (NewscreenY<(card.getCardSprite().getY()+card.getCardSprite().getHeight()))){
            return true;
        }
        return false;
    }
}