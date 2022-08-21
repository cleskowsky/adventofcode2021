package a;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day6 {
	public static void main(String[] args) {
		var fish = Input("1,4,1,1,1,1,1,1,1,4,3,1,1,3,5,1,5,3,2,1,1,2,3,1,1,5,3,1,5,1,1,2,1,2,1,1,3,1,5,1,1,1,3,1,1,1,1,1,1,4,5,3,1,1,1,1,1,1,2,1,1,1,1,4,4,4,1,1,1,1,5,1,2,4,1,1,4,1,2,1,1,1,2,1,5,1,1,1,3,4,1,1,1,3,2,1,1,1,4,1,1,1,5,1,1,4,1,1,2,1,4,1,1,1,3,1,1,1,1,1,3,1,3,1,1,2,1,4,1,1,1,1,3,1,1,1,1,1,1,2,1,3,1,1,1,1,4,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,5,1,1,1,2,2,1,1,3,5,1,1,1,1,3,1,3,3,1,1,1,1,3,5,2,1,1,1,1,5,1,1,1,1,1,1,1,2,1,2,1,1,1,2,1,1,1,1,1,2,1,1,1,1,1,5,1,4,3,3,1,3,4,1,1,1,1,1,1,1,1,1,1,4,3,5,1,1,1,1,1,1,1,1,1,1,1,1,1,5,2,1,4,1,1,1,1,1,1,1,1,1,1,1,1,1,5,1,1,1,1,1,1,1,1,2,1,4,4,1,1,1,1,1,1,1,5,1,1,2,5,1,1,4,1,3,1,1");
		System.out.println(fish);
		for (int i = 0; i < 256; i++) {
			fish = tick(fish);
		}
		System.out.println(countFish(fish));
	}

	static Map<Integer, Long> tick(Map<Integer, Long> fish) {
		var ret = new HashMap<Integer, Long>();
		// Age all fish 1 day
		for (int i = 0; i <= 9; i++) {
			ret.put(i - 1, fish.getOrDefault(i, 0l));
		}
		// Add new fish
		ret.put(8, ret.get(-1));
		ret.put(6, ret.get(-1) + ret.get(6));
		ret.remove(-1);
		return ret;
	}

	static long countFish(Map<Integer, Long> fish) {
		return fish.values()
			.stream()
			.reduce(0l, Long::sum);
	}

	static Map<Integer, Long> Input(String s) {
		var ret = new HashMap<Integer, Long>();
		Arrays.stream(s.split(","))
			.map(Integer::parseInt)
			.forEach(x -> {
				var y = ret.getOrDefault(x, 0l);
				ret.put(x, ++y);
			});
		return ret;
	}
}
