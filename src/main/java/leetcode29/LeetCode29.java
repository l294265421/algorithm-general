package leetcode29;

public class LeetCode29 {
	public int divide(int dividend, int divisor) {
		if (divisor == 0) {
			return Integer.MAX_VALUE;
		}
		
		if(dividend == Integer.MIN_VALUE && divisor == -1) {
			return Integer.MAX_VALUE;
		}
		
		int result = 0;
		if (dividend >= 0 && divisor > 0) {
			dividend -= divisor;
			while (dividend >= 0) {
				result++;
				dividend -= divisor;
			}
		} else if (dividend < 0 && divisor < 0) {
			dividend -= divisor;
			while (dividend <= 0) {
				result++;
				dividend -= divisor;
			}
		} else if (dividend >= 0 && divisor < 0) {
			dividend += divisor;
			while (dividend >= 0) {
				result--;
				dividend += divisor;
			}
		} else {
			dividend += divisor;
			while (dividend<= 0) {
				result--;
				dividend += divisor;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		LeetCode29 leetCode29 = new LeetCode29();
		System.out.println(leetCode29.divide(13, -54));
	}
}
