package org.example.Interfaces;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.Interface;
import org.example.JavaInterfaces.Drawable;

public class Line extends Interface implements Drawable {

    private int[] target = {0, 0};

    public Line(String name1){
        super();
        name = name1;
        remove();
    }

    @Override
    public void setPosition(int[] xy){
        coords[0] = xy[0];
        coords[1] = xy[1];
        target[0] = xy[2];
        target[1] = xy[3];
    }

    public void draw(GraphicsContext gc){
        gc.setStroke(Color.GREEN);
        gc.strokeLine(coords[0] * 64 + 32, coords[1] * 64 + 64, target[0] * 64 + 32, target[1] * 64 + 64);
    }

    @Override
    public void remove(){
        System.out.println("remove" + name);
        drawableArrayList.remove(this);
    }

    @Override
    public void add(){
        System.out.println("add" + name);
        drawableArrayList.add(this);
    }

}
