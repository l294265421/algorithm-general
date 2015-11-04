package leetcode122;
/**
 * 贪心实现：寻找每一段的最大利润；
 * 对于每一笔交易 假设是从i天买入 j天卖出 ，
 * 如果有j+1天的价格高于j天，所以肯定是从i到j+1利润更高，
 * 同样的如果有第i-1天价格比第i天更低，肯定是从i-1天到
 * j天利润更高。所以是不是对于每一段我们只用找到波谷和
 * 波峰，相减就可以了呢？
 * @author liyuncong
 *
 */
public class LeetCode122 {
	public int maxProfit(int[] prices) {
		if (prices.length == 0)
			return 0;
		// Profit for individual transaction.
		int profit = 0;
		int totalProfit = 0;
		int minValue = prices[0];
		for (int i = 0; i < prices.length; i++) {
			// If the profit made is already higher than i should switch to a
			// new minValue.
			if ((prices[i] - minValue) < profit) {
				minValue = prices[i];
				totalProfit += profit;
				profit = 0;
			} else {
				profit = (prices[i] - minValue);
			}
		}
		totalProfit += profit;
		return totalProfit;
	}

}
