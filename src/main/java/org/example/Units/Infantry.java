package org.example.Units;

import javafx.scene.media.AudioClip;
import org.example.Map;
import org.example.ResourceLoader;
import org.example.SoundManager;
import org.example.Unit;

public class Infantry extends Unit {

    private static int infCount = 0;
    String name(){
        return "Infantry №" + String.valueOf(infCount);
    }

    public Infantry(int[] coords, int team, Map map){
        infCount++;
        super(30, 10, coords, team, map);
        name = name();
        peakSound = SoundManager.getSoundInfantry("default");
        peakSound.play();
        attackSound = SoundManager.getSoundInfantry("attack");
        deathSound = SoundManager.getSoundInfantry("death");
        hitBox = new int[]{64, 64};
        unitSprite = ResourceLoader.spritesLib.get("infantry");
    }
}
