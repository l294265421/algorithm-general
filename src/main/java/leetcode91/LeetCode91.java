package leetcode91;

public class LeetCode91 {
	private int ways;
	public int numDecodings(String s) {
		trial(s, 0);
		return this.ways;
	}
	
	public void trial(String s, int index) {
		if (index == s.length()) {
			// 刚好达到s.length()才刚好解码
			this.ways++;
			return;
		} else if (index > s.length()) {
			return;
		}
		
		if (s.charAt(index) == '1' || s.charAt(index) == '2') {
			// 把index解码为一个字符
			trial(s, index + 1);
			// 把index和index+1一起解释为一个字符
			trial(s, index + 2);
		} else {
			trial(s, index + 1);
		}
	}
	
	public static void main(String[] args) {
		LeetCode91 leetCode91 = new LeetCode91();
		System.out.println(leetCode91.numDecodings("122"));
	}
}
