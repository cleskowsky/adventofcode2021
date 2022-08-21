package a;

import java.util.List;

import org.junit.jupiter.api.Test;

import a.Day5.Vent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day5Test {

	@org.junit.jupiter.api.Test
	void getVents() {
		List<Vent> l = Day5.getVents(new Vent(0, 9), new Vent(2, 9));
		assertEquals(3, l.size());
		assertTrue(l.contains(new Vent(0, 9)));
		assertTrue(l.contains(new Vent(1, 9)));
		assertTrue(l.contains(new Vent(2, 9)));
	}

	@Test
	void getVentsForDiagonal() {
		List<Vent> l = Day5.getVents(new Vent(9, 7), new Vent(7, 9));
		assertEquals(3, l.size());
		assertTrue(l.contains(new Vent(9, 7)));
		assertTrue(l.contains(new Vent(8, 8)));
		assertTrue(l.contains(new Vent(7, 9)));
	}
}