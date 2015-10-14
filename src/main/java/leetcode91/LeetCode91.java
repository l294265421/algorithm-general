package leetcode91;

public class LeetCode91 {
	private int ways;

	public int numDecodings(String s) {
		if (s.length() == 0) {
			return 0;
		}
		trial(s, 0);
		return this.ways;
	}

	public void trial(String s, int index) {
		int slen = s.length();
		if (index == slen) {
			// 刚好达到s.length()才刚好解码
			this.ways++;
			return;
		}

		// 必须保证编码的第一个数字不为0
		if (s.charAt(index) == '0') {
			System.out.println(s.charAt(index));
			return;
		} else if (s.charAt(index) == '1' || s.charAt(index) == '2') {
			if (slen == index + 1) {
				// 把index解码为一个字符
				trial(s, index + 1);
			} else {
				if (s.charAt(index + 1) == '0') {
					// 把index和index+1一起解释为一个字符
					trial(s, index + 2);
				} else {
					// 把index解码为一个字符
					trial(s, index + 1);
					// 把index和index+1一起解释为一个字符
					int val = Integer.parseInt(
							s.substring(index, index + 2));
					if (val <= 26) {
						trial(s, index + 2);
					}
				}
			}
		} else {
			// 把index解码为一个字符
			trial(s, index + 1);
		}
	}

	public static void main(String[] args) {
		LeetCode91 leetCode91 = new LeetCode91();
		System.out.println(leetCode91.numDecodings("9317949759856497357254398763219839323723136763131916377913495416692666785978758414629119614215967159"));
	}
}
