package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import org.example.Interfaces.DragBox;

import java.awt.*;

import static javafx.application.Application.launch;

public class Gui extends Application {

    static Input input;
    static Image grass = ResourceLoader.loadSprite("grass");
    static Image infantry = ResourceLoader.loadSprite("infantry");
    static Unit peakUnit = null;
    static Robot robot;
    static int[] screenSize = {
            Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height
    };
    static int spriteSize = 64;
    static Stage stage;
    static StackPane root;
    static Map map;
    static EntityManager entityManager;
    static DragBox dragBox = new DragBox("dragbox gui");
    public static int[] getHitBox(String object){
        switch (object){
            case "unit" ->{return new int[]{spriteSize, spriteSize};}

            case "build" ->{return new int[]{spriteSize, spriteSize};}

            default ->{return new int[]{64, 64};}

        }
    }

    public void start(Stage stage){
        stage.setTitle("ArmyVsArmy");
        robot  = new Robot();
        Canvas canvas = new Canvas(screenSize[0], screenSize[1]);
        Canvas canvas1 = new Canvas(screenSize[0], screenSize[1]);
        Canvas canvasInt = new Canvas(screenSize[0], screenSize[1]);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc1 = canvas1.getGraphicsContext2D();
        GraphicsContext gcInt = canvasInt.getGraphicsContext2D();
        gcInt.setStroke(Color.RED);
        gcInt.setLineWidth(5);
        gc.setImageSmoothing(false);
        gc1.setImageSmoothing(false);
        gcInt.setImageSmoothing(false);
        StackPane pane = new StackPane(canvas, canvas1, canvasInt);
        root = pane;
        Scene scene = new Scene(root, screenSize[0], screenSize[1] - 70);
        stage.setScene(scene);
        fillMap(gc);
        stage.show();
        startKeyListener(scene, gcInt);
        startATimer(gc, gc1, gcInt);
    }

    void startKeyListener(Scene scene, GraphicsContext gcInt){
        scene.setOnKeyPressed(e ->{
            switch (e.getCode()){
                case D -> {
                    input.doAction("build;create;3, 3;infantry");
                    //input.doAction("unit;move;" + peakUnit.getPosition() + ";" + (peakUnit.coords[0] + 1) + ";" + peakUnit.coords[1]);
                }
                case W -> {
                    if (peakUnit != null) {
                        peakUnit.peakBox.remove();
                    }
                }
                default -> {Main.errorMessage("Unknow action");}
            }
        });
        scene.setOnMouseClicked(e ->{
            switch (e.getButton()) {
                case PRIMARY -> {
                    Unit unit = getUnit(new int[]{(int) e.getSceneX(), (int) e.getSceneY()});
                    if (unit != null) {
                        if (peakUnit != null) {
                            peakUnit.unPeak();
                            System.out.println("change unit " + peakUnit.name + " at ");
                        }
                        peakUnit = unit;
                        System.out.println(peakUnit.name);
                        peakUnit.peak();
                    } else {
                        if (peakUnit != null) {
                            peakUnit.unPeak();
                            System.out.println("change unit " + peakUnit.name + " at null");
                        }
                        peakUnit = null;
                    }
                }
                case SECONDARY -> {
                    if (peakUnit != null) {
                        input.doAction("unit;move;" + peakUnit.getPosition() + ";" + (((int) e.getSceneX() / 64) + ";" + ((int) e.getSceneY() / 64)));
                        //peakUnit.move(new int[]{(int) e.getSceneX() / 64, (int) e.getSceneY() / 64});
                    }
                }
            }
        });
        scene.setOnMouseDragged(e ->{
            dragBox.setCoordsX1Y1(new int[]{(int)robot.getMouseX(), (int)robot.getMouseY()});
        });
        scene.setOnMousePressed(e ->{
            dragBox.setCoordsXY(new int[]{(int)robot.getMouseX(), (int)robot.getMouseY()});
            dragBox.add();
        });
        scene.setOnMouseReleased(e ->{dragBox.remove();});
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
        gc.clearRect(0, 0, screenSize[0], screenSize[1]);
        for(int y = 0; y < map.getHeight(); y++){
            for (int x = 0; x < map.getWidth(); x++){
                gc.drawImage(grass, x * spriteSize, y * spriteSize, spriteSize, spriteSize);
            }
        }
    }

    void startATimer(GraphicsContext gc, GraphicsContext gc1, GraphicsContext gcInt){
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gcInt.clearRect(0, 0, screenSize[0], screenSize[1]);
                for (Interface el: Interface.interfaceList){
                    gcInt.drawImage(el.image, el.coords[0], el.coords[1], el.size[0], el.size[1]);
                    dragBox.draw(gcInt);
                }
                fillMap(gc);
                gc1.clearRect(0, 0, screenSize[0], screenSize[1]);
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
