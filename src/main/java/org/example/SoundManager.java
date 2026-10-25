package org.example;

import javafx.scene.media.AudioClip;

import java.util.HashMap;

public class SoundManager {
    static HashMap<String, AudioClip> sounds = new HashMap<>();
    static String[] soundsLib = {"system/error",
            "units/infantry/default",
            "units/infantry/attack",
            "units/infantry/death"
    };

    public static HashMap<String, AudioClip> getSounds() {
        return sounds;
    }

    public static String[] getSoundsLib() {
        return soundsLib;
    }

    public static AudioClip getSoundInfantry(String name){
        return sounds.get("units/infantry/" + name);
    }

    public static void loadSounds() {
        for (int i = 0; i < soundsLib.length; i++){
            AudioClip audioClip = new AudioClip(SoundManager.class.getResource( "/sounds/" + soundsLib[i] + ".mp3").toExternalForm());
        sounds.put(soundsLib[i], audioClip);
        }
    }

    public static void playSystemSounds(String soundName){
        switch (soundName){
        case "error" ->{
            sounds.get(soundsLib[0]).play();
        }
        default ->{Main.errorMessage("\nSound not exist");}
    }}

    public static void playInfantrySounds(String soundName){
        switch (soundName){
            case "default" ->{
                sounds.get(soundsLib[1]).play();
            }
            case "attack" ->{
                sounds.get(soundsLib[2]).play();
            }
            default ->{System.out.println("\nSound not exist");}
        }
    }

}