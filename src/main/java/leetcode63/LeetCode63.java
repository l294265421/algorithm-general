package leetcode63;

/**
 * 遍历一棵只有一个叶子结点的树，获得到达这个
 * 叶子结点的树的数目
 * @author yuncong
 *
 */
public class LeetCode63 {
	private int pathNum;
	private int mMinusOne;
	private int nMinusOne;
	private int[][] obstacleGrid;
	
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null) {
			return 0;
		}
        int rowLen = obstacleGrid.length;
        if (rowLen == 0) {
			return 0;
		}
        int columnLen = obstacleGrid[0].length;
        this.mMinusOne = rowLen - 1;
        this.nMinusOne = columnLen - 1;
        this.obstacleGrid = obstacleGrid;
        findPathNum(0, 0);
        return this.pathNum;
    }
    
    /**
     * 树的先序遍历，根的坐标决定了它是否有左子树或右子树
     * @param m
     * @param n
     */
    public void findPathNum(int m, int n) {
    	if (obstacleGrid[m][n] == 1) {
			return;
		}
		if (m == this.mMinusOne && n == this.nMinusOne) {
			this.pathNum++;
		}
		
		if(m < this.mMinusOne) {
			findPathNum(m + 1, n);
		}
		
		if (n < this.nMinusOne) {
			findPathNum(m, n + 1);
		}
	}
    
    public static void main(String[] args) {
		LeetCode63 leetCode63 = new LeetCode63();
		int[][] obstacleGrid = new int[3][3];
		int rowNum = 0;
		for(int[] row : obstacleGrid) {
			int column = 0;
			for(int num : row) {
				obstacleGrid[rowNum][column] = 0;
				column++;
			}
			rowNum++;
		}
		obstacleGrid[1][1] = 1;
		System.out.println(leetCode63.uniquePathsWithObstacles(obstacleGrid));
	}
}
