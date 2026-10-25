package org.example;

import org.example.Builds.Barracks;
import org.example.Units.Infantry;

import java.util.HashMap;
import java.util.Scanner;

public class Input {

    private Map map;
    private HashMap<String, Unit> units;
    private HashMap<String, City> builds;

    public Input(Map map, EntityManager EM){
        this.map = map;
        units = EM.getUnits();
        builds = EM.getBuilds();
    }

    public void doAction(String action){
        Server.send(action);
        Client.send(action);
        String[] parts = action.split(";");
        switch (parts[0]){
            case "unit":
                actionForUnits(parts);
                break;
            case "build":
                actionForBuilds(parts);
                break;
            case "EM":
                actionForEM(parts);
                break;

            default: System.out.println("404 - action not found");
            break;
        }
    }

    void actionForEM(String[] parts){
        switch (parts[1]){
            case "addU" ->{
                switch (parts[2]){
                    case "infantry" ->{
                        Unit unit = new Infantry(new int[]{
                                Integer.parseInt(parts[3]),
                                Integer.parseInt(parts[4])},
                                Integer.parseInt(parts[5]),
                                map);
                        map.getEM().addEntity(unit);
                    }
                    default -> {System.out.println("404 - unit type not found");}
                }
            }
            default -> {System.out.println("404 - action not found");}
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
