package org.example;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Unit {

    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int attackRadius;
    protected int moveSpeed;
    protected int[] hitBox = {1, 1};
    protected int[] coords = {0, 0};
    public int[] getScreenCoords(){
        return null;
    }
    public String getPosition(){
        return coords[0] + ", " + coords[1];
    }
    protected int team;
    protected String name;
    protected boolean alive = true;
    protected String icon = "U";
    protected String spriteName = "infantry";
    protected Image unitSprite = ResourceLoader.loadSprite("infantry-ranger");
    protected boolean stels = false;
    protected Map map;
    protected AudioClip peakSound;

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
        //System.out.println("coords " + this.coords[0] + " " + this.coords[1]);
        this.team = team;
        this.map = map;
        map.getEM().addEntity(this);

    }

    public void move(int[] xy){
        System.out.println("Trying to move from " + getPosition() + " to " + xy[0] + ", " + xy[1] + "...");
        if (map.getUnit(xy[0], xy[1]) == null && map.getCity(xy[0], xy[1]) == null ){
            map.getUnits().remove(getPosition());
            System.out.println("delete the unit at " + getPosition());
            coords = xy;
            map.getUnits().put(getPosition(), this);
        } else {
            System.out.println("False! Trying to attack...");
            attack(xy);
        }
    }

    void attack(int[] xy){
        if (map.getUnit(xy[0], xy[1]) != null && map.getUnit(xy[0], xy[1]).team != team){
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
        map.getUnits().remove(enemy.getPosition());
    }

    void killEnemy(City enemy){
        enemy.alive = false;
        map.getBuilds().remove(enemy.getPosition());
    }

    public void peak(){
        System.out.print("\nPeak unit at " + getPosition());
        peakSound.play();
    }

}
