package org.example;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {

    private int[] size = {10,10};
    private HashMap<String, Unit> units;
    private HashMap<String, City> builds;
    private ArrayList<Unit> unitsList;
    private ArrayList<City> buildsList;

    private String icon(int x, int y){
        String xy = x + ", " + y;
        String icon = "[ ]";
        if(this.units.get(xy) != null){
            icon = "[" + this.units.get(xy).icon + "]";

        } else if (this.builds.get(xy) != null) {
            icon = "[" + this.builds.get(xy).icon + "]";
        }
        return icon;
    }

    HashMap getUnits(){
        return units;
    }

    HashMap getBuilds(){
        return builds;
    }

    City getCity(int x, int y){
        String xy = x + ", " + y;
        if (this.builds.get(xy) != null){
            return this.builds.get(xy);
        } else {
            return null;
        }
    }

    Unit getUnit(int x, int y){
        String xy = x + ", " + y;
        if (this.units.get(xy) != null){
            return this.units.get(xy);
        } else {
            return null;
        }
    }

    Image getSprite(int x, int y){
        String xy = x + ", " + y;
        Image name = null;
        if(this.units.get(xy) != null){
            name = this.units.get(xy).unitSprite;

        } else if (this.builds.get(xy) != null) {
            name = this.builds.get(xy).buildSprite;
        }
        return name;
    }

    private EntityManager eM;
    public EntityManager getEM(){
        return eM;
    }

    int getWidth(){
        return size[0];
    }

    int getHeight(){
        return size[1];
    }

    public Map(int x, int y, EntityManager entityManager){
        this.size[0] = x;
        this.size[1] = y;
        eM = entityManager;
        setHashMap(eM);
    }

    void setHashMap(EntityManager entityManager){
        this.units = entityManager.getUnits();
        this.builds = entityManager.getBuilds();
        this.unitsList = entityManager.getUnitsList();
        this.buildsList = entityManager.getBuildsList();
    }

    void show(){
        System.out.print("\n");
        for(int y = 0; y < this.size[0]; y++){
            for (int x = 0; x < size[1]; x++){
                System.out.print(icon(x, y));
            }
            System.out.print("\n");
        }
    }

}
