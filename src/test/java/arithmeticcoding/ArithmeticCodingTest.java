package arithmeticcoding;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ArithmeticCodingTest {
	private ArithmeticCoding arithmeticCoding;
	
	@Before
	public void init() {
		Map<Character, Interval> characterIntervalMap = generateCharacterIntervalMap();
		arithmeticCoding = new ArithmeticCoding("CAEE$", characterIntervalMap);
	}
	
	private Map<Character, Interval> generateCharacterIntervalMap() {
		Map<Character, Interval> characterIntervalMap = new HashMap<Character, Interval>();
		addEntryToCharacterIntervalMap(characterIntervalMap, 'A', 0, 0.2);
		addEntryToCharacterIntervalMap(characterIntervalMap, 'B', 0.2, 0.3);
		addEntryToCharacterIntervalMap(characterIntervalMap, 'C', 0.3, 0.5);
		addEntryToCharacterIntervalMap(characterIntervalMap, 'D', 0.5, 0.55);
		addEntryToCharacterIntervalMap(characterIntervalMap, 'E', 0.55, 0.85);
		addEntryToCharacterIntervalMap(characterIntervalMap, 'F', 0.85, 0.9);
		addEntryToCharacterIntervalMap(characterIntervalMap, '$', 0.9, 1.0);
		return characterIntervalMap;

	}
	
	private void addEntryToCharacterIntervalMap(Map<Character, Interval> characterIntervalMap, char c, double left, double right) {
		Interval interval = new Interval();
		interval.setLeft(new BigDecimal(left));
		interval.setRight(new BigDecimal(right));
		interval.setLeftInclusive(true);
		characterIntervalMap.put(c, interval);
	}

	@Test
	public void testEncode() {
		String encode = arithmeticCoding.encode();
		System.out.println(encode);
		String decode = arithmeticCoding.decode(encode);
		System.out.println(decode);
	}

}
