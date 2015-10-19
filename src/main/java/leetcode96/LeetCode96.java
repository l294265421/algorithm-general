package leetcode96;

public class LeetCode96 {
	public int numTrees(int n) {
		// http://blog.csdn.net/jiadebin890724/article/details/23305915
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
