package leetcode64;

public class LeetCode64 {
    private int minNum = Integer.MAX_VALUE;
	private int mMinusOne;
	private int nMinusOne;
	private int[][] grid;
	
    public int minPathSum(int[][] grid) {
        if (grid == null) {
			return 0;
		}
        int rowLen = grid.length;
        if (rowLen == 0) {
			return 0;
		}
        int columnLen = grid[0].length;
        this.mMinusOne = rowLen - 1;
        this.nMinusOne = columnLen - 1;
        this.grid = grid;
        minPathSum(0, 0, 0);
        return this.minNum;
    }
	    
    /**
     * 树的先序遍历，根的坐标决定了它是否有左子树或右子树
     * @param m
     * @param n
     */
    public void minPathSum(int m, int n, int sum) {
    	sum += grid[m][n];
		if (m == this.mMinusOne && n == this.nMinusOne) {
			if (sum < minNum) {
				minNum = sum;
			};
		}
		
		if(m < this.mMinusOne) {
			minPathSum(m + 1, n, sum);
		}
		
		if (n < this.nMinusOne) {
			minPathSum(m, n + 1, sum);
		}
	}
}
