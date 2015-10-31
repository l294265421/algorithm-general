package leetcode121;

public class LeetCode1212 {
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
		int maxj = prices[len - 1];
		int profit = prices[len - 1] - prices[len - 2];
		if(profit > 0) {
		    maxProfit = profit;
		}
		
		// 某一天买进的最大利润，就是它后面的最大价格减去今天的价格
		for(int i = len - 3; i >= 0; i--) {
			// 通过新加入的元素与之前集合中的最大元素相比较得到新集合中的最大元素
			if (prices[i + 1] > maxj) {
				maxj = prices[i + 1];
			}
			profit = maxj - prices[i];
			if (profit > 0 && profit > maxProfit) {
				maxProfit = profit;
			}
		}
		
		return maxProfit;
	}
	
	public static void main(String[] args) {
		LeetCode1212 leetCode1212 = new LeetCode1212();
		int[] prices = new int[] {1, 2};
		System.out.println(leetCode1212.maxProfit(prices));
	}
}
