package org.example.Interfaces;


import javafx.scene.image.Image;
import org.example.Interface;
import org.example.ResourceLoader;

public class MovePoint extends Interface {

    public MovePoint(int[] xy, String newName){
        super();
        name = newName;
        image = ResourceLoader.spritesLib.get("point");
        coords = xy;
        size = new int[]{64, 64};
        add();
    }

}
