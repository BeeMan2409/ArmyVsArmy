package org.example.Interfaces;

import org.example.Interface;
import org.example.ResourceLoader;

public class PeakBox extends Interface {

    public PeakBox(int[] xy, String newName){
        super();
        name = newName;
        image = ResourceLoader.spritesLib.get("[ ]");
        coords = xy;
        size = new int[]{64, 64};
        System.out.println("Created new peakBox " + name);
        add();
    }

}
