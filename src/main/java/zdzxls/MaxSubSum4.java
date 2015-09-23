package zdzxls;

public class MaxSubSum4 implements MaxSubSum {

	public int maxSubSum(int[] a) {
		// TODO Auto-generated method stub
		int maxSum = 0;
		int thisSum = 0;
		for(int j = 0; j < a.length; j++) {
			thisSum += a[j];
			if(thisSum > maxSum) {
				maxSum = thisSum;
			}
			// 和为0的序列不可能是最大子序列的前缀，删掉
			if(thisSum < 0) {
				thisSum = 0;
			}
		}
		return maxSum;
	}

}
