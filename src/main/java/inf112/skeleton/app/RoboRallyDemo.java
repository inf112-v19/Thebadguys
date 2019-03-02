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
    int i = 0;

    CardGUI cardGUI;

    boolean insideSlot0 = false;
    boolean insideSlot1 = false;
    boolean insideSlot2 = false;
    boolean insideSlot3 = false;
    boolean insideSlot4 = false;


    private CardGUI cardSlot0;
    private CardGUI cardSlot1;
    private CardGUI cardSlot2;
    private CardGUI cardSlot3;
    private CardGUI cardSlot4;

    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private float posX, posY;


    private float cardX=400;
    private float cardY=400;



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
        cardGUI = new CardGUI(batch);
        cardGUI.createCard(cardX, cardY);

        //creation of the 5 cardSlots
        cardSlot0 = new CardGUI(batch);
        cardSlot1 = new CardGUI(batch);
        cardSlot2 = new CardGUI(batch);
        cardSlot3 = new CardGUI(batch);
        cardSlot4 = new CardGUI(batch);

        //the positions for the cardslots
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

        cardGUI.getCardSprite().setPosition(cardX, cardY);


        //cardGUI.getCardSlotSprite().setPosition(posX,posY+100);
        batch.begin();
        sprite.draw(batch);


        //rotation of sprite, rotate 90 degrees every 100th gametick
        if(i%100==0){
            //clockwise rotation
            sprite.rotate(90);
            //counter-clockwise
            //sprite.rotate90(false);

            System.out.println("insideSlot0 "+insideSlot0);
            System.out.println("insideSlot1 "+insideSlot1);
            System.out.println("insideSlot2 "+insideSlot2);
            System.out.println("insideSlot3 "+insideSlot3);
            System.out.println("insideSlot4 "+insideSlot4+ "\n");

            /*
            System.out.println("Cardslot0 Position: "+cardSlot0.getCardSlotSprite().getX()+ " " + cardSlot0.getCardSlotSprite().getY()+ " " + (cardSlot0.getCardSlotSprite().getX()+cardSlot0.getCardSlotSprite().getWidth()) + " " + (cardSlot0.getCardSlotSprite().getY()+cardSlot0.getCardSlotSprite().getHeight()));
            System.out.println("Cardslot1 Position: "+cardSlot1.getCardSlotSprite().getX()+ " " + cardSlot1.getCardSlotSprite().getY()+ " " + (cardSlot1.getCardSlotSprite().getX()+cardSlot1.getCardSlotSprite().getWidth()) + " " + (cardSlot1.getCardSlotSprite().getY()+cardSlot1.getCardSlotSprite().getHeight()));
            System.out.println("Cardslot2 Position: "+cardSlot2.getCardSlotSprite().getX()+ " " + cardSlot2.getCardSlotSprite().getY());
            System.out.println("Cardslot3 Position: "+cardSlot3.getCardSlotSprite().getX()+ " " + cardSlot3.getCardSlotSprite().getY());
            System.out.println("Cardslot4 Position: "+cardSlot4.getCardSlotSprite().getX()+ " " + cardSlot4.getCardSlotSprite().getY());
            */
            System.out.println("Card Positon "+cardGUI.getCardSprite().getX() + " " + cardGUI.getCardSprite().getY());
            System.out.println("Tick "+i+"\n");
        }

        //draw the cardslots
        cardSlot0.getCardSlotSprite().draw(batch);
        cardSlot1.getCardSlotSprite().draw(batch);
        cardSlot2.getCardSlotSprite().draw(batch);
        cardSlot3.getCardSlotSprite().draw(batch);
        cardSlot4.getCardSlotSprite().draw(batch);

        //draw the card
        cardGUI.getCardSprite().draw(batch);
        //System.out.println(cardGUI.getCardSprite().getX());

        //denne bruker eg til å sjekke om midten av kortet er inne i kort-sloten
        //cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)

        //trenger denne for å kunne finne den unike kordinaten til X
        //(cardSlot2.getCardSlotSprite().getX()+cardSlot2.getCardSlotSprite().getWidth())

        //if the center of the card is inside the cardslot then it is inside the slot and its new default cordinates will be in the middle of the cardslot
        if((cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)> cardSlot0.getCardSlotSprite().getX() && cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)<(cardSlot0.getCardSlotSprite().getX()+cardSlot0.getCardSlotSprite().getWidth())) && (cardGUI.getCardSprite().getY()+getCardCenterY(cardGUI)> cardSlot0.getCardSlotSprite().getY() && cardGUI.getCardSprite().getY()+getCardCenterY(cardGUI)<cardSlot0.getCardSlotSprite().getHeight())) {
            insideSlot0=true;
            insideSlot1=false;
            insideSlot2=false;
            insideSlot3=false;
            insideSlot4=false;

        }
        else if((cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)> cardSlot1.getCardSlotSprite().getX() && cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)<(cardSlot1.getCardSlotSprite().getX()+cardSlot1.getCardSlotSprite().getWidth())) && (cardGUI.getCardSprite().getY()+getCardCenterY(cardGUI)> cardSlot1.getCardSlotSprite().getY() && cardGUI.getCardSprite().getY()+getCardCenterY(cardGUI)<(cardSlot1.getCardSlotSprite().getY()+cardSlot1.getCardSlotSprite().getHeight()))) {
            insideSlot0=false;
            insideSlot1=true;
            insideSlot2=false;
            insideSlot3=false;
            insideSlot4=false;
        }
        else if((cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)> cardSlot2.getCardSlotSprite().getX() && cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)<(cardSlot2.getCardSlotSprite().getX()+cardSlot2.getCardSlotSprite().getWidth())) && (cardGUI.getCardSprite().getY()+getCardCenterY(cardGUI)> cardSlot2.getCardSlotSprite().getY() && cardGUI.getCardSprite().getY()+getCardCenterY(cardGUI)<(cardSlot2.getCardSlotSprite().getY()+cardSlot2.getCardSlotSprite().getHeight()))) {
            insideSlot0=false;
            insideSlot1=false;
            insideSlot2=true;
            insideSlot3=false;
            insideSlot4=false;
        }
        else if((cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)> cardSlot3.getCardSlotSprite().getX() && cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)<(cardSlot3.getCardSlotSprite().getX()+cardSlot3.getCardSlotSprite().getWidth())) && (cardGUI.getCardSprite().getY()+getCardCenterY(cardGUI)> cardSlot3.getCardSlotSprite().getY() && cardGUI.getCardSprite().getY()+getCardCenterY(cardGUI)<(cardSlot3.getCardSlotSprite().getY()+cardSlot3.getCardSlotSprite().getHeight()))) {
            insideSlot0=false;
            insideSlot1=false;
            insideSlot2=false;
            insideSlot3=true;
            insideSlot4=false;
        }
        else if((cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)> cardSlot4.getCardSlotSprite().getX() && cardGUI.getCardSprite().getX()+getCardCenterX(cardGUI)<(cardSlot4.getCardSlotSprite().getX()+cardSlot4.getCardSlotSprite().getWidth())) && (cardGUI.getCardSprite().getY()+getCardCenterY(cardGUI)> cardSlot4.getCardSlotSprite().getY() && cardGUI.getCardSprite().getY()+getCardCenterY(cardGUI)<(cardSlot4.getCardSlotSprite().getY()+cardSlot4.getCardSlotSprite().getHeight()))){
            insideSlot0=false;
            insideSlot1=false;
            insideSlot2=false;
            insideSlot3=false;
            insideSlot4=true;
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


        if(button == Buttons.LEFT){
            posX = screenX - cardGUI.getCardSprite().getWidth()/2;
            posY = Gdx.graphics.getHeight() - screenY - cardGUI.getCardSprite().getHeight()/2;
            //cardGUI.render(posX, posY);
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

        //viss inni slot 1 ny card position
        if(insideSlot0){
            cardX = cardSlot0.getCardSlotSprite().getX()+getCardSlotCenterX(cardSlot0)-getCardCenterX(cardGUI);
            cardY = cardSlot0.getCardSlotSprite().getY()+getCardSlotCenterY(cardSlot0)-getCardCenterY(cardGUI);
        }
        else if(insideSlot1){
            cardX = cardSlot1.getCardSlotSprite().getX()+getCardSlotCenterX(cardSlot1)-getCardCenterX(cardGUI);
            cardY = cardSlot1.getCardSlotSprite().getY()+getCardSlotCenterY(cardSlot1)-getCardCenterY(cardGUI);
        }
        else if(insideSlot2){
            cardX = cardSlot2.getCardSlotSprite().getX()+getCardSlotCenterX(cardSlot2)-getCardCenterX(cardGUI);
            cardY = cardSlot2.getCardSlotSprite().getY()+getCardSlotCenterY(cardSlot2)-getCardCenterY(cardGUI);
        }
        else if(insideSlot3){
            cardX = cardSlot3.getCardSlotSprite().getX()+getCardSlotCenterX(cardSlot3)-getCardCenterX(cardGUI);
            cardY = cardSlot3.getCardSlotSprite().getY()+getCardSlotCenterY(cardSlot3)-getCardCenterY(cardGUI);
        }
        else if(insideSlot4){
            cardX = cardSlot4.getCardSlotSprite().getX()+getCardSlotCenterX(cardSlot4)-getCardCenterX(cardGUI);
            cardY = cardSlot4.getCardSlotSprite().getY()+getCardSlotCenterY(cardSlot4)-getCardCenterY(cardGUI);
        }
        else{
            cardY= 400;
            cardX = 400;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        cardX= screenX - cardGUI.getCardSprite().getWidth()/2;
        cardY= Gdx.graphics.getHeight() - screenY - cardGUI.getCardSprite().getHeight()/2;

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
    public Float getCardSlotCenterX(CardGUI card){
        return card.getCardSlotSprite().getWidth()/2;
    }

    public Float getCardSlotCenterY(CardGUI card){
        return card.getCardSlotSprite().getHeight()/2;
    }
}