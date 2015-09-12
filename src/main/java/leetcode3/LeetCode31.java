package leetcode3;

public class LeetCode31 {
	/**
	 * 找到最大无重复字符子序列；
	 * 这里采用的方式是：找出字符串中以每 个字符结束的最大无重复字符
	 * 子序列，从而找到最终的最大无重复字符子序列；
	 * 这种方式可以利用以前一个字符结尾的最大无重复字符子序列的结果，
	 * 因为它提供了一个边界，并且提供了一个无重复的字符序列，如果
	 * 加上现在的没重复，就直接加上，如果有重复，就在上一个子序列中
	 * 找到现在这个字符，并从后面断开，连续到现在这个字符，得到以现在这个
	 * 字符结尾的最大子序列
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring(String s) {
		String longest = "";
		StringBuffer lastLongest = new StringBuffer();
		int sLen = s.length();
		for(int i = 0; i < sLen; i++) {
			char temp = s.charAt(i);
			if (lastLongest.indexOf(String.valueOf(temp)) == -1) {
				lastLongest.append(temp);
			} else {
				lastLongest = new StringBuffer(lastLongest.substring(lastLongest.lastIndexOf(String.valueOf(temp)) + 1));
				lastLongest.append(temp);
			}
			if (lastLongest.length() > longest.length()) {
				longest = lastLongest.toString();
			}
		}
		return longest.length();
	}
	
	public static void main(String[] args) {
		LeetCode31 leetCode31 = new LeetCode31();
		System.out.println(leetCode31.lengthOfLongestSubstring("abcabcbb"));
	}
}
