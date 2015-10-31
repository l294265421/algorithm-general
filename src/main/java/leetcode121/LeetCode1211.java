package leetcode121;

public class LeetCode1211 {
	/**
	 * 搞反了，应该用卖的价格减去买的价格，这里用买的价格减去卖的价格了
	 * @param prices
	 * @return
	 */
	public int maxProfit(int[] prices) {
		int maxProfit = 0;
		if (prices == null) {
			return maxProfit;
		}
		
		int len = prices.length;
		if (len < 2) {
			return 0;
		}
		
		// 初始值
		int minj = prices[len - 1];
		maxProfit = prices[len - 2] - prices[len - 1];
		// 每一天的价格减去它后面天里的最小价格，就是这一天买进的最大利润
		for(int i = len - 3; i >= 0; i--) {
			// 通过新加入的元素与之前集合中的最小元素相比较得到新集合中的最小元素
			if (prices[i + 1] < minj) {
				minj = prices[i + 1];
			}
			int profit = prices[i] - minj;
			if (profit > maxProfit) {
				maxProfit = profit;
			}
		}
		return maxProfit;
	}
}
