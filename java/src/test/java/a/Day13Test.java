package a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {

    @Test
    void foldY() {
        // Given         Expect
        // ..            ..
        // ..            .#
        // -- fold line
        // .#
        // fold line y = 2

        String expected = """
            ..
            .#""";

        Paper p = new Paper();
        p.put(1, 3);
        p = p.fold("y=2");
        assertEquals(expected, p.toString());
    }

    // foldX
    @Test
    void foldX() {
        // Given    Expect
        // ..|.     ..
        // ..|#     .#
        // fold line x = 2

        String expected = """
            ..
            .#""";

        Paper p = new Paper();
        p.put(3, 1);
        p = p.fold("x=2");
        assertEquals(expected, p.toString());
    }

    @Test
    void partA() {
        // Sample
        System.out.println(input("in/day13_simple.txt"));
        // Parta
        System.out.println(input("in/day13.txt"));
    }


    private Paper input(String fname) {
        Paper p = new Paper();

        try {
            for (String s : Files.readAllLines(Path.of(fname))) {
                // Reading dots
                if (s.contains(",")) {
                    String[] split = s.split(",");
                    p.put(
                        Integer.parseInt(split[0]),
                        Integer.parseInt(split[1])
                    );
                }

                // Reading folds
                if (s.contains("=")) {
                    String[] split = s.split("=");
                    if (split[0].contains("y")) {
                        p = p.fold("y=" + split[1]);
                    } else {
                        p = p.fold("x=" + split[1]);
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return p;
    }
}
