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

    public String isExpressConveyerBelt(int x, int y) {
        if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTNORTH) {
            return "north"+nextConveyerBelt(x, y + 1, Direction.NORTH);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTEAST) {
            return "east"+nextConveyerBelt(x + 1, y, Direction.EAST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTSOUTH) {
            return "south"+nextConveyerBelt(x, y - 1, Direction.SOUTH);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTWEST) {
            return "west"+nextConveyerBelt(x - 1, y, Direction.WEST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTNORTHTOEAST) {
            return "east"+nextConveyerBelt(x + 1, y, Direction.EAST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTNORTHTOWEST) {
            return "west"+nextConveyerBelt(x - 1, y, Direction.WEST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTEASTTONORTH) {
            return "north"+nextConveyerBelt(x, y + 1, Direction.NORTH);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTEASTTOSOUTH) {
            return "south"+nextConveyerBelt(x, y - 1, Direction.SOUTH);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTSOUTHTOEAST) {
            return "east"+nextConveyerBelt(x + 1, y, Direction.EAST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTSOUTHTOWEST) {
            return "west"+nextConveyerBelt(x - 1, y, Direction.WEST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTWESTTONORTH) {
            return "north"+nextConveyerBelt(x, y + 1, Direction.NORTH);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTWESTTOSOUTH) {
            return "south"+nextConveyerBelt(x, y - 1, Direction.SOUTH);
        }
        return "noBelt";
    }

    public String isConveyerBelt(int x, int y) {
        if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTNORTH) {
            return "north"+nextConveyerBelt(x, y + 1, Direction.NORTH);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTEAST) {
            return "east"+nextConveyerBelt(x + 1, y, Direction.EAST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTSOUTH) {
            return "south"+nextConveyerBelt(x, y - 1, Direction.SOUTH);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTWEST) {
            return "west"+nextConveyerBelt(x - 1, y, Direction.WEST);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTNORTH) {
            return "north"+nextConveyerBelt(x, y + 1, Direction.NORTH);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTEAST) {
            return "east"+nextConveyerBelt(x + 1, y, Direction.EAST);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTSOUTH) {
            return "south"+nextConveyerBelt(x, y - 1, Direction.SOUTH);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTWEST) {
            return "west"+nextConveyerBelt(x - 1, y, Direction.WEST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTNORTHTOEAST) {
            return "east"+nextConveyerBelt(x + 1, y, Direction.EAST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTNORTHTOWEST) {
            return "west"+nextConveyerBelt(x - 1, y, Direction.WEST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTEASTTONORTH) {
            return "north"+nextConveyerBelt(x, y + 1, Direction.NORTH);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTEASTTOSOUTH) {
            return "south"+nextConveyerBelt(x, y - 1, Direction.SOUTH);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTSOUTHTOEAST) {
            return "east"+nextConveyerBelt(x + 1, y, Direction.EAST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTSOUTHTOWEST) {
            return "west"+nextConveyerBelt(x - 1, y, Direction.WEST);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTWESTTONORTH) {
            return "north"+nextConveyerBelt(x, y + 1, Direction.NORTH);
        }
        else if (tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTWESTTOSOUTH) {
            return "south"+nextConveyerBelt(x, y - 1, Direction.SOUTH);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTNORTHTOEAST) {
            return "east"+nextConveyerBelt(x + 1, y, Direction.EAST);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTNORTHTOWEST) {
            return "west"+nextConveyerBelt(x - 1, y, Direction.WEST);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTEASTTONORTH) {
            return "north"+nextConveyerBelt(x, y + 1, Direction.NORTH);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTEASTTOSOUTH) {
            return "south"+nextConveyerBelt(x, y - 1, Direction.SOUTH);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTSOUTHTOEAST) {
            return "east"+nextConveyerBelt(x + 1, y, Direction.EAST);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTSOUTHTOWEST) {
            return "west"+nextConveyerBelt(x - 1, y, Direction.WEST);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTWESTTONORTH) {
            return "north"+nextConveyerBelt(x, y + 1, Direction.NORTH);
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTWESTTOSOUTH) {
            return "south"+nextConveyerBelt(x, y - 1, Direction.SOUTH);
        }
        return "noBelt";
    }

    public String nextConveyerBelt(int x, int y, Direction dir) {
        if (dir == Direction.NORTH && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTNORTHTOWEST) {
            return "Left";
        }
        else if(dir == Direction.NORTH && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTNORTHTOEAST) {
            return "Right";
        }
        else if (dir == Direction.EAST && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTEASTTONORTH) {
            return "Left";
        }
        else if (dir == Direction.EAST && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTEASTTOSOUTH) {
            return "Right";
        }
        else if (dir == Direction.SOUTH && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTSOUTHTOEAST) {
            return "Left";
        }
        else if (dir == Direction.SOUTH && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTSOUTHTOWEST) {
            return "Right";
        }
        else if (dir == Direction.WEST && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTWESTTOSOUTH) {
            return "Left";
        }
        else if (dir == Direction.WEST && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTWESTTONORTH) {
            return "Right";
        }
        else if (dir == Direction.NORTH && tiles.get(x, y) == MapTile.CONVEYERBELTNORTHTOWEST) {
            return "Left";
        }
        else if (dir == Direction.NORTH && tiles.get(x, y) == MapTile.CONVEYERBELTNORTHTOEAST) {
            return "Right";
        }
        else if (dir == Direction.EAST && tiles.get(x, y) == MapTile.CONVEYERBELTEASTTONORTH) {
            return "Left";
        }
        else if (dir == Direction.EAST && tiles.get(x, y) == MapTile.CONVEYERBELTEASTTOSOUTH) {
            return "Right";
        }
        else if (dir == Direction.SOUTH && tiles.get(x, y) == MapTile.CONVEYERBELTSOUTHTOEAST) {
            return "Left";
        }
        else if (dir == Direction.SOUTH && tiles.get(x, y) == MapTile.CONVEYERBELTSOUTHTOWEST) {
            return "Right";
        }
        else if (dir == Direction.WEST && tiles.get(x, y) == MapTile.CONVEYERBELTWESTTOSOUTH) {
            return "Left";
        }
        else if (dir == Direction.WEST && tiles.get(x, y) == MapTile.CONVEYERBELTWESTTONORTH) {
            return "Right";
        }
        return "NoTurn";
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

    public Boolean isSpinRight(int x, int y) {
        if (tiles.get(x, y) == MapTile.SPINRIGHT) {
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
            System.out.println("GRATULERER DU VANT SPILET!");
            return true;
        }
        else{
            return false;
        }
    }

    public int isRepairSite(int x, int y, int turn) {
        if(tiles.get(x, y) == MapTile.REPAIRSITE && turn != 4) {
            return 1;
        }
        else if (tiles.get(x, y) == MapTile.REPAIRSITE && turn == 4) {
            return 2;
        }
        else if (tiles.get(x, y) == MapTile.REPAIRSITE2 && turn == 4) {
            return 3;
        }
        else if (tiles.get(x, y) == MapTile.REPAIRSITE2 && turn != 4) {
            return 1;
        }
        return 0;
    }

    public Boolean isLaser(int x, int y){
        if(tiles.get(x,y) == MapTile.LASER){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean wallNearby(Direction dir, int x, int y) {
        if (dir == Direction.NORTH && tiles.get((double) x , y + 0.5) == MapTile.WALL) {
            return true;
        }
        else if(dir == Direction.EAST && tiles.get(x + 0.5, (double) y) == MapTile.WALL) {
            return true;
        }
        else if(dir == Direction.SOUTH && tiles.get((double) x, y - 0.5) == MapTile.WALL) {
            return true;
        }
        else if(dir == Direction.WEST && tiles.get(x - 0.5 , (double) y) == MapTile.WALL) {
            return true;
        }
        else {
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
        if(tiles.get(x,y) == MapTile.WALL){
            return false;
        }
        return true;
    }

    public boolean isOutsideMap(int x , int y){
        if(x>tiles.getWidth() || y>tiles.getHeight() || x<0 || y<0){
            return true;
        }
        return false;
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

    public boolean isConveyer(int x, int y) {
        if (tiles.get(x, y) == MapTile.CONVEYERBELTNORTH) {
            return true;
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTSOUTH){
            return true;
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTEAST){
            return true;
        }
        else if (tiles.get(x, y) == MapTile.CONVEYERBELTWEST){
            return true;
        }
        else{
            return false;
        }
    }

}

