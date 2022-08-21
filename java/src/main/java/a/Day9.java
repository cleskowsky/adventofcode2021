package a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day9 {
	public static List<Point> getLowPoints(HeightMap heightMap) {
		var ret = new ArrayList<Point>();
		for (Point p : heightMap.enumerate()) {
			var val = heightMap.getHeightAt(p);
			if (heightMap.getNeighbourHeights(p).stream().allMatch(x -> val < x)) {
				ret.add(p);
			}
		}
		return ret;
	}

	public static Map<Point, Point> getBasin(HeightMap heightMap, Point lowPoint) {
		Map<Point, Point> basin = new HashMap<>();

		List<Point> seen = new ArrayList<>();
		seen.add(lowPoint);

		while (!seen.isEmpty()) {
			Point p = seen.remove(0);
			if (heightMap.getHeightAt(p) < 9 && basin.get(p) == null) {
				basin.put(p, lowPoint);
				seen.addAll(heightMap.getNeighbours(p));
			}
		}

		return basin;
	}

	public static void main(String[] args) throws IOException {
//		HeightMap heightMap = InputParser.readFromFile("in/9_sample.txt");
		HeightMap heightMap = InputParser.readFromFile("in/9.txt");

		// Part a

		var lowPoints = getLowPoints(heightMap);

		int sum = lowPoints.stream()
			.map(p -> heightMap.getHeightAt(p) + 1)
			.reduce(0, Integer::sum);

		System.out.println(sum);

		// Part b

		System.out.println(lowPoints);

		List<Integer> sizes = new ArrayList<>();

		for (Point lowPoint : lowPoints) {
			var basin = getBasin(heightMap, lowPoint);
			sizes.add(basin.keySet().size());
		}
		Collections.sort(sizes, Collections.reverseOrder());
		System.out.println(sizes.get(0) * sizes.get(1) * sizes.get(2));
	}


}

class HeightMap {
	private Map<Point, Integer> data = new HashMap<>();

	public static List<Point> neighbours = List.of(
		new Point(0, -1), // top
		new Point(1, 0),  // right
		new Point(0, 1),  // bottom
		new Point(-1, 0)  // left
	);

	public void addPoint(int x, int y, int h) {
		data.put(new Point(x, y), h);
	}

	public Integer getHeightAt(Point p) {
		return data.get(p);
	}

	public List<Integer> getNeighbourHeights(Point p) {
		return getNeighbours(p).stream()
			.map(x -> data.get(x))
			.toList();
	}

	public List<Point> getNeighbours(Point p) {
		List<Point> ret = new ArrayList<>();
		for (Point nb : neighbours) {
			var x = new Point(p.x() + nb.x(), p.y() + nb.y());
			if (data.get(x) == null) {
				continue;
			}
			ret.add(x);
		}
		return ret;
	}

	public Set<Point> enumerate() {
		return data.keySet();
	}
}

record Point(int x, int y) {
}

class InputParser {
	public static HeightMap readFromFile(String fileName) throws IOException {
		HeightMap heightMap = new HeightMap();
		var lines = Files.readAllLines(Path.of(fileName));
		for (int y = 0; y < lines.size(); y++) {
			var row = lines.get(y);
			for (int x = 0; x < row.length(); x++) {
				var c = row.charAt(x);
				heightMap.addPoint(x, y, Character.getNumericValue(c));
			}
		}
		return heightMap;
	}
}
