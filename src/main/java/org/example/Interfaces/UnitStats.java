package org.example.Interfaces;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.Interface;
import org.example.JavaInterfaces.Drawable;
import org.example.JavaInterfaces.IStats;

public class UnitStats extends Interface implements Drawable {

    private IStats owner;
    private int hp;
    private int maxHp;
    private int attack;
    private int[] coords = {0, 0};
    final private int tileSize = 64;
    private int allSize = (coords[1] + size[1] + tileSize / 6);
    //private int hpSize = allSize - (allSize * ((hp / maxHp) * 100) / 100);

    public UnitStats(IStats owner){
        super();
        this.owner = owner;
        name = owner.getName() + " stats";
        image = null;
        size = owner.getSize();
        coords = owner.getCoords();
        hp = owner.getHp();
        maxHp = owner.getMaxHp();

        System.out.println("Created new unitStats " + name);
    }

    @Override
    public void remove(){
        if (drawableArrayList.contains(this)){
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
        gc.setLineWidth(5);
//        gc.strokeLine(
//                owner.getCoords()[0] * tileSize,
//                (owner.getCoords()[1] * tileSize + size[1] + tileSize / 6),
//                ((owner.getCoords()[0] * tileSize + size[0]) * (owner.getHpLevel() / 100)),
//                (owner.getCoords()[1] * tileSize + size[1] + tileSize / 6)
//        );
        //System.out.println(owner.getHp() + " " + owner.getMaxHp() + " " + owner.getHpLevel());
        gc.strokeLine(   // Работай родненьки ЧатЖПТ
                // x1: левый край — фиксирован (как в конструкторе для полной полоски)
                owner.getCoords()[0] * tileSize,

                // y: как было
                owner.getCoords()[1] * tileSize + size[1] + tileSize / 6.0,

                // x2: правый край = левый + масштабированная ширина
                owner.getCoords()[0] * tileSize + size[0] * (owner.getHpLevel() / 100.0),

                // y: как было
                owner.getCoords()[1] * tileSize + size[1] + tileSize / 6.0
        );
        gc.setLineWidth(2);
    }

}
