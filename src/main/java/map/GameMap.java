package map;

import Grid.Direction;
import Grid.IGrid;
import inf112.skeleton.app.AIRobot;
import inf112.skeleton.app.Robot;
import inf112.skeleton.app.IRobot;

public class GameMap implements IGameMap {
    private IGrid<MapTile> tiles;

    public GameMap(IGrid<MapTile> tiles) {
        tiles.copy();
        this.tiles = tiles;
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
        } else if (dir == Direction.SOUTH && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTSOUTHTOWEST) {
            return "Right";
        } else if (dir == Direction.WEST && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTWESTTOSOUTH) {
            return "Left";
        } else if (dir == Direction.WEST && tiles.get(x, y) == MapTile.EXPRESSCONVEYERBELTWESTTONORTH) {
            return "Right";
        } else if (dir == Direction.NORTH && tiles.get(x, y) == MapTile.CONVEYERBELTNORTHTOWEST) {
            return "Left";
        } else if (dir == Direction.NORTH && tiles.get(x, y) == MapTile.CONVEYERBELTNORTHTOEAST) {
            return "Right";
        } else if (dir == Direction.EAST && tiles.get(x, y) == MapTile.CONVEYERBELTEASTTONORTH) {
            return "Left";
        } else if (dir == Direction.EAST && tiles.get(x, y) == MapTile.CONVEYERBELTEASTTOSOUTH) {
            return "Right";
        } else if (dir == Direction.SOUTH && tiles.get(x, y) == MapTile.CONVEYERBELTSOUTHTOEAST) {
            return "Left";
        } else if (dir == Direction.SOUTH && tiles.get(x, y) == MapTile.CONVEYERBELTSOUTHTOWEST) {
            return "Right";
        } else if (dir == Direction.WEST && tiles.get(x, y) == MapTile.CONVEYERBELTWESTTOSOUTH) {
            return "Left";
        } else if (dir == Direction.WEST && tiles.get(x, y) == MapTile.CONVEYERBELTWESTTONORTH) {
            return "Right";
        }
        return "NoTurn";
    }

    public boolean isHole(int x, int y) {
        if (tiles.get(x, y) == MapTile.HOLE) {
            return true;
        }
        return false;
    }

    public boolean isSpinLeft(int x, int y) {
        if (tiles.get(x, y) == MapTile.SPINLEFT) {
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
        } else if (tiles.get(x, y) == MapTile.CHECKPOINT2 && flagspassed == 1) {
            return true;
        } else if (tiles.get(x, y) == MapTile.CHECKPOINT3 && flagspassed == 2) {
            return true;
        } else if (tiles.get(x, y) == MapTile.CHECKPOINT4 && flagspassed == 3) {
            System.out.println("GRATULERER DU VANT SPILET!");
            return true;
        } else {
            return false;
        }
    }

    public int isRepairSite(int x, int y, int turn) {
        if (tiles.get(x, y) == MapTile.REPAIRSITE && turn != 4) {
            return 1;
        } else if (tiles.get(x, y) == MapTile.REPAIRSITE && turn == 4) {
            return 2;
        } else if (tiles.get(x, y) == MapTile.REPAIRSITE2 && turn == 4) {
            return 3;
        } else if (tiles.get(x, y) == MapTile.REPAIRSITE2 && turn != 4) {
            return 1;
        }
        return 0;
    }

    public void fireLasers(IRobot robot) {
        for (int i = 0; i < tiles.getWidth(); i++) {
            for (int j = 0; j < tiles.getHeight(); j++) {
                if (tiles.get(i, j) == MapTile.LASERNORTH) {
                    int targetHit = 0;
                    int tempY1 = j;
                    while (targetHit == 0 && tempY1 < 12) {
                        if (robot.getAlive() && robot.getPosX() == i && robot.getPosY() == tempY1) {
                            robot.takeDamage();
                            targetHit = 1;
                        } else if (tiles.get((double) i, tempY1 + 0.5) == MapTile.WALL) {
                            targetHit = 1;
                        }
                        tempY1++;
                    }
                } else if (tiles.get(i, j) == MapTile.LASEREAST) {
                    int targetHit = 0;
                    int tempX1 = i;
                    while (targetHit == 0 && tempX1 != 12) {
                        if (robot.getAlive() && robot.getPosX() == tempX1 && robot.getPosY() == j) {
                            robot.takeDamage();
                            targetHit = 1;
                        } else if (tiles.get(tempX1 + 0.5, j) == MapTile.WALL) {
                            targetHit = 1;
                        }
                        tempX1++;
                    }
                } else if (tiles.get(i, j) == MapTile.LASERSOUTH) {
                    int targetHit = 0;
                    int tempY1 = j;
                    while (targetHit == 0 && tempY1 != -1) {
                        if (robot.getAlive() && robot.getPosX() == i && robot.getPosY() == tempY1) {
                            robot.takeDamage();
                            targetHit = 1;
                        } else if (tiles.get(i, tempY1 - 0.5) == MapTile.WALL) {
                            targetHit = 1;
                        }
                        tempY1--;
                    }
                } else if (tiles.get(i, j) == MapTile.LASERWEST) {
                    int targetHit = 0;
                    int tempX1 = i;
                    while (targetHit == 0 && tempX1 != -1) {
                        if (robot.getAlive() && robot.getPosX() == tempX1 && robot.getPosY() == j) {
                            robot.takeDamage();
                            targetHit = 1;
                        } else if (tiles.get(tempX1 - 0.5, j) == MapTile.WALL) {
                            targetHit = 1;
                        }
                        tempX1--;
                    }
                }
            }
        }
    }

    public void robotFireLasers(Robot[] robots, Robot shooter, int shooterID) {
        boolean targetHit = false;
        for (int i = 0; i < robots.length; i++) {
            if (i != shooterID) {
                if (robots[i] != null && robots[i].getAlive() && shooter.getDirection() == Direction.NORTH) {
                    int tempY1 = shooter.getPosY()+1;
                    while (targetHit == false && tempY1 < 12) {
                        if (robots[i].getPosX() == shooter.getPosX() && robots[i].getPosY() == tempY1) {
                            robots[i].takeDamage();
                            targetHit = true;
                        } else if (getTiles().get((double) shooter.getPosX(), tempY1 + 0.5) == MapTile.WALL) {
                            targetHit = true;
                        }
                        tempY1++;
                    }
                } else if (robots[i] != null && robots[i].getAlive() && shooter.getDirection() == Direction.EAST) {
                    int tempX1 = shooter.getPosX()+1;
                    while (targetHit == false && tempX1 != 12) {
                        if (robots[i].getPosX() == tempX1 && robots[i].getPosY() == shooter.getPosY()) {
                            robots[i].takeDamage();
                            targetHit = true;
                        } else if (getTiles().get(tempX1 + 0.5, shooter.getPosY()) == MapTile.WALL) {
                            targetHit = true;
                        }
                        tempX1++;
                    }
                } else if (robots[i] != null && robots[i].getAlive() && shooter.getDirection() == Direction.SOUTH) {
                    int tempY1 = shooter.getPosY()-1;
                    while (targetHit == false && tempY1 != -1) {
                        if (robots[i].getPosX() == shooter.getPosX() && robots[i].getPosY() == tempY1) {
                            robots[i].takeDamage();
                            targetHit = true;
                        } else if (getTiles().get(shooter.getPosX(), tempY1 - 0.5) == MapTile.WALL) {
                            targetHit = true;
                        }
                        tempY1--;
                    }
                } else if (robots[i] != null && robots[i].getAlive() && shooter.getDirection() == Direction.WEST) {
                    int tempX1 = shooter.getPosX()-1;
                    while (targetHit == false && tempX1 != -1) {
                        if (robots[i].getPosX() == tempX1 && robots[i].getPosY() == shooter.getPosY()) {
                            robots[i].takeDamage();
                            targetHit = true;
                        } else if (getTiles().get(tempX1 - 0.5, shooter.getPosY()) == MapTile.WALL) {
                            targetHit = true;
                        }
                        tempX1--;
                    }
                }
            }
        }
    }

    public Boolean wallNearby(Direction dir, int x, int y, int amount) {
        if (dir == Direction.NORTH && tiles.get((double) x, y + 0.5) == MapTile.WALL) {
            return true;
        } else if (dir == Direction.EAST && tiles.get(x + 0.5, (double) y) == MapTile.WALL) {
            return true;
        } else if (dir == Direction.SOUTH && tiles.get((double) x, y - 0.5) == MapTile.WALL) {
            return true;
        } else if (dir == Direction.WEST && tiles.get(x - 0.5, (double) y) == MapTile.WALL) {
            return true;
        } else if (dir == Direction.EAST && amount == -1 && tiles.get(x - 0.5, (double) y) == MapTile.WALL){
            return true;
        }else if (dir == Direction.SOUTH && amount == -1 && tiles.get(x, (double) y + 0.5) == MapTile.WALL){
            return true;
        }else if (dir == Direction.WEST && amount == -1 && tiles.get(x + 0.5, (double) y) == MapTile.WALL){
            return true;
        }else if (dir == Direction.NORTH && amount == -1 && tiles.get(x, (double) y-0.5) == MapTile.WALL){
            return true;
        } else {
            return false;
        }
    }

    public Boolean convWallNearby(Direction dir, int x, int y) {
        if (dir == Direction.NORTH && tiles.get((double) x, y + 0.5) == MapTile.WALL) {
            return true;
        } else if (dir == Direction.EAST && tiles.get(x + 0.5, (double) y) == MapTile.WALL) {
            return true;
        } else if (dir == Direction.SOUTH && tiles.get((double) x, y - 0.5) == MapTile.WALL) {
            return true;
        } else if (dir == Direction.WEST && tiles.get(x - 0.5, (double) y) == MapTile.WALL) {
            return true;
        } else {
            return false;
        }
    }

    public int getHeight() {
        return tiles.getHeight();
    }

    @Override
    public int getWidth() {
        return tiles.getWidth();
    }
    
    public IGrid<MapTile> getTiles(){
        return tiles;
    }

}

