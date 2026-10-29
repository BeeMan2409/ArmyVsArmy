package org.example;

import javafx.scene.image.Image;
import org.example.JavaInterfaces.IStats;

public class City implements IStats {

    protected Image buildSprite;
    protected Map map;
    protected int[] rotateXY = {1, 0};
    protected String spriteName;
    protected int hp;
    protected int maxHp;
    protected int team;
    protected int[] startPoint = {0, 0};
    protected int[] coords;
    protected int[] size;
    protected String name;
    protected String getPosition(){
        return coords[0] + ", " + coords[1];
    }
    protected boolean alive = true;
    protected boolean hasSP = false;
    String icon = "B";

    public boolean getHasSP(){return hasSP;}

    public int[] getStartPoint(){return startPoint;}

    @Override
    public int[] getCoords(){
        return coords;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public double getHpLevel(){
        return (hp / maxHp) * 100;
    }

    @Override
    public int getHp(){
        return hp;
    }

    @Override
    public int getMaxHp(){
        return maxHp;
    }

    @Override
    public int[] getSize(){
        return size;
    }

    @Override
    public int getAttack(){
        return 0;
    }

    public City(int hp, int team, int[] coords, Map map){
        this.hp = hp;
        this.team = team;
        this.coords = coords;
        this.map = map;
        map.getBuilds().put(getPosition(), this);
    }

    public void setStartPoint(int[] xy){
        startPoint = xy;
    }

}
