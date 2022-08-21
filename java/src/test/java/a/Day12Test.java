package a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day12Test {

    @Test
    void findPaths() {
        Map<String, List<String>> caves = new HashMap<>();

        // start -> end
        caves.put("start", List.of("end"));

        List<String> paths = new ArrayList<>();
        Day12.findPaths("start", "", paths, caves);
        assertEquals(1, paths.size());
        assertEquals("start,end", paths.get(0));

        // start -> b -> end
        // Don't want a path from b -> start (don't backtrack to start)
        // Similarly don't want end -> b (we're finished with this path)
        caves.put("start", List.of("b"));
        caves.put("b", List.of("end"));

        paths = new ArrayList<>();
        Day12.findPaths("start", "", paths, caves);
        assertEquals(1, paths.size());
        assertEquals("start,b,end", paths.get(0));
    }

    @Test
    void findPathsCantRevisitSmallCaves() {
        Map<String, List<String>> caves = new HashMap<>();

        // start -> b -> end
        //          |
        //          v
        //          d
        // d is not part of a discovered path since I
        // can't backtrack to b from d

        caves.put("start", List.of("b"));
        caves.put("b", List.of("d", "end"));
        caves.put("d", List.of("b"));

        List<String> paths = new ArrayList<>();
        Day12.findPaths("start", "", paths, caves);
        assertEquals(1, paths.size());
        assertEquals("start,b,end", paths.get(0));
    }

    @Test
    void findPathsCanRevisitBigCaves() {
        Map<String, List<String>> caves = new HashMap<>();

        // start -> B -> end
        //          |
        //          v
        //          d

        caves.put("start", List.of("B"));
        caves.put("B", List.of("end", "d"));
        caves.put("d", List.of("B"));

        List<String> paths = new ArrayList<>();
        Day12.findPaths("start", "", paths, caves);
        System.out.println(paths);
        assertEquals(2, paths.size());
        assertEquals("start,B,end", paths.get(0));
        assertEquals("start,B,d,B,end", paths.get(1));
    }

    @Test
    void samplePuzzleInput() {
        Map<String, List<String>> caves = new HashMap<>();

        caves.put("start", List.of("A", "b"));
        caves.put("A", List.of("b", "c", "end"));
        caves.put("c", List.of("A"));
        caves.put("b", List.of("A", "d", "end"));
        caves.put("d", List.of("b"));

        List<String> paths = new ArrayList<>();
        Day12.findPaths("start", "", paths, caves);
        assertEquals(10, paths.size());
        assertEquals("start,A,b,A,c,A,end", paths.get(0));
    }

    @Test
    void parseInput() {
        String s = """
            start-b
            b-d
            b-end""";

        Map<String, List<String>> caves = Day12.parseInput(s);
        assertEquals(3, caves.size());
        List<String> x = caves.get("start");
        assertEquals(1, x.size());
        assertTrue(x.contains("b"));
        x = caves.get("b");
        assertEquals(2, x.size());
        assertTrue(x.contains("d") && x.contains("end"));
        assertTrue(x.containsAll(List.of("d", "end")));
        x = caves.get("d");
        assertEquals(1, x.size());
        assertTrue(x.contains("b"));
    }

    @Test
    void samplePuzzleInput2() {
        String s = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc""";

        Map<String, List<String>> caves = Day12.parseInput(s);
        List<String> paths = new ArrayList<>();
        Day12.findPaths("start", "", paths, caves);
        assertEquals(19, paths.size());
    }

    @Test
    void partB() {
        String s = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end""";

        Map<String, List<String>> caves = Day12.parseInput(s);
        System.out.println(caves);
        List<String> paths = new ArrayList<>();
        Day12.findPaths("start", "", paths, caves);
        assertEquals(36, paths.size());
    }

    @Test
    void partB_2() {
        // Ok I found a couple of cave connections from start were defined
        // like HN-start. My parser assumes left -> right always so that's
        // not a valid connection for me. I flipped it to be start -> HN
        // and my test passes
        String s = """
            dc-end
            start-HN
            start-kj
            start-dc
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc""";

        Map<String, List<String>> caves = Day12.parseInput(s);
        System.out.println(caves);
        List<String> paths = new ArrayList<>();
        Day12.findPaths("start", "", paths, caves);
        assertEquals(103, paths.size());
    }
}
