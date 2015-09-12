package leetcode3;

public class LeetCode3 {
	/**
	 * 找到最大无重复字符子序列；
	 * 这里采用的方式是：找出字符串中以每 个字符开始的最大无重复字符
	 * 子序列，从而找到最终的最大无重复字符子序列
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring(String s) {
		String longest = "";
		int sLength = s.length();
		for(int i = 0; i < sLength; i++) {
			StringBuffer thisLongest = new StringBuffer();
			for(int j = i; j < sLength; j++) {
				char temp = s.charAt(j);
				if (thisLongest.indexOf(String.valueOf(temp)) == -1) {
					thisLongest.append(temp);
				} else {
					break;
				}
			}
			if (longest.length() < thisLongest.toString().length()) {
				longest = thisLongest.toString();
			}
		}
		
		return longest.length();
	}
	
	public static void main(String[] args) {
		LeetCode3 leetCode3 = new LeetCode3();
		System.out.println(leetCode3.lengthOfLongestSubstring("bbbbb>d"));
	}
}
