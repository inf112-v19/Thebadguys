package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.sun.istack.internal.Nullable;
import map.IGameMap;

import java.util.ArrayList;

public class CardHandler {

    private SpriteBatch batch;
    private Sprite cardSprite10;
    private BitmapFont font;

    private ArrayList<CardSlots> cardSlotPos;
    private ArrayList<Sprite> randomSpriteList;
    private ArrayList<Sprite> spritePos;
    private Deck Deck;
    private Deck lockedDeck;
    private Cards[] selectedCards;
    private Cards clickedCard;
    private Cards listCard;
    //private Cards CardButton;
    private CardSlots temp;
    private int counter;

    private boolean isDone=false;
    private boolean notFirst=false;
    private boolean isClicked=false;
    private boolean isInside;

    private Robot robot;
    private IGameMap map;
    private int cardDelt=9;
    private int cardSlotLock=0;
    private ArrayList<Sprite> lockedList;
    private int pri;
    private String name;
    private int x;

    public CardHandler(SpriteBatch batch, Robot robot, IGameMap map){
        //creation of all arrays containing positions or cards
        spritePos= new ArrayList<>();
        cardSlotPos= new ArrayList<>();
        randomSpriteList=new ArrayList<>();
        Deck = new Deck();
        lockedDeck=new Deck();
        lockedList = new ArrayList<>();
        selectedCards = new Cards[5];
        this.batch=batch;
        this.robot=robot;
        this.map=map;
        //create the card that Is clicked
        Texture cardTexture = new Texture(Gdx.files.internal("Models/AlleBevegelseKortUtenPrioritet/genericCard.png"));
        cardSprite10 = new Sprite(cardTexture);
        clickedCard=new Cards(0,0, "",0, cardSprite10);
        font = new BitmapFont();
    }

    public void dragged(int screenX, int screenY, Cards CardButton){
        clickedCard.getCardSprite().setPosition(screenX - clickedCard.getCardSprite().getWidth() / 2, Gdx.graphics.getHeight() - screenY - clickedCard.getCardSprite().getHeight() / 2);
    }

    public void letGo(int screenX, int screenY, Cards endTurnBtn, Cards powerDownBtn){
        isInside=false;
        String sendCards = "";
        if (!mainMenu.getMainRunning()){
            if (insideCard(screenX, screenY, powerDownBtn)){
                robot.setPowerdown(true);
            }
            if(insideCard(screenX, screenY, endTurnBtn)){
                for(int i = 0; i < 5; i++) {
                    if (selectedCards[i] != null) {
                        sendCards += selectedCards[i].getName() + "~" + selectedCards[i].getPriority() + "~";
                    }
                }
                String string = "/r/"+RoboRallyDemo.getID()+ sendCards + "/e/";
                System.out.println(sendCards);
                RoboRallyDemo.getClient().getBackendClient().send(string.getBytes());
                isDone=true;
            }

            //method that ajusts a cards location on the screen if it is let go of. the position wil depend if it is inside or outside a cardslot
            ajustCardLocation();

            //create a new clickedCard so that a card does not stick to the mouse when let go of
            clickedCard=new Cards(0,0, "",0, cardSprite10);
        }
    }

    public void click(int button, int screenX, int screenY, Cards CardButton){
        counter=0;
        for(int i=0; i<cardDelt; i++){
            if(insideCard(screenX, screenY,Deck.getDeckList().get(i)) && button == Input.Buttons.LEFT){
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
    }

    //the x cordinate at the centre of a card
    protected Float getCardCenterX(Cards card){
        return card.getCardSprite().getWidth()/2;
    }

    //the y cordinate at the centre of a card
    protected Float getCardCenterY(Cards card){
        return card.getCardSprite().getHeight()/2;
    }

    //the x cordinate at the centre of a cardSlot
    protected Float getCardSlotCenterX(CardSlots card){
        return card.getCardSlotSprite().getWidth()/2;
    }

    //the y cordinate at the center of a cardSlot
    protected Float getCardSlotCenterY(CardSlots card){
        return card.getCardSlotSprite().getHeight()/2;
    }

    //metode for å sjekke om midten av et kort er innenfor ein cardSlot
    protected boolean insideCardSlot(Cards card, CardSlots cardSlotX){
        return (card.getCardSprite().getX() + getCardCenterX(card) > cardSlotX.getCardSlotSprite().getX() && card.getCardSprite().getX() + getCardCenterX(card) < (cardSlotX.getCardSlotSprite().getX() + cardSlotX.getCardSlotSprite().getWidth())) && (card.getCardSprite().getY() + getCardCenterY(card) > cardSlotX.getCardSlotSprite().getY() && card.getCardSprite().getY() + getCardCenterY(card) < (cardSlotX.getCardSlotSprite().getY() + cardSlotX.getCardSlotSprite().getHeight()));
    }

    //metode for å sjekke om muspeikeren er over et kort
    public boolean insideCard(float screenX, float screenY, Cards card){
        float NewscreenY= Gdx.graphics.getHeight() - screenY;
        return (screenX > card.getCardSprite().getX()) && (screenX < (card.getCardSprite().getX() + card.getCardSprite().getWidth())) && (NewscreenY > card.getCardSprite().getY()) && (NewscreenY < (card.getCardSprite().getY() + card.getCardSprite().getHeight()));
    }

    //method to set the position of sprites, if it is the first turn then just set the position of the sprites,
    //if it is not the first turn then I use this method to change the sprites of the cards to get new random cards
    protected void setCardSprites() {
        int x=0;
        randomSpriteList.clear();
        addSprites();
        if(notFirst){
            spritePos.clear();
            Deck.getDeckList().clear();
            updateDeckList();
            extraxtLockedCards();
        }else{
            for (int i = 0; i < cardDelt; i++) {
                spritePos.add(getRandomSprite());
                spritePos.get(i).setPosition(x, 250);
                x+=105;
            }
        }
    }

    public void ajustCardLocation(){
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
    }

    //insert a sprite and get the right priority back
    private int spriteToPri(Sprite sprite){
        for(CardValues card: CardValues.values()){
            if(card.getSprite()==sprite){
                return card.getPriority();
            }
        }
        return 0;
    }

    //insert a sprite and get the right name back
    private String spriteToName(Sprite sprite){
        for(CardValues card: CardValues.values()){
            if(card.getSprite()==sprite){
                return card.getName();
            }
        }
        return null;
    }

    //method to create the card-Objects
    protected void createInitialDecklist(){
        int x=0;
        for(Sprite sprite: spritePos){
            name=spriteToName(sprite);
            pri=spriteToPri(sprite);
            listCard=new Cards(x, 250, name, pri, sprite);
            Deck.getDeckList().add(listCard);
            x+=105;
        }
    }

    //update the Decklist after a turn
    private void updateDeckList(){
        x=0;
        for (int i = 0; i < cardDelt; i++) {
            spritePos.add(getRandomSprite());
            spritePos.get(i).setPosition(x, 250);
            name=spriteToName(spritePos.get(i));
            pri=spriteToPri(spritePos.get(i));
            listCard=new Cards(x, 250, name, pri,spritePos.get(i));
            Deck.getDeckList().add(listCard);
            x+=105;
        }
    }

    //extraxt the locked card
    public void extraxtLockedCards(){
        for (Cards card : selectedCards){
            if(card!=null){
                Sprite temp=card.getCardSprite();
                lockedList.add(temp);
                name=spriteToName(card.getCardSprite());
                pri=spriteToPri(card.getCardSprite());
                listCard=new Cards(x, 250, name, pri,temp);
                lockedDeck.getDeckList().add(listCard);
            }
        }
    }

    //method to draw the cards
    protected void drawCards(){
        for(Cards card: Deck.getDeckList()){
            card.getCardSprite().draw(batch);
            font.draw(batch,""+card.getPriority(),card.getCardSprite().getX()+card.getCardSprite().getWidth()-30,card.getCardSprite().getY()+card.getCardSprite().getHeight()-10);
        }
    }

    //method to create and place cardslots
    protected void createCardSlots(){
        int x=0;
        for(int i=0; i<5; i++){
            temp = new CardSlots(x, 0);
            cardSlotPos.add(temp);
            x+=185;
        }
    }

    //method to draw the cardslots
    protected void drawCardSlots(){
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

    //add all the sprites into the sprite list, and check if the sprite added is one of the sprites that are locked
    private void addSprites(){
        for(CardValues card: CardValues.values()){
            boolean locked=false;
            for(Cards selected: selectedCards){
                if(selected!=null){
                    if(card.getSprite()==selected.getCardSprite()){
                        locked=true;
                        break;
                    }
                }
            }
            if(!locked){
                randomSpriteList.add(card.getSprite());
            }
        }
    }

    //method that empties the selectedCards array, that is used when an turn is over
    protected void nullyFy(){
        for(int i=0; i<selectedCards.length-cardSlotLock; i++){
            selectedCards[i]=null;
        }
        selectedCards[0]=null;
    }

    public void lockDown(){
        if(cardDelt>5){
            cardDelt--;
        }else{
            if(cardSlotLock<5){
                cardSlotLock++;
            }
        }
    }

    public int getCardSlotLock(){
        return cardSlotLock;
    }

    public Cards[] getSelectedCards(){
        return selectedCards;
    }

    public ArrayList<Sprite> getSpritePos(){
        return spritePos;
    }

    public void setNotFirst(boolean notFirst) {
        this.notFirst = notFirst;
    }

    public boolean getNotFirst(){
        return notFirst;
    }

    public void setisDone(boolean isDone){
        this.isDone = isDone;
    }

    public boolean getisDone(){
        return isDone;
    }

    public void drawLockedList(){
        for(Sprite sprite:lockedList){
            sprite.draw(batch);
        }
    }
}

