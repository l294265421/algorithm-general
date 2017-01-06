package arithmeticcoding;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * 算术编码算法实现。
 * <br/>
 * 算术编码，就是用一个数编码一串字符串。它的思想思想是这样的:给出一个区间,
 * <br/>这个区间被分成n份，n是这串字符串中不同字符的个数，每一份占区间长度的比例
 * <br/>与相应字符出现次数占整个字符串长度的比例，这样，任何一个字符就与区间中的一个子区间
 * <br/>一一对应，最开始的区间为[0, 1),通过字符串的第一个字符得到对应的区间，
 * <br/>通过第二个字符得到第一个区间中的一个子区间，不断迭代下去，直到字符被
 * <br/>用尽，用由最后一个字符得到的区间中的任何一个数字就能代表整个字符串。
 * <br/>
 * 算法的具体描述见《Fundamentals of Multimedia》7.6节。
 * 
 * @author liyuncong
 *
 */
public class ArithmeticCoding {
	private String input;
	private Map<Character, Interval> characterIntervalMap = new HashMap<Character, Interval>();

	/**
	 * 
	 * @param input 需要编码的字符串
	 * @param characterIntervalMap 字符的初始区间
	 * @throws NullPointerException 当input为null时
	 * @throws IllegalArgumentException 当input长度为0时
	 */
	public ArithmeticCoding(String input, Map<Character, Interval> characterIntervalMap) {
		if (input == null) {
			 throw new NullPointerException("input is null");
		}
		if (input.length() == 0) {
			throw new IllegalArgumentException("input'lenght is 0");
		}
		this.input = input;
		this.characterIntervalMap = Objects.requireNonNull(characterIntervalMap, "characterIntervalMap is null");
	}
	
	/**
	 * 这个方法对input作统计得到字符区间
	 * @param input 需要编码的字符串
	 * @throws NullPointerException 当input为null时
	 * @throws IllegalArgumentException 当input长度为0时
	 */
	public ArithmeticCoding(String input) {
		if (input == null) {
			 throw new NullPointerException("input is null");
		}
		if (input.length() == 0) {
			throw new IllegalArgumentException("input'lenght is 0");
		}
		this.input = input;
		
		computeCharacterIntervalMap();
	}
	
	private void computeCharacterIntervalMap() {
		Map<Character, Integer> charCount = count();
		
		// 用double而不是BigDecimal，是为了防止相除得到无限小数
		double length = input.length();
		BigDecimal left = new BigDecimal(0);
		for(Entry<Character, Integer> entry : charCount.entrySet()) {
			Character c = entry.getKey();
			Integer count = entry.getValue();
			
			Interval interval = new Interval();
			interval.setLeft(left);
			interval.setRight(left.add(BigDecimal.valueOf(count / length)));
			interval.setLeftInclusive(true);
			interval.setRightInclusive(false);
			
			left = interval.getRight();
			
			characterIntervalMap.put(c, interval);
		}
	}
	
	/**
	 * 统计每个字符出现的次数
	 * @return
	 */
	private Map<Character, Integer> count() {
		Map<Character, Integer> charCount = new HashMap<>();
		for(int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			Integer count = charCount.get(c);
			if (count == null) {
				charCount.put(c, 1);
			} else {
				charCount.put(c, count + 1);
			}
		}
		return charCount;
	}
	
	public String encode() {
		BigDecimal low = new BigDecimal(0);
		BigDecimal high = new BigDecimal(1);
		BigDecimal range = new BigDecimal(1);
		for(int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			Interval interval = characterIntervalMap.get(c);
			high = low.add(interval.getRight().multiply(range));
			low = low.add(interval.getLeft().multiply(range));
			range = high.subtract(low);
		}
		
		Interval resultInterval = new Interval();
		resultInterval.setLeft(low);
		resultInterval.setLeftInclusive(true);
		resultInterval.setRight(high);
		return generateCodeword(resultInterval);
	}
	
	private String generateCodeword(Interval interval) {
		StringBuilder codeword = new StringBuilder("0.");
		while (binaryStringToBigDecimal(codeword.toString()).compareTo(interval.getLeft()) < 0) {
			codeword.append('1');
			if (binaryStringToBigDecimal(codeword.toString()).compareTo(interval.getRight()) > 0) {
				codeword.setCharAt(codeword.length() - 1, '0');
			}
		}
		return codeword.toString();
	}
	
	/**
	 * 
	 * @param binaryString 二进制小数(如 0.1001)的字符串形式，只支持大于0小于1的数
	 * @return
	 */
	private BigDecimal binaryStringToBigDecimal(String binaryString) {
		int dotIndex = binaryString.indexOf('.');
		String decimalPart = binaryString.substring(dotIndex + 1);
		BigDecimal result = new BigDecimal("0");
		if ("".equals(decimalPart)) {
			return result;
		}
		for(int i = 0; i < decimalPart.length(); i++) {
			BigDecimal temp = new BigDecimal(decimalPart.substring(i, i + 1)).multiply(new BigDecimal(1).divide(new BigDecimal(2).pow(i + 1)));
			result = result.add(temp);
		}
		return result;
	}
	
	public static void main(String[] args) {
		BigInteger bigInteger = new BigInteger("123");
		System.out.println(bigInteger.toString(10));
		BigDecimal bigDecimal = new BigDecimal(bigInteger, 3);
		System.out.println(bigDecimal);
		System.out.println(new BigDecimal(1).divide(new BigDecimal(2).pow(0 + 1)));
		System.out.println(new BigDecimal("0").add(new BigDecimal(0.5)));
	}
}
