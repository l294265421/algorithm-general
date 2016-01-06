package zdzxls;

public class MaxSubSum2 implements MaxSubSum {

	public int maxSubSum(int[] a) {
		int maxSum = 0;
		// 起点
		for (int i = 0; i < a.length; i++) {
			int thisSum = 0;
			// 终点
			for (int j = i; j < a.length; j++) {
				thisSum += a[j];
				if (thisSum > maxSum) {
					maxSum = thisSum;
				}

			}
		}
		return maxSum;
	}

}
