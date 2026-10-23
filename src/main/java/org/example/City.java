package org.example;

import javafx.scene.image.Image;

public class City {

    protected Image buildSprite;
    protected Map map;
    protected int[] rotateXY = {1, 0};
    protected String spriteName;
    protected int hp;
    protected int team;
    protected int[] startPoint = {0, 0};
    protected int[] coords;
    protected String name;
    protected String getPosition(){
        return coords[0] + ", " + coords[1];
    }
    protected boolean alive = true;
    protected boolean hasSP = false;
    String icon = "B";

    public boolean getHasSP(){return hasSP;}

    public int[] getStartPoint(){return startPoint;}

    public int[] getCoords(){
        return coords;
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
