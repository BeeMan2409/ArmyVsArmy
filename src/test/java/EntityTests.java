import javafx.application.Application;
import org.example.*;
import org.example.Builds.Barracks;
import org.example.Units.Infantry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EntityTests {

    @BeforeEach
    void start(){
        SoundManager.loadSounds();
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 7 ,4",
            "1, 4, 8, 8",
            "6,7 , 6 ,9"
    })
    void attack(int uX, int uY, int eX, int eY){
        HashMap<String, Unit> u = new HashMap<>();
        EntityManager entityManager = new EntityManager();
        Map map = new Map(10, 10, entityManager);
        int[] uCoords = {uX, uY};
        int[] eCoords = {eX, eY};
        Unit unit = new Infantry(uCoords, 1 , map);
        Unit enemy = new Infantry(eCoords, 2 , map);
        u.put(unit.getPosition(), unit);
        u.put(enemy.getPosition(), enemy);
        unit.move(eCoords);
        assertEquals(enemy.getMaxHp() - unit.getAttack(), enemy.getHp());
    }

    @ParameterizedTest
    @CsvSource({
           "5, 5, 7 ,4",
            "1, 4, 8, 8",
            "6,7 , 6 ,9"
    })
    void move(int x, int y, int newX, int newY){
        HashMap<String, Unit> u = new HashMap<>();
        HashMap<String, City> b = new HashMap<>();
        EntityManager entityManager = new EntityManager();
        Map map = new Map(10, 10, entityManager);
        Scanner scanner = new Scanner(System.in);
        Input input = new Input(map, entityManager);
        int[] newPos = {newX, newY};
        Unit unit = new Unit(30, 30, new int[]{x, y}, 1 , map);
        String oldPos = unit.getPosition();
        input.doAction("unit;move;" + unit.getPosition() + ";" + newPos[0] + ";" + newPos[1]);
        boolean testGood = false;
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (u.get(newPos[0] + ", " + newPos[1]) != null && u.get(oldPos) == null){
            testGood = true;
        }
        String error = "unit not moved";
        if (u.get(oldPos) != null){
            error = "unit not removed";
        }
        assertEquals(testGood, true, error);
    }

    @ParameterizedTest
    @CsvSource({
        "300, 300",
            "500,300",
            "300,800",
            "320, 320",
            "5, 5"
    })
    void testPeak(int x, int y){
        int[] coords = {x, y};
        EntityManager entityManager = new EntityManager();
        Map map = new Map(10, 10, entityManager);
        Scanner scanner = new Scanner(System.in);
        Input input = new Input(map, entityManager);
        Unit unit = new Infantry(new int[]{5, 5}, 1, map);
        Gui.setMap(map);
        Gui.setEntityManager(entityManager);
        Gui.setInput(input);
        Unit unit1 = Gui.getUnit(new int[]{coords[0], coords[1]});
        boolean testGood = false;
        String message = "Unit not found\n";
        if (unit == unit1 || (coords[0] != 320 && coords[1] != 320)){
            testGood = true;
            message = "Unit found";
        }
        System.out.print(message);
        assertEquals(testGood, true);
    }

    @Test
    void createUnit(){
        EntityManager entityManager = new EntityManager();
        Map map = new Map(10, 10, entityManager);
        City barracks = new Barracks(1, new int[]{5, 5}, map);
        if (barracks instanceof Barracks barracks1){
            barracks1.createUnit("infantry");
        }
        boolean successful = false;
        if (entityManager.getUnits().get("6, 5") != null){
            successful = true;
        }
        assertEquals(successful, true);
    }

}
