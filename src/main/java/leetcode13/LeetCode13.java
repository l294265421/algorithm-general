package leetcode13;

public class LeetCode13 {
	private String romanNumberLetters = "IVXLCDM";
    public int romanToInt(String s) {
        int num = 0;
        int len = s.length();
        int start = len - 1;
        while (start >= 0) {
			int end = nextNumberStr(s, start);
			String romanNumber = s.substring(end, start + 1);
			num += getNumber(romanNumber);
			start = end - 1;
		}
        return num;
    }
    
    public int nextNumberStr(String s, int start) {
		int end = start - 1;
		for(; end >= 0; end--) {
			char first = s.charAt(end);
			char second = s.charAt(start);
			if (!firstSmallerThanSecond(first, second)) {
				break;
			}
		}
		end++;
		return end;
	}
    
    public int getNumber(String romanNumber) {
    	int num = 0;
		int len = romanNumber.length();
		if (len == 1) {
			if (romanNumber.equals("I")) {
				num = 1;
			}else if (romanNumber.equals("V")) {
				num = 5;
			}
			else if (romanNumber.equals("X")) {
				num = 10;
			}else if (romanNumber.equals("L")) {
				num = 50;
			}else if (romanNumber.equals("C")) {
				num = 100;
			}else if (romanNumber.equals("D")) {
				num = 500;
			}else if (romanNumber.equals("M")) {
				num = 1000;
			}
		} else {
			if (romanNumber.equals("IV")) {
				num = 4;
			}
			else if (romanNumber.equals("IX")) {
				num = 9;
			}else if (romanNumber.equals("XL")) {
				num = 40;
			}else if (romanNumber.equals("XC")) {
				num = 90;
			}else if (romanNumber.equals("CD")) {
				num = 400;
			}else if (romanNumber.equals("CM")) {
				num = 900;
			}
		}
		return num;
	}
    
    public boolean firstSmallerThanSecond(char first, char second) {
		if (romanNumberLetters.indexOf(first) < 
				romanNumberLetters.indexOf(second)) {
			return true;
		}
		return false;
		
	}
    
    public static void main(String[] args) {
		LeetCode13 leetCode13 = new LeetCode13();
		System.out.println(leetCode13.romanToInt("MMCCCXCIX"));
	}
}
