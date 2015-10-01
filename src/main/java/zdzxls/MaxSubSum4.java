package zdzxls;

public class MaxSubSum4 implements MaxSubSum {

	/**
	 * 任意最大子序列必定以a中某个数字结束；一个候选最大子序列
	 * 结尾数字的前缀序列必须为正
	 */
	public int maxSubSum(int[] a) {
		int maxSum = 0;
		int thisSum = 0;
		for(int j = 0; j < a.length; j++) {
			thisSum += a[j];
			if(thisSum > maxSum) {
				maxSum = thisSum;
			}
			// 和小于0的序列不可能是最大子序列的前缀，删掉
			if(thisSum < 0) {
				thisSum = 0;
			}
		}
		return maxSum;
	}

}
