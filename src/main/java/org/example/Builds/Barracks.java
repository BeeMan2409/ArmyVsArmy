package org.example.Builds;

import org.example.City;
import org.example.Map;
import org.example.ResourceLoader;
import org.example.Unit;
import org.example.Units.Infantry;

public class Barracks extends City {

    public Barracks(int team, int[] coords, Map map){
        super(30, team, coords, map);
        hasSP = true;
        startPoint[0] = coords[0] + rotateXY[0];
        startPoint[1] = coords[0] + rotateXY[1];
        buildSprite = ResourceLoader.loadSprite("barracks");
    }

    public void createUnit(String name){
        Unit unit = newUnit(name);
        unit.move(startPoint);
    }

    private Unit newUnit(String name){
        switch (name){
            case "infantry" -> {
                Unit unit = new Infantry(coords, team, map);

                return unit;
            }
            default -> {return null;}
        }
    }

}
