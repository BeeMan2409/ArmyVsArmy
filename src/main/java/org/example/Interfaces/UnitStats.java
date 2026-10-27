package org.example.Interfaces;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.Interface;
import org.example.JavaInterfaces.Drawable;
import org.example.JavaInterfaces.IStats;

public class UnitStats extends Interface implements Drawable {

    private int hp;
    private int maxHp;
    private int attack;
    private int[] coords = {0, 0};
    final private int tileSize = 64;

    public UnitStats(IStats owner){
        super();
        name = owner.getName() + " stats";
        image = null;
        size = owner.getSize();
        coords[0] = owner.getCoords()[0] * tileSize;
        coords[1] = owner.getCoords()[1] * tileSize;
        hp = owner.getHp();
        maxHp = owner.getMaxHp();

        System.out.println("Created new unitStats " + name);
    }

    @Override
    public void remove(){
        if (!drawableArrayList.contains(this)){
        drawableArrayList.remove(this);
        }
    }

    @Override
    public void add(){
        if (!drawableArrayList.contains(this)){
            drawableArrayList.add(this);
        }
    }

    public void draw(GraphicsContext gc){
        gc.setStroke(Color.RED);
        gc.strokeLine(
                coords[0] + size[0],
                (coords[1] + size[1] + tileSize / 6),
                (coords[0] + size[0] * 2),
                (coords[1] + size[1] + tileSize / 6)
        );
    }

}
