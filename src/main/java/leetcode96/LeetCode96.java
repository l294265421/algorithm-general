package leetcode96;

public class LeetCode96 {
	/**
	 * 分析：本题其实关键是递推过程的分析，n个点中每个点都可以作为root，
	 * 当 i 作为root时，小于 i  的点都只能放在其左子树中，大于 i 的点
	 * 只能放在右子树中，此时只需求出左、右子树各有多少种，二者相乘即为
	 * 以 i 作为root时BST的总数。
	 * 
	 * Catalan number
	 * @param n
	 * @return
	 */
	public int numTrees(int n) {
        int[] res = new int[n+1];
        res[0] = 1;
        res[1] = 1;//base case
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                res[i] += res[j]*res[i-j-1];//result for i nodes is the sum of multiplications of all left subtrees and all right subtrees
            }
        }
        return res[n];
	}

	public static void main(String[] args) {
		LeetCode96 leetCode96 = new LeetCode96();
		System.out.println(leetCode96.numTrees(3));
	}
}
