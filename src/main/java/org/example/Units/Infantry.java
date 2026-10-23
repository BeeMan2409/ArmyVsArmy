package org.example.Units;

import javafx.scene.media.AudioClip;
import org.example.Map;
import org.example.SoundManager;
import org.example.Unit;

public class Infantry extends Unit {

    private static int infCount = 0;
    String name(){
        return "Infantry №" + String.valueOf(infCount);
    }

    public Infantry(int[] coords, int team, Map map){
        infCount++;
        super(30, 30, coords, team, map);
        super.name = name();
        peakSound = SoundManager.getSounds().get(SoundManager.getSoundsLib()[1]);
        peakSound.play();
        hitBox = new int[]{64, 64};
    }
}
