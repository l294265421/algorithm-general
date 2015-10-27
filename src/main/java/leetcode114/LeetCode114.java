package leetcode114;

import global.TreeNode;

public class LeetCode114 {
	private TreeNode headPre;
	private TreeNode cursor;
	public void flatten(TreeNode root) {
		if (root == null || (root.left == null && root.right == null)) {
			return;
		}
		TreeNode rootTemp = new TreeNode(-1);
		this.headPre = rootTemp;
		this.cursor = rootTemp;
		
		flattenMe(root);
		root.left = null;
		root.right = this.headPre.right.right;
	}

	public void flattenMe(TreeNode root) {
		if (root != null) {
			this.cursor.right = new TreeNode(root.val);
			this.cursor = cursor.right;
			flattenMe(root.left);
			flattenMe(root.right);
		}
	}

	public static void main(String[] args) {
		TreeNode rootTemp = new TreeNode(1);
		TreeNode rootTemp1 = new TreeNode(2);
		TreeNode rootTemp2 = new TreeNode(3);
		rootTemp.left = rootTemp1;
		rootTemp.right = rootTemp2;
		LeetCode114 leetCode114 = new LeetCode114();
		leetCode114.flatten(rootTemp);
		System.out.println(rootTemp);
	}
}
