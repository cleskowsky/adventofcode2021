package a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 {
    public static void main(String[] args) {
        System.out.println(13);
        System.out.println("Hello, world");
        System.out.println(1);
    }

}

class Paper {
    private Map<Point, String> children = new HashMap<>();
    private int maxX = 0;
    private int maxY = 0;

    public void put(int x, int y) {
        children.put(new Point(x, y), "#");
        if (x >= maxX) {
            maxX = x + 1;
        }
        if (y >= maxY) {
            maxY = y + 1;
        }
    }

    @Override
    public String toString() {
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < maxY; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < maxX; j++) {
                line.append(children.getOrDefault(new Point(j, i), "."));
            }
            rows.add(line.toString());
        }
        rows.add("Number of dots: " + children.keySet().size());
        return String.join("\n", rows);
    }

    /**
     * Returns new sheet of paper folded along the axis specified by instr
     */
    public Paper fold(String instr) {
        Paper folded = new Paper();

        String[] split = instr.split("=");

        boolean foldingUp = split[0].contains("y");
        int foldLine = Integer.parseInt(split[1]);

        children.forEach((k, v) -> {
            int x = k.x();
            int y = k.y();
            if (foldingUp) {
                if (k.y() > foldLine) {
                    int dy = k.y() - foldLine;
                    y = foldLine - dy;
                }
            } else {
                if (k.x() > foldLine) {
                    int dx = k.x() - foldLine;
                    x = foldLine - dx;
                }
            }
            folded.put(x, y);
        });

        return folded;
    }
}
