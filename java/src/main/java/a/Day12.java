package a;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 {

    //    public static final boolean PART_B = false;
    public static final boolean PART_B = true;

    public static void main(String[] args) throws Exception {
        // Find all paths between start,end
        // Arrive at a cave
        // Add passages to list of next potential caves on path
        //   Add all big caves
        //   Add small caves not visited before
        //   Add passages not previously taken (avoid loops!)

        // partb : 152756 (too high)
        String s = Files.readString(Path.of("in/12.txt"));
        Map<String, List<String>> caves = parseInput(s);
        List<String> paths = new ArrayList<>();
        findPaths("start", "", paths, caves);
        System.out.println(paths.size());
    }

    /**
     * Find all paths between start and end caves
     */
    public static void findPaths(String cave, String path, List<String> paths, Map<String, List<String>> caves) {
        // Add cave to path
        if (path.isEmpty()) {
            // Don't add leading comma on the first call to findPaths
            path += cave;
        } else {
            path += "," + cave;
        }

        // Is it one I've been to before?
        //   Is it small? (This isn't a valid path, return)
        if (isSmallCave(cave) && beenHereBefore(cave, path)) {
            return;
        }

        // Is it end? (Add to paths, return)
        if (cave.equals("end")) {
            paths.add(path);
        } else {
            // Visit each cave I can see from this one
            // Get list of visible caves
            List<String> visibleCaves = caves.get(cave);
            for (String x : visibleCaves) {
                findPaths(x, path, paths, caves);
            }
        }
    }

    public static boolean isSmallCave(String cave) {
        return Character.isLowerCase(cave.charAt(0));
    }

    public static boolean beenHereBefore(String cave, String path) {
        if (path.isEmpty()) {
            return false;
        }

        Map<String, Integer> visits = new HashMap<>();
        for (String s : path.split(",")) {
            visits.put(s, visits.getOrDefault(s, 0) + 1);
        }

        if (PART_B) {
            List<String> match = visits.keySet().stream()
                .filter(Day12::isSmallCave)
                .filter(x -> visits.get(x) > 1)
                .toList();

            switch (match.size()) {
                case 0:
                    return false;
                case 1:
                    // Can only visit a small cave twice once
                    // Can't visit that cave more than twice
                    String x = match.get(0);
                    return visits.get(x) > 2;
                default:
                    // More than 1 cave visited twice case
                    return true;
            }
        } else {
            return visits.keySet().stream()
                .filter(Day12::isSmallCave)
                .anyMatch(x -> visits.get(x) > 1);
        }
    }

    public static Map<String, List<String>> parseInput(String s) {
        Map<String, List<String>> m = new HashMap<>();

        for (String line : s.split("\n")) {
            String[] split = line.split("-");
            String lhs = split[0];
            String rhs = split[1];

            // Consider x -> a
            // Add x -> a path
            // Add a -> x path
            List<String> x = m.getOrDefault(lhs, new ArrayList<>());
            x.add(rhs);
            m.put(lhs, x);

            if (lhs.equals("start") || rhs.equals("end")) {
                continue;
            } else {
                List<String> y = m.getOrDefault(rhs, new ArrayList<>());
                y.add(lhs);
                m.put(rhs, y);
            }
        }

        return m;
    }
}
