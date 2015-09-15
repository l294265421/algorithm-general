package leetcode9;

public class LeetCode9 {
    public boolean isPalindrome(int x) {
    	// 负数没有回文数
        if (x < 0) {
			return false;
		}
        
        // 大于等于10的数，如果倒转过来与原来的数不等，也不是回文数
        if (x >= 10) {
			int palindromeCandidate = reverse(x);
			if (palindromeCandidate != x) {
				return false;
			}
		}
        
        return true;
    }
    
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
		LeetCode9 leetCode9 = new LeetCode9();
		System.out.println(leetCode9.isPalindrome(101));
	}
}
