package a;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {

	@Test
	void tick() {
		Map<Integer, Long> x = Day6.Input("3,4,3,1,2");
		for (int i = 0; i < 80; i++) {
			x = Day6.tick(x);
		}
		assertEquals(5934, Day6.countFish(x));
	}
}