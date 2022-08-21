package a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10 {

	public static final boolean DEBUG = false;
	public static final boolean PART_B = false;

	public static void main(String[] args) {
//		var data = Input("in/10_sample.txt");
		var data = Input("in/10.txt");

		if (PART_B) {
			List<Long> scores = data.stream()
				.map(Day10::validateChunk)
				.filter(x -> x.firstInvalidClose == null)
				.map(x -> x.missingCloses)
				.map(Day10::scoreIncomplete)
				.collect(Collectors.toList());
			Collections.sort(scores);
			System.out.println(scores);
			System.out.println(scores.get(scores.size() / 2));

			// 192498059 is too low
			// I mishandled scores which could wrap around in the integer data type :/

		} else {
			var pts = data.stream()
				.map(Day10::validateChunk)
				.map(x -> x.firstInvalidClose)
				.map(Day10::scoreBadChar)
				.reduce(0, Integer::sum);
			System.out.println(pts);
		}
	}

	public static Map<Character, Character> matchingPairs = Map.of(
		')', '(',
		']', '[',
		'}', '{',
		'>', '<',

		'(', ')',
		'[', ']',
		'{', '}',
		'<', '>'
	);

	/**
	 * Returns chunk score when consider first invalid char
	 */
	public static int scoreBadChar(Character firstInvalidChar) {
		if (firstInvalidChar == null) {
			return 0;
		}

		return switch (firstInvalidChar) {
			case ')' -> 3;
			case ']' -> 57;
			case '}' -> 1197;
			case '>' -> 25137;
			default -> 0;
		};
	}

	/**
	 * Returns chunk score when considering incomplete chunks
	 */
	public static long scoreIncomplete(String danglingOpens) {
		if (danglingOpens == null) {
			return 0;
		}

		long sum = 0;
		for (Character c : danglingOpens.toCharArray()) {
			sum = sum * 5 + switch (c) {
				case ')' -> 1;
				case ']' -> 2;
				case '}' -> 3;
				case '>' -> 4;
				default -> 0;
			};
		}
		return sum;
	}

	/**
	 * Returns true if all chunks have matching open, close delimiters
	 */
	public static ValidationStatus validateChunk(String chunk) {
		if (DEBUG) {
			System.out.println("Validating chunk: " + chunk);
		}

		Stack<Character> opens = new Stack<>();
		boolean isValid = true;
		Character firstInvalidClose = null;
		String missingCloses = null;

		for (Character c : chunk.toCharArray()) {
			if ("([{<".indexOf(c) >= 0) {
				// Chunk opener found
				opens.add(c);
				if (DEBUG) {
					System.out.println(opens);
				}
			} else {
				// Chunk closer
				if (opens.isEmpty()) {
					// Found a closer without a matching opener eg "())"
					isValid = false;
					firstInvalidClose = c;
					break;
				}
				Character expectedOpenerForCloser = matchingPairs.get(c);
				if (opens.pop() != expectedOpenerForCloser) {
					// Mismatched opener, closer eg "(}"
					isValid = false;
					firstInvalidClose = c;
					break;
				}
			}
		}

		if (opens.size() > 0) {
			isValid = false;

			if (PART_B) {
				missingCloses = "";
				while (!opens.isEmpty()) {
					missingCloses += matchingPairs.get(opens.pop()).toString();
				}
			}
		}

		if (DEBUG) {
			System.out.println("Result: " + isValid);
		}
		return new ValidationStatus(isValid, firstInvalidClose, missingCloses);
	}

	record ValidationStatus(boolean valid, Character firstInvalidClose, String missingCloses) {
	}

	private static List<String> Input(String fileName) {
		List<String> ret = new ArrayList<>();
		try {
			ret = Files.readAllLines(Path.of(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
