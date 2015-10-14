package leetcode91;

import java.util.HashMap;

public class LeetCode913 {
	HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	int count;
	public int numDecodings(String s) {
		if (s.length() == 0)
			return 0;
		return decode(s, 0);
	}

	public int decode(String s, int i) {
		// 递归停止条件
		if (i == s.length())
			return 1;
		if (i == s.length() - 1)
			return s.charAt(i) == '0' ? 0 : 1;
		if (map.containsKey(i)) {
			this.count++;
			System.out.println(count);
			return map.get(i);
		}
		if (s.charAt(i) == '0')
			return 0;
		
		int res = decode(s, i + 1);
		int val = Integer.parseInt(s.substring(i, i + 2));
		if (val > 0 && val <= 26) {
			res += decode(s, i + 2);
		}
		map.put(i, res);
		return res;
	}

	public static void main(String[] args) {
		LeetCode913 leetCode913 = new LeetCode913();
		System.out
				.println(leetCode913
						.numDecodings("9317949759856497357254398763219839323723136763131916377913495416692666785978758414629119614215967159"));
	}
}
