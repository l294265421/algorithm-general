package leetcode110;

import global.TreeNode;

public class LeetCode110 {
	private boolean isBalanced = true;
	public boolean isBalanced(TreeNode root) {
		treeDepth(root);
		return this.isBalanced;
	}
	
	public int treeDepth(TreeNode root) {
		if (this.isBalanced == true && root != null) {
			if (root.left == null && root.right == null) {
				return 1;
			} else if (root.left != null && root.right == null) {
				int leftDepth = treeDepth(root.left);
				// int rightDepth = 0;
				if (Math.abs(leftDepth) > 1) {
					this.isBalanced = false;
				}
				return leftDepth + 1; 
			} else if (root.left == null && root.right != null) {
				// int leftDepth = 0;
				int rightDepth = treeDepth(root.right);
				if (Math.abs(rightDepth) > 1) {
					this.isBalanced = false;
				}
				return rightDepth + 1; 
			} else {
				int leftDepth = treeDepth(root.left);
				int rightDepth = treeDepth(root.right);
				if (Math.abs(leftDepth - rightDepth) > 1) {
					this.isBalanced = false;
				}
				return Math.max(leftDepth, rightDepth) + 1;
			}
		}
		return 0;
	}
}
