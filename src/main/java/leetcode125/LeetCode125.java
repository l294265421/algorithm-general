package leetcode125;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeetCode125 {
    public boolean isPalindrome(String s) {
    	if (s.equals("")) {
			return true;
		}
    	
    	Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(s);
		StringBuilder stemStringBuilder = new StringBuilder();
		boolean isFind = false;
		while (matcher.find()) {
			if (!isFind) {
				isFind = true;
			}
			stemStringBuilder.append(matcher.group());
		}
		if (isFind) {
			String stem = stemStringBuilder.toString().toLowerCase();
			int len = stem.length();
			for(int i = 0; i < len / 2; i++) {
				if (stem.charAt(i) != stem.charAt(len - 1 - i)) {
					return false;
				}
			}
		} 
		return true;
    }
	public static void main(String[] args) {
		LeetCode125 leetCode125 = new LeetCode125();
		String input = "race a car";
		System.out.println(leetCode125.isPalindrome(input));
	}

}
