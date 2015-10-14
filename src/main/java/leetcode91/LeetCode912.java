package leetcode91;

public class LeetCode912 {
	public int numDecodings(String s) {

		// Corner cases ruled out
		if (s.startsWith("0") || s.length() < 1)
			return 0;
		else {
			char[] c = s.toCharArray();
			int[] dp = new int[c.length];
			dp[0] = 1;
			for (int i = 1; i < c.length; i++) {

				// Calculate the value of number of current and previous digits
				int sum = c[i] - '0' + (c[i - 1] - '0') * 10;

				if (c[i] == '0')
					// If numbers like 00, 30, 40.... occur, rule out.
					if (sum == 0 || sum >= 30)
						return 0;
					// A 0 must be bounded with its previous digit, so the dp
					// value is equal to previous of previous dp value.
					else
						dp[i] = i > 1 ? dp[i - 2] : 1;

				// Current digit is not 0 and can be combined with previous
				// digit to form a 1~26 number.
				else if (sum < 27 && c[i - 1] != '0')
					dp[i] = dp[i - 1] + (i > 1 ? dp[i - 2] : 1);
				else
					dp[i] = dp[i - 1];
			}
			return dp[dp.length - 1];
		}

	}
	
	public static void main(String[] args) {
		LeetCode912 leetCode912 = new LeetCode912();
		System.out.println(leetCode912.numDecodings("9317949759856497357254398763219839323723136763131916377913495416692666785978758414629119614215967159"));
	}
}
