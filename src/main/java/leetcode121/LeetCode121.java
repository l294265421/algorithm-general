package leetcode121;

public class LeetCode121 {
	public int maxProfit(int[] prices) {
		int maxProfit = 0;
		if (prices == null) {
			return maxProfit;
		}
		
		int len = prices.length;
		for(int i = 0; i < len - 1; i++) {
			for(int j = i + 1; j < len; j++) {
				int profit = prices[i] - prices[j];
				if (profit > maxProfit) {
					maxProfit = profit;
				}
			}
		}
		return maxProfit;
	}
}
