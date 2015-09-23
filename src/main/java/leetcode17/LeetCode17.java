package leetcode17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode17 {
	
	public List<String> letterCombinations(String digits) {
		int len = digits.length();
		if (len < 1) {
			return new ArrayList<String>();
		}
		
		String[] phonePanel = new String[] {"", "", "abc", "def", 
				"ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
		
		String firstNumber = phonePanel[Integer.valueOf(digits.substring(0, 1))];
		String[] firstArray = stringToArray(firstNumber);
		if (len == 1) {
			return Arrays.asList(firstArray);
		}
		
		String[] result = firstArray;
 		for(int i = 1; i < len; i++) {
 			String number = digits.substring(i, i + 1);
 			result = cartesian(result, stringToArray(phonePanel[Integer.valueOf(number)]));
 		}
 		return Arrays.asList(result);
	}
	
	// 两个数组做迪卡尔积
	public String[] cartesian(String[] x, String[] y) {
		int xLen = x.length;
		int yLen = y.length;
		String[] result = new String[xLen * yLen];
		for(int i = 0; i < xLen; i++) {
			for(int j = 0; j < yLen; j++) {
				result[i * yLen + j] = x[i] + y[j];
			}
		}
		return result;
	}
	
	public String[] cartesian(String x, String y) {
		String[] xArr = stringToArray(x);
		
		String[] yArr = stringToArray(y);
		
		return cartesian(xArr, yArr);
	}
	
	public String[] stringToArray(String x) {
		int xLen = x.length();
		String[] xArr = new String[xLen];
		for(int i = 0; i < xLen; i++) {
			xArr[i] = x.substring(i, i + 1);
		}
		return xArr;
	}
	
	public static void main(String[] args) {
		LeetCode17 leetCode17 = new LeetCode17();
		System.out.println(leetCode17.letterCombinations("23"));
	}
}
