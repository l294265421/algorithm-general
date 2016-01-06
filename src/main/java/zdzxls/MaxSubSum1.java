package zdzxls;

public class MaxSubSum1 implements MaxSubSum {

	public int maxSubSum(int[] a) {
		int maxSum = 0;
		String sub = "";
		// 起点
		for(int i = 0; i < a.length; i++) {
			// 终点
			for(int j = i; j < a.length; j++) {
				int thisSum = 0;
				String thisSub = "";
				// k就是游标
				for(int k = i; k <= j; k++) {
					thisSum += a[k];
					thisSub += a[k] + " ";
				}
				if(thisSum > maxSum) {
					maxSum = thisSum;
					sub = thisSub;
				}
					
			}
		}
		System.out.println(sub);
		return maxSum;
	}

}
