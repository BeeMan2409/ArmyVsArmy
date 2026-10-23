package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;

import static javafx.application.Application.launch;

public class Gui extends Application {

    static Input input;
    static Image grass = ResourceLoader.loadSprite("grass");
    static Image infantry = ResourceLoader.loadSprite("infantry");
    static Unit peakUnit = null;
    static int[] screenSize = {
            Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height
    };
    static int spriteSize = 64;
    static Stage stage;
    static StackPane root;
    static Map map;
    static EntityManager entityManager;
    public static int[] getHitBox(String object){
        switch (object){
            case "unit" ->{return new int[]{spriteSize, spriteSize};}

            case "build" ->{return new int[]{spriteSize, spriteSize};}

            default ->{return new int[]{64, 64};}

        }
    }

    public void start(Stage stage){
        stage.setTitle("ArmyVsArmy");
        Canvas canvas = new Canvas(screenSize[0], screenSize[1]);
        Canvas canvas1 = new Canvas(screenSize[0], screenSize[1]);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc1 = canvas1.getGraphicsContext2D();
        gc.setImageSmoothing(false);
        gc1.setImageSmoothing(false);
        StackPane pane = new StackPane(canvas, canvas1);
        root = pane;
        Scene scene = new Scene(root, screenSize[0], screenSize[1] - 70);
        stage.setScene(scene);
        fillMap(gc);
        stage.show();
        startKeyListener(scene);
        startATimer(gc, gc1);
    }

    void startKeyListener(Scene scene){
        scene.setOnKeyPressed(e ->{
            switch (e.getCode()){
                case D -> {
                    //input.doAction("unit;move;" + peakUnit.getPosition() + ";" + (peakUnit.coords[0] + 1) + ";" + peakUnit.coords[1]);
                }
                default -> {Main.errorMessage("Unknow action");}
            }
        });
        scene.setOnMouseClicked(e ->{
            switch (e.getButton()) {
                case PRIMARY -> {
                    Unit unit = getUnit(new int[]{(int) e.getSceneX(), (int) e.getSceneY()});
                    if (unit != null) {
                        peakUnit = unit;
                    } else {peakUnit = null;}
                }
                case SECONDARY -> {
                    if (peakUnit != null) {
                        peakUnit.move(new int[]{(int) e.getSceneX() / 64, (int) e.getSceneY() / 64});
                    }
                }
            }
        });
    }

    public static void setEntityManager(EntityManager newEM){
        entityManager = newEM;
    }

    public static Unit getUnit(int[] xy){
        Unit unit = null;
        System.out.println("Serching unit at " + xy[0] + " " + xy[1] + "...");
        for (Unit el: entityManager.getUnitsList()) {
            System.out.println("Check unit at " + el.coords[0] * 64 + " " + el.coords[1] * 64 + "...");
            if (xy[0] >= el.coords[0] * 64 &&
                    xy[0] <= el.coords[0] * 64 + el.hitBox[0] &&
                    xy[1] >= el.coords[1] * 64 - 32 &&
                    xy[1] <= el.coords[1] * 64 - 32 + el.hitBox[1]) {
                unit = el;
                unit.peak();
                System.out.println("\nsuccessful " + el.coords[0] * 64 + " " + el.coords[1] * (64 - 32));
                break;
            }
        }
        return unit;
    }

    public static void setInput(Input input1){
        input = input1;
    }

    public static void setMap(Map newMap){
        map = newMap;
    }

    void fillMap(GraphicsContext gc){
        for(int y = 0; y < map.getHeight(); y++){
            for (int x = 0; x < map.getWidth(); x++){
                gc.drawImage(grass, x * spriteSize, y * spriteSize, spriteSize, spriteSize);
            }
        }
    }

    void startATimer(GraphicsContext gc, GraphicsContext gc1){
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, screenSize[0], screenSize[1]);
                gc1.clearRect(0, 0, screenSize[0], screenSize[1]);
                fillMap(gc);
                for (int y = 0; y < map.getHeight(); y++){
                    for(int x = 0; x < map.getWidth(); x++) {
                        if (map.getSprite(x, y) != null) {
                            gc1.drawImage(map.getSprite(x, y), x * spriteSize, y * spriteSize, spriteSize, spriteSize);
                        }
                    }
                }
            }
        };
        animationTimer.start();
    }

    public static void main(String[] args){
        launch(args);
    }
}
