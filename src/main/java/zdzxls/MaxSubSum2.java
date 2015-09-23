package zdzxls;

public class MaxSubSum2 implements MaxSubSum {

	public int maxSubSum(int[] a) {
		// TODO Auto-generated method stub
		int maxSum = 0;
		for (int i = 0; i < a.length; i++) {
			int thisSum = 0;
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
