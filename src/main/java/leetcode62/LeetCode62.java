package leetcode62;

/**
 * 遍历一棵只有一个叶子结点的树，获得到达这个
 * 叶子结点的树的数目
 * @author yuncong
 *
 */
public class LeetCode62 {
	private int pathNum;
	private int mMinusOne;
	private int nMinusOne;
    public int uniquePaths(int m, int n) {
    	this.mMinusOne = m - 1;
    	this.nMinusOne = n - 1;
    	findPathNum(0, 0);
        return this.pathNum;
    }
    
    /**
     * 树的先序遍历，根的坐标决定了它是否有左子树或右子树
     * @param m
     * @param n
     */
    public void findPathNum(int m, int n) {
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
		LeetCode62 leetCode62 = new LeetCode62();
		System.out.println(leetCode62.uniquePaths(1, 3));
	}
}
