package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityManager {

    private HashMap units = new HashMap<>();
    private HashMap builds = new HashMap<>();
    private ArrayList<Unit> unitsList = new ArrayList<>();
    private ArrayList<City> buildsList = new ArrayList<>();

    public HashMap<String, City> getBuilds() {
        return builds;
    }

    public ArrayList<City> getBuildsList() {
        return buildsList;
    }

    public HashMap<String, Unit> getUnits() {
        return units;
    }

    public ArrayList<Unit> getUnitsList() {
        return unitsList;
    }

    public EntityManager(){}

    public void addEntity(Unit unit){
        this.units.put(unit.coords, unit);
        this.unitsList.add(unit);
    }

    public void addEntity(City city){
        this.builds.put(city.coords, city);
        this.buildsList.add(city);
    }
}
