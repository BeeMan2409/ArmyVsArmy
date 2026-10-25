package org.example.Interfaces;

import javafx.scene.canvas.GraphicsContext;
import org.example.Interface;
import org.example.ResourceLoader;

public class DragBox extends Interface {

    public DragBox(String newName){
        super();
        name = newName;
        image = null;
        size = new int[]{64, 64};
        coords = new int[]{0, 0, 0, 0};
        System.out.println("Created new peakBox " + name);
    }

    public void setCoordsXY(int[] xy){
        coords[0] = xy[0];
        coords[1] = xy[1];
    }

    public void setCoordsX1Y1(int[] xy){
        coords[2] = xy[0];
        coords[3] = xy[1];
    }

    public void draw(GraphicsContext gc) {
            gc.strokeLine(coords[0], coords[1], coords[2], coords[1]); //x1 y1 x2 y1
            gc.strokeLine(coords[0], coords[1], coords[0], coords[3]); //x1 y1 x1 y2
            gc.strokeLine(coords[2], coords[3], coords[2], coords[1]); //x2 y2 x2 y1
            gc.strokeLine(coords[2], coords[3], coords[0], coords[3]); //x2 y2 x1 y2
    }
}
