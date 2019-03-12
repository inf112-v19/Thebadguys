package map;

import Grid.Direction;
import Grid.IGrid;

public class GameMap implements IGameMap {
    protected IGrid<MapTile> tiles;
    protected int x;
    protected int y;

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

    public MapTile getCell(int x, int y) {
        if(x < 0 || x >= tiles.getWidth())
            throw new IndexOutOfBoundsException();
        if(y < 0 || y >= tiles.getHeight())
            throw new IndexOutOfBoundsException();
        return tiles.get(x,y);

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
            y++;
        }
        else if(dir == Direction.WEST) {
            x--;
        }
        else if(dir == Direction.SOUTH) {
            y--;

        }
        else if(dir == Direction.EAST) {
            x++;
        }


    }


    public boolean playerCanGo(Direction d){
        if(d == Direction.NORTH){
            if(!isValidPosition(this.x,this.y++)){
                return false;
            }else{
                return true;
            }
        }
        else if(d == Direction.EAST){
            if(!isValidPosition(this.x++,this.y)){
                return false;
            }else{
                return true;
            }
        }
        else if(d == Direction.WEST){
            if(!isValidPosition(this.x--,this.y)){
                return false;
            }else{
                return true;
            }
        }
        else if (d == Direction.SOUTH){
            if(!isValidPosition(this.x, this.y--)){
                return false;
            }else {
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







}

