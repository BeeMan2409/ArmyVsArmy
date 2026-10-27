package org.example;

import org.example.JavaInterfaces.Moveble;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityManager {

    private HashMap<String, Unit> units = new HashMap<>();
    private HashMap<String, City> builds = new HashMap<>();
    private ArrayList<Unit> unitsList = new ArrayList<>();
    private ArrayList<City> buildsList = new ArrayList<>();
    private HashMap<String, Moveble> reservedSpots = new HashMap<>();

    public HashMap<String, Moveble> getReservedSpots(){
        return reservedSpots;
    }

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
        this.units.put(unit.getPosition(), unit);
        this.unitsList.add(unit);
    }

    public void addEntity(City city){
        this.builds.put(city.getPosition(), city);
        this.buildsList.add(city);
    }

    public void removeEntity(String pos, Unit unit){
        this.unitsList.remove(unit);
        this.units.remove(pos);
    }

    public void removeEntity(String pos, City city){
        this.buildsList.remove(city);
        this.builds.remove(pos);
    }

    public void reserveASpot(int[] xy){
        // сделать метод где юниты будут резервировать места на которые двигаются и не смогли прийти в одной место
        // если место занято - найти ближайшее свободное
        // использовать хэшмап с интерфейсом обьектов которые могут резервировать или занимать место 
    }

    void sendToServ(String message){
        Server.send(message);
        Client.send(message);
    }
}
