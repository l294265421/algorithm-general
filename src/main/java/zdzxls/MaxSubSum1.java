package zdzxls;

public class MaxSubSum1 implements MaxSubSum {

	public int maxSubSum(int[] a) {
		// TODO Auto-generated method stub
		int maxSum = 0;
		String sub = "";
		for(int i = 0; i < a.length; i++) {
			for(int j = i; j < a.length; j++) {
				int thisSum = 0;
				String thisSub = "";
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
