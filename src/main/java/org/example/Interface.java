package org.example;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class Interface {

    public static HashMap<String, Interface> interfaceHashMap = new HashMap<>();
    public static ArrayList<Interface> interfaceList = new ArrayList<>();
    protected int[] coords = {0, 0};
    protected int[] size = {64, 64};
    protected Image image;
    protected String name = "";

    public String getPosition(){
        return coords[0] + ", " + coords[1];
    }

    public static String getPositionXY(int[] coords){
        return coords[0] + ", " + coords[1];
    }

    public static HashMap<String, Interface> getInterfaceHashMap(){
        return interfaceHashMap;
    }

    public Interface(){
    }

    public void remove(){
        System.out.println("Remove interface element " + name);
        interfaceHashMap.remove(name);
        interfaceList.remove(this);
    }

    public void add(){
        System.out.println("Add interface element " + name);
        interfaceHashMap.put(name, this);
        if (!interfaceList.contains(this)) {
            interfaceList.add(this);
        }
    }

    public void setPosition(int[] xy){
        coords = new int[]{xy[0], xy[1]};
    }
}
