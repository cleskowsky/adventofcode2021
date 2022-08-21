package a;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day5 {
	public static boolean DEBUG = false;

	public static boolean PART_B = true;

	public static void main(String[] args) throws Exception {
		Map<Vent, Integer> overlap = new HashMap<>();

//		List<String> lines = Files.readAllLines(Path.of("day5_sample.txt"));
		List<String> lines = Files.readAllLines(Path.of("day5.txt"));
		for (String l : lines) {
			Pattern p = Pattern.compile("[0-9]+");
			List<Integer> matches = p.matcher(l)
				.results()
				.map(MatchResult::group)
				.map(Integer::parseInt)
				.toList();

			List<Vent> vents = getVents(
				new Vent(matches.get(0), matches.get(1)),
				new Vent(matches.get(2), matches.get(3))
			);

			if (DEBUG) {
				System.out.println(vents);
			}

			for (Vent v : vents) {
				var x = overlap.getOrDefault(v, 0);
				overlap.put(v, x + 1);
			}
		}

		if (DEBUG) {
			System.out.println(overlap);
		}

		var sum = 0;
		for (Vent v : overlap.keySet()) {
			if (overlap.get(v) > 1) {
				if (DEBUG) {
					System.out.println(v);
				}
				sum++;
			}
		}
		System.out.println(sum);
	}

	static record Vent(int x, int y) {
	}

	/**
	 * Returns a list of vents between v1 and v2 (inclusive)
	 *
	 * @param v1 First vent
	 * @param v2 Last vent
	 * @return A list of vents
	 */
	static public List<Vent> getVents(Vent v1, Vent v2) {
		List<Vent> l = new ArrayList<>();
		if (v1.x == v2.x) {
			// Vertical line
			var x = v1.x;
			var min = Math.min(v1.y, v2.y);
			var max = Math.max(v1.y, v2.y);
			for (int i = min; i <= max; i++) {
				l.add(new Vent(x, i));
			}
		} else if (v1.y == v2.y) {
			// Horizontal line
			var y = v1.y;
			var min = Math.min(v1.x, v2.x);
			var max = Math.max(v1.x, v2.x);
			for (int i = min; i <= max; i++) {
				l.add(new Vent(i, y));
			}
		} else if (PART_B) {
			// Diagonal line
			var x = 1;
			if (v2.x < v1.x) {
				x = -1;
			}
			var y = 1;
			if (v2.y < v1.y) {
				y = -1;
			}
			var v = v1;
			l.add(v);
			while (!v.equals(v2)) {
				v = new Vent(v.x + x, v.y + y);
				l.add(v);
			}
		}
		return l;
	}
}
