package a;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {

	@Test
	void validateChunk() {
		assertTrue(Day10.validateChunk("()").valid());
		assertTrue(Day10.validateChunk("[]").valid());
		assertTrue(Day10.validateChunk("{}").valid());
		assertTrue(Day10.validateChunk("<>").valid());
		assertFalse(Day10.validateChunk("(]").valid());
		assertFalse(Day10.validateChunk("(]]").valid());
	}
}