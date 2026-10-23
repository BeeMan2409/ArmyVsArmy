package org.example;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ResourceLoader {

    public static String[] spritesNames = {
            "[]",
            "barracks",
            "btr",
            "grass",
            "infantry",
            "infantry-ranger",
            "infantry-rpg",
            "infantry-swat",
            "parachute",
            "point",
            "tank",
            "void"};
    public static HashMap<String, Image> spritesLib = new HashMap<>();

    public static void loadResources(){
        for (int i = 0; i < spritesNames.length; i++){
            spritesLib.put(spritesNames[i], loadSprite(spritesNames[i]));
        }
    }

    public static Image loadSprite(String name) {
        System.out.println("load /sprites/" + name + ".png");
        return new Image(
                ResourceLoader.class.getResourceAsStream("/sprites/" + name + ".png")
        );
    }
}