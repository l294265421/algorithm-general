package leetcode14;

public class LeetCode14 {
	public String longestCommonPrefix(String[] strs) {
		int len = strs.length;
		if (len == 0) {
			return "";
		}
		
		// 最长公共前缀不可能比数组中任何一个元素长
		String prefix = strs[0];
		for(int i = 1; i < len; i++) {
			String temp = strs[i];
			while (prefix.length() > 0) {
				if (temp.startsWith(prefix)) {
					break;
				} else {
					prefix = prefix.substring(0, prefix.length() - 1);
				}
			}
		}
		return prefix;
	}
	
	public static void main(String[] args) {
		LeetCode14 leetCode14 = new LeetCode14();
		String[] test = new String[] {"ddd", "adc", "adf"};
		System.out.println(leetCode14.longestCommonPrefix(test));
	}
}
