package org.example;

import java.util.HashMap;

public class Player {

    private int team = 0;
    private int money = 0;
    private String color = "white";
    private boolean lose = false;
    private HashMap<int[], Unit> units;
    private HashMap<int[], City> builds;

    public Player(int team, HashMap u, HashMap b){
        this.team = team;
        this.units = u;
        this.builds = b;
    }
}
