package a;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {


	@Test
	void fuelNeededToAlign() {
		List<Integer> crabs = Day7.Input("16,1,2,0,4,2,7,1,2,14");
		Map<Integer, Integer> tt = Map.of(
			1, 41,
			2, 37,
			3, 39,
			10, 71
		);
		tt.forEach((x, y) -> assertEquals(y, Day7.fuelNeeded(x, crabs)));
	}

	@Test
	void fuelNeededB() {
		List<Integer> crabs = Day7.Input("16,1,2,0,4,2,7,1,2,14");
		Map<Integer, Integer> tt = Map.of(
			5, 168,
			2, 206
		);
		tt.forEach((x, y) -> assertEquals(y, Day7.fuelNeededB(x, crabs)));
	}
}