package org.example;

import org.example.Builds.Barracks;

import java.util.HashMap;
import java.util.Scanner;

public class Input {

    private Map map;
    private HashMap<String, Unit> units;
    private HashMap<String, City> builds;

    public Input(Map map, Scanner scanner){
        this.map = map;
        units = map.getUnits();
        builds = map.getBuilds();
    }

    public void start(Scanner scanner){
        System.out.println("Input started");
        Thread thread = new Thread(()->{
            while (true){
                doAction(Main.getIn());
            }
        });
        thread.start();
    }

    public void doAction(String action){
        String[] parts = action.split(";");
        switch (parts[0]){
            case "unit":
                actionForUnits(parts);
                break;
            case "build":
                actionForBuilds(parts);
                break;

            default: System.out.println("404 - action not found");
            break;
        }
    }

    void actionForUnits(String[] parts) {
            switch (parts[1]) {
                case "move" ->{   // шаблон -> unit;move;pos;newX;newY
                    Unit unit = units.get(parts[2]);
                    if (unit != null) {
                    unit.move(new int[]{
                            Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4])
                    });
                        System.out.print("\nUnit move to " + parts[3] + ", " + parts[4]);
                    } else {
                        System.out.println("Unit on " + parts[2] + " not exist");
                    }
                }
                default -> {
                    System.out.println("404: unknown action");
                }
            }
    }

    void actionForBuilds(String[] parts){
        switch (parts[1]){
            case "create" ->{   // шаблон -> build;create;"position";unitName
                City build = builds.get(parts[2]);
                if (build != null && build instanceof Barracks barracks){
                    barracks.createUnit(parts[3]);
                } else {System.out.println("Build on " + parts[2] + " not exist");}
            }
            case "setSP" ->{   // шаблон -> build;setSP;"position";newX;newY
                City build = builds.get(parts[2]);
                if (build != null && build.getHasSP()){
                    build.setStartPoint(new int[]{
                            Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4])
                    });
                }
            }
            default -> {
                System.out.println("404: unknown action");
            }
        }
    }

}
