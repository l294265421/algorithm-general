package leetcode7;

public class LeetCode71 {
	public int reverse(int x) {
		//最小负数的补码是10000...，在int范围内没有
		// 与之匹配的相反数；它的实际相反数超出了int
		// 的范围，所以算作overflow
		if (x == Integer.MIN_VALUE) {
			return 0;
		}
		
		int sign = 1;
		if (x < 0) {
			sign = -1;
			x *= -1;
		}
		
		String resultStr = "";
		while (x >= 10) {
			int remainder = x % 10;
			resultStr += remainder;
			x = x / 10;
		}
		resultStr += x;
		
		int result = 0;
		try {
			result = Integer.valueOf(resultStr) * sign;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		LeetCode71 leetCode7 = new LeetCode71();
		long t = 9646324351l;
		System.out.println(leetCode7.reverse(-2147483648));
		System.out.println(Integer.MIN_VALUE);
	}
}
