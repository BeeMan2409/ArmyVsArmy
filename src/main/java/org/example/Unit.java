package org.example;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import org.example.Interfaces.MovePoint;
import org.example.Interfaces.PeakBox;

import java.util.concurrent.atomic.AtomicBoolean;

public class Unit {

    protected Interface movePoint;
    protected Interface peakBox;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int attackRadius;
    protected int moveSpeed = 800;
    protected int[] hitBox = {1, 1};
    protected int[] coords = {0, 0};
    public int[] getScreenCoords(){
        return null;
    }
    public String getPosition(){
        return coords[0] + ", " + coords[1];
    }
    protected int team;
    protected int unitCount;
    protected String name;
    protected boolean alive = true;
    protected String icon = "U";
    protected String unitType = "unit";
    protected Image unitSprite = ResourceLoader.spritesLib.get("infantry-ranger");
    protected boolean stels = false;
    protected Map map;
    protected AudioClip peakSound;
    protected AudioClip attackSound;
    protected AudioClip deathSound;
    protected int[] moveTo;
    protected AtomicBoolean newOrder = new AtomicBoolean(false);
    protected Thread moveThread;

    public int getHp(){
        return hp;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public int getAttack(){
        return attack;
    }

    public Unit(int hp, int attack, int[] coords, int team, Map map){
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.coords = coords;
        this.team = team;
        this.map = map;
        map.getEM().addEntity(this);
        peakBox = new PeakBox(new int[]{coords[0] * 64, coords[1] * 64}, unitType + " " + unitCount + " peakBox");
        peakBox.remove();
    }

    void walkTo(int[] xy){
        Interface point = new MovePoint(new int[]{xy[0] * 64, xy[1] * 64}, unitType + " " + unitCount  + " movePoint");
        movePoint = point;
        int[] oldCoords = new int[]{coords[0] * 64, coords[1] * 64};
        int dx = (xy[0] - coords[0]) * 64;
        int dy = (xy[1] - coords[1]) * 64;
        double distance = Math.sqrt((double) dx * dx + (double) dy * dy);
        int steps = -1;
        newOrder.set(false);
        peakSound.play();
        while (xy[0] != oldCoords[0] || xy[1] != oldCoords[1]){
            steps++;
            System.out.println("Distance to target " + (distance - 64 * steps));
            System.out.println("walk to " + xy[0] + " " + xy[1]);
            if (distance == 0) {   // уже на месте
                break;
            }
            if (distance - 64 * steps <= 64) {   // остаток меньше шага - встаём точно в цель
                try {
                    Thread.sleep(moveSpeed);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                map.getEM().getUnits().remove(getPosition());
                System.out.println("delete the unit at " + getPosition());
                coords = new int[]{ xy[0], xy[1]};
                map.getEM().getUnits().put(getPosition(), this);
                System.out.println("Here " + xy[0] + " " + xy[1]);
                break;
            }
            try {
                Thread.sleep(moveSpeed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            map.getEM().getUnits().remove(getPosition());
            coords = new int[]{
                    coords[0] + (int) Math.round(dx / distance),
                    coords[1] + (int) Math.round(dy / distance)
            };
            map.getEM().getUnits().put(getPosition(), this);
            peakBox.setPosition(new int[]{coords[0] * 64, coords[1] * 64});
            System.out.println("delete the unit at " + getPosition());
        }
        removeMovePoint();
        movePoint = null;
        peakBox.setPosition(new int[]{coords[0] * 64, coords[1] * 64});
    }

    public void move(int[] xy) {
        Thread thread = new Thread(()-> {
            System.out.println("Trying to move from " + getPosition() + " to " + xy[0] + ", " + xy[1] + "...");
            if (map.getUnit(xy[0], xy[1]) == null && map.getCity(xy[0], xy[1]) == null) {
                walkTo(xy);
            } else {
                System.out.println("False! Trying to attack...");
                attack(xy);
            }
        });
        if (moveThread != null && moveThread.isAlive()){
            moveThread.interrupt();
            removeMovePoint();
            movePoint = null;
        }
        moveThread = thread;
        thread.start();
    }

    void attack(int[] xy){
        if (map.getUnit(xy[0], xy[1]) != null && map.getUnit(xy[0], xy[1]).team != team){
            attackSound.play();
            System.out.println("Trying to make him unlive...");
            map.getUnit(xy[0], xy[1]).hp = map.getUnit(xy[0], xy[1]).hp - attack;
            if (map.getUnit(xy[0], xy[1]).hp < 1){
                System.out.println("Trying to kill him...");
                killEnemy(map.getUnit(xy[0], xy[1]));
            }
        } else if (map.getCity(xy[0], xy[1]) != null && map.getCity(xy[0], xy[1]).team != team) {
            System.out.println("Trying to brake this...");
            map.getCity(xy[0], xy[1]).hp = map.getCity(xy[0], xy[1]).hp - attack;
            if (map.getCity(xy[0], xy[1]).hp < 1){
                System.out.println("Trying to brake it...");
                killEnemy(map.getCity(xy[0], xy[1]));
            }
        }
    }

    void killEnemy(Unit enemy){
        enemy.alive = false;
        enemy.deathSound.play();
        map.getUnits().remove(enemy.getPosition());
    }

    void killEnemy(City enemy){
        enemy.alive = false;
        map.getBuilds().remove(enemy.getPosition());
    }

    public void peak(){
        System.out.print("\nPeak unit at " + getPosition());
        peakBox.add();
        System.out.println("Turning peakBox on " + peakBox.name);
        if (movePoint != null){movePoint.add();System.out.println("Turning movePoint on");}
        peakSound.play();
    }

    public void removeMovePoint(){System.out.println("Remove " + movePoint.name);movePoint.remove();}

    public void removePeakBox(){System.out.println("Remove " + peakBox.name);peakBox.remove();}

    public void unPeak(){
        System.out.println("Unpeak unit " + name);
        removePeakBox();
        if (movePoint != null) {
            removeMovePoint();
        }
    }

}
