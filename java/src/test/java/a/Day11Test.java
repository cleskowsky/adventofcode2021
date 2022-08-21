package a;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {

    @Test
    void parseInput() {
        String s = """
            00
            19""";
        EnergyLevelMap m = Day11.parseInput(s);

        assertEquals(2, m.rows);
        assertEquals(2, m.cols);
        assertEquals(0, m.get(0, 0));
        assertEquals(0, m.get(1, 0));
        assertEquals(1, m.get(0, 1));
        assertEquals(9, m.get(1, 1));
    }

    @Test
    void step() {
        String s = """
            00
            19""";
        EnergyLevelMap m = Day11.parseInput(s);

        m.step();

        assertEquals(2, m.get(0, 0));
        assertEquals(2, m.get(1, 0));
        assertEquals(3, m.get(0, 1));
        assertEquals(0, m.get(1, 1));
    }

    @Test
    void flash() {
        String s = "09";
        EnergyLevelMap m = Day11.parseInput(s);
        m.step();
        m.flash();

        String t = "20";
        EnergyLevelMap n = Day11.parseInput(t);
        assertEquals(m.toString(), n.toString());

        s = """
            11111
            19991
            19191
            19991
            11111""";
        m = Day11.parseInput(s);
        m.step();

        t = """
            34543
            40004
            50005
            40004
            34543""";
        n = Day11.parseInput(t);
        assertEquals(n.toString(), m.toString());
    }

    @Test
    void neighbours() {
        String s = "0";
        EnergyLevelMap m = Day11.parseInput(s);
        assertTrue(m.neighbours(0, 0).isEmpty());

        s = "00";
        m = Day11.parseInput(s);
        assertEquals(1, m.neighbours(0, 0).size());
        assertTrue(m.neighbours(0, 0).contains(new Point(1, 0)));

        s = """
            00
            00""";
        m = Day11.parseInput(s);
        assertEquals(3, m.neighbours(1, 1).size());
        assertTrue(m.neighbours(1, 1).containsAll(List.of(
            new Point(0, 0),
            new Point(1, 0),
            new Point(0, 1)
        )));
    }

    @Test
    void flashCounter() {
        EnergyLevelMap m = Day11.parseInput("0");
        int x = m.flash();
        assertEquals(0, x);

        m.put(0, 0, 10);
        x = m.flash();
        assertEquals(1, x);
    }

    @Test
    void stepFlashCount() {
        int x = 0;

        EnergyLevelMap m = Day11.parseInput("""
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526""");

        for (int i = 0; i < 100; i++) {
            x += m.step();
        }

        assertEquals(1656, x);
    }

    @Test
    void allZeroes() {
        EnergyLevelMap m = Day11.parseInput("0");
        assertTrue(m.allZeroes());

        m.step();
        assertFalse(m.allZeroes());
    }
}
