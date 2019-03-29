package map;

import Grid.Direction;
import Grid.IGrid;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.Cards;

import java.util.Map;

public class GameMap implements IGameMap {
    private IGrid<MapTile> tiles;
    private int x;
    private int y;
    private Direction dir = Direction.NORTH;

    public GameMap(IGrid<MapTile> tiles) {
        tiles.copy();
        this.tiles = tiles;
        int counter = 0;
        boolean nNull = true;

        for(int i=0;i<tiles.getWidth();i++) {
            for(int j=0; j<tiles.getHeight(); j++) {
                if(tiles.get(i,j) == MapTile.PLAYER) {
                    counter++;
                    x = i;
                    y = j;

                }
                if(getCell(i,j) == null) {
                    nNull = false;
                }
            }
            if(counter > 1 || !nNull) {
                throw new IllegalArgumentException("Invalid argument, tiles can only contain one player and not null");
            }
        }
    }

    public boolean isHole(int x, int y){
        if(tiles.get(x,y) == MapTile.HOLE){
            return true;
        }

        return false;
    }

    public boolean isSpinLeft(int x, int y){
        if(tiles.get(x,y) == MapTile.SPINLEFT){
            return true;
        }
        return false;
    }
    public Boolean isCheckpoint(int x, int y, int flagspassed) {
        if (tiles.get(x, y) == MapTile.CHECKPOINT1 && flagspassed == 0) {
            return true;
        }
        else if(tiles.get(x, y) == MapTile.CHECKPOINT2 && flagspassed == 1) {
            return true;
        }
        else if(tiles.get(x, y) == MapTile.CHECKPOINT3 && flagspassed == 2) {
            return true;
        }
        else if (tiles.get(x, y) == MapTile.CHECKPOINT4 && flagspassed == 3) {
            System.out.println("GRATULERA DU VANT SPILET!");
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean isLaser(int x, int y){
        if(tiles.get(x,y) == MapTile.LASER){
            return true;
        }
        else{
            return false;
        }
    }

    public MapTile getCell(int x, int y) {
        if(x < 0 || x >= tiles.getWidth())
            throw new IndexOutOfBoundsException();
        if(y < 0 || y >= tiles.getHeight())
            throw new IndexOutOfBoundsException();
        return tiles.get(x,y);
    }

    public void setCell(int x, int y, MapTile maptile){
        if(x < 0 || x >= tiles.getWidth())
            throw new IndexOutOfBoundsException();
        if(y < 0 || y >= tiles.getHeight())
            throw new IndexOutOfBoundsException();
        tiles.set(x, y, maptile);
    }

    public int getHeight() {
        return tiles.getHeight();
    }

    @Override
    public int getPlayerGold() {
        return 0;
    }

    @Override
    public int getPlayerHitPoints() {
        return 0;
    }

    @Override
    public int getWidth() {
        return tiles.getWidth();
    }

    @Override
    public boolean isPlaying() {
        return true;
    }


    public void movePlayer(Direction dir) throws MovePlayerException {
        if(!playerCanGo(dir)) {
            throw new MovePlayerException("The new position is illegal");
        }
        else if(dir == Direction.NORTH) {
            setCell(x, y++, MapTile.OPEN);
            setCell(x, y, MapTile.PLAYER);
        }
        else if(dir == Direction.WEST) {
            setCell(x--, y, MapTile.OPEN);
            setCell(x, y, MapTile.PLAYER);
        }
        else if(dir == Direction.SOUTH) {
            setCell(x, y--, MapTile.OPEN);
            setCell(x, y, MapTile.PLAYER);
        }
        else if(dir == Direction.EAST) {
            setCell(x++, y, MapTile.OPEN);
            setCell(x, y, MapTile.PLAYER);
        }


    }


    public boolean playerCanGo(Direction d){
        if(d == Direction.NORTH){
            if(!isValidPosition(this.x,this.y + 1)){
                return false;
            } else{
                return true;
            }
        }
        else if(d == Direction.EAST){
            if(!isValidPosition(this.x + 1,this.y)){
                return false;
            }else{
                return true;
            }
        }
        else if(d == Direction.WEST){
            if(!isValidPosition(this.x - 1,this.y)){
                return false;
            }else{
                return true;
            }
        }
        else if (d == Direction.SOUTH){
            if(!isValidPosition(this.x, this.y - 1)){
                return false;
            }else{
                return true;
            }
        }

        return true;

    }


    public boolean isValidPosition(int x, int y) {
        if(x>tiles.getWidth() || y>tiles.getHeight() || x<0 || y<0 ||
                tiles.get(x,y) == MapTile.WALL){
            return false;
        }
        return true;
    }

    public int getPosX(){
        return this.x;
    }

    public int getPosY(){
        return this.y;
    }

    public void setPosX(int newX) {
        this.x = newX;
    }

    public void setPosY(int newY) {
        this.y = newY;
    }

    public Direction getDirection() {
        return dir;
    }


    public void rotate_right() {
        if(this.getDirection() == Direction.WEST){
            this.dir = Direction.NORTH;
        }
        else if (this.getDirection() == Direction.NORTH){
            this.dir = Direction.EAST;
        }
        else if (this.getDirection() == Direction.EAST){
            this.dir = Direction.SOUTH;
        }
        else if (this.getDirection() == Direction.SOUTH){
            this.dir = Direction.WEST;
        }
        //this.sprite.rotate(90);
    }

    public void rotate_left() {
        if(this.getDirection() == Direction.NORTH){
            this.dir = Direction.WEST;
        }
        else if (this.getDirection() == Direction.WEST){
            this.dir = Direction.SOUTH;
        }
        else if (this.getDirection() == Direction.SOUTH){
            this.dir = Direction.EAST;
        }
        else if (this.getDirection() == Direction.EAST){
            this.dir = Direction.NORTH;
        }
        //this.sprite.rotate(-90);
    }

    public void moveForward(int amount){
        Direction current_direction = this.getDirection();
        if (current_direction == Direction.NORTH) {
            int newY = this.getPosY() + amount;
            this.setPosY(newY);
        }
        else if (current_direction == Direction.EAST) {
            int newX = this.getPosX() + amount;
            this.setPosX(newX);
        }
        else if (current_direction == Direction.SOUTH) {
            int newY = this.getPosY() - amount;
            this.setPosY(newY);
        }
        else if (current_direction == Direction.WEST) {
            int newX = this.getPosX() - amount;
            this.setPosX(newX);
        }
        else {
            System.out.println("Something went terribly wrong");
        }
    }

    public void move(Cards card){ // gets the command from a card and figures out which command to execute
        String command = card.getCardSprite().getTexture().toString();
        switch (command){
            case "Models/AlleBevegelseKortUtenPrioritet/BackUp.png":
                this.moveForward(-1);
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-1.png":
                this.moveForward(1);
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-2.png":
                this.moveForward(1); // added twice so we can incrementally check for collisions
                this.moveForward(1); // along the robots move-path
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Move-3.png":
                this.moveForward(1);
                this.moveForward(1);
                this.moveForward(1);
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Rotate-90.png":
                this.rotate_right();
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Rotate-180.png":
                this.rotate_right();
                this.rotate_right();
                break;
            case "Models/AlleBevegelseKortUtenPrioritet/Rotate-C90.png":
                this.rotate_left();
                break;
            default:
                System.out.println("Something went wrong");
        }
        // need check if robot is on map, and check for hazard, should integrate with grid
    }






}

