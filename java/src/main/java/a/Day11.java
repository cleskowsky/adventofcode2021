package a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {

    public static boolean PART_B = true;

    public static void main(String[] args) {
        // Puzzle input
        EnergyLevelMap m = Day11.parseInput("""
            2238518614
            4552388553
            2562121143
            2666685337
            7575518784
            3572534871
            8411718283
            7742668385
            1235133231
            2546165345""");

//        m = Day11.parseInput("""
//            5483143223
//            2745854711
//            5264556173
//            6141336146
//            6357385478
//            4167524645
//            2176841721
//            6882881134
//            4846848554
//            5283751526""");

        if (PART_B) {
            int x = 0;
            while (!m.allZeroes()) {
                m.step();
                x++;
            }
            System.out.println(x);
        } else {
            int numberOfFlashes = 0;
            for (int i = 0; i < 100; i++) {
                numberOfFlashes += m.step();
            }
            System.out.println(numberOfFlashes);
        }

    }

    static EnergyLevelMap parseInput(String s) {
        EnergyLevelMap m = new EnergyLevelMap();

        String[] lines = s.split("\n");
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[0].length(); j++) {
                m.put(j, i, Character.getNumericValue(lines[i].charAt(j)));
            }
        }

        return m;
    }
}

class EnergyLevelMap {
    Map<Point, Integer> m = new HashMap<>();
    int rows;
    int cols;

    List<Point> neighbours = List.of(
        new Point(-1, -1),
        new Point(0, -1),
        new Point(1, -1),
        new Point(-1, 0),
        new Point(1, 0),
        new Point(-1, 1),
        new Point(0, 1),
        new Point(1, 1)
    );

    public int get(int x, int y) {
        return m.get(new Point(x, y));
    }

    public void put(int x, int y, int energyLevel) {
        if (x >= cols) {
            cols = x + 1;
        }
        if (y >= rows) {
            rows = y + 1;
        }
        m.put(new Point(x, y), energyLevel);
    }

    public int step() {
        m.forEach((k, v) -> {
            m.put(k, v + 1);
        });

        int sum = 0;

        int x = flash();
        while (x > 0) {
            sum += x;
            x = flash();
        }

        return sum;
    }

    public int flash() {
        int flashCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int v = get(j, i);
                if (v > 9) {
                    flashCount++;
                    m.put(new Point(j, i), 0);
                    for (Point n : neighbours(j, i)) {
                        int nv = m.get(n);
                        if (nv == 0) {
                            // Don't add energy to a flashed level
                            continue;
                        }
                        m.put(n, nv + 1);
                    }
                }
            }
        }
        return flashCount;
    }

    public List<Point> neighbours(int x, int y) {
        return neighbours(new Point(x, y));
    }

    public List<Point> neighbours(Point p) {
        List<Point> ret = new ArrayList<>();

        neighbours.forEach(n -> {
            int x = p.x() - n.x();
            int y = p.y() - n.y();

            boolean xOutOfBounds = x < 0 || x >= cols;
            boolean yOutOfBounds = y < 0 || y >= rows;
            if (xOutOfBounds || yOutOfBounds) {
                return;
            }

            ret.add(new Point(x, y));
        });

        return ret;
    }

    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < rows; i++) {
            String row = "";
            for (int j = 0; j < cols; j++) {
                row += m.get(new Point(j, i));
            }
            row += "\n";
            s += row;
        }

        return s;
    }

    public boolean allZeroes() {
        return m.values().stream().allMatch(x -> {
            return x == 0;
        });
    }
}
