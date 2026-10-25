package org.example;

import org.example.Builds.Barracks;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static int port = 8080;
    static String ip = "localhost";
    static boolean endGame = false;
    static boolean isServer = true;
    static Scanner scanner;

    public static String getIn(){
        return scanner.nextLine();
    }

    static int[] screenSize = {
            Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height
    };

    static Input masterInput;

    static void chooseGamemod(){

    }

    public static void errorMessage(String er){
        SoundManager.playSystemSounds("error");
        System.out.println("\n" + er);
    }

    static void inputListening(Map map, EntityManager EM){
        Scanner scanner = new Scanner(System.in);
        Input input = new Input(map, EM);
        masterInput = input;
        Thread thread = new Thread(()->{
            while (true){
                input.doAction(scanner.nextLine());
                map.show();
            }
        });
        thread.start();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ResourceLoader.loadResources();
        SoundManager.loadSounds();
        Scanner scanner1 = new Scanner(System.in);
        scanner = scanner1;
        EntityManager entityManager = new EntityManager();
        Map map = new Map(21, 11, entityManager);
        City barracks = new Barracks(1, new int[]{3, 3}, map);
        //City barracks1 = new Barracks(2, new int[]{15, 7}, map);
        map.show();
        inputListening(map, entityManager);
        masterInput.doAction("build;create;3, 3;infantry");
        //masterInput.doAction("build;create;15, 7;infantry");
        Gui.setMap(map);
        Gui.setInput(masterInput);
        Gui.setEntityManager(entityManager);
        Gui.main(args);
//        chooseGamemod();
//        if(isServer){
//            Server.main(args);
//        } else {
//            Client.main(args);
//        }
        while(endGame){}

    }
}