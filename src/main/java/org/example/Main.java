package org.example;

import org.example.Builds.Barracks;

import javax.xml.transform.sax.SAXResult;
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

    static ArrayList<Unit> units = new ArrayList<>();
    static ArrayList<City> builds = new ArrayList<>();
    static HashMap<String, Unit> mapObjU = new HashMap<>();
    static HashMap<String, City> mapObjB = new HashMap<>();
    static Input masterInput;

    static void chooseGamemod(){

    }

    public static void errorMessage(String er){
        SoundManager.playSystemSounds("error");
        System.out.println("\n" + er);
    }

    static void inputListening(Map map){
        Scanner scanner = new Scanner(System.in);
        Input input = new Input(map, scanner);
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
        SoundManager.loadSounds();
        Scanner scanner1 = new Scanner(System.in);
        scanner = scanner1;
        EntityManager entityManager = new EntityManager();
        Map map = new Map(10, 10, entityManager);
        City barracks = new Barracks(1, new int[]{3, 3}, map);
        map.show();
        inputListening(map);
        masterInput.doAction("build;create;3, 3;infantry");
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