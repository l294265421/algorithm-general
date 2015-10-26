package leetcode111;

import global.TreeNode;

public class LeetCode111 {
	public int minDepth(TreeNode root) {
		if (root != null) {
			if (root.left == null && root.right == null) {
				return 1;
			} else if (root.left != null && root.right == null) {
				int leftDepth = minDepth(root.left);
				// int rightDepth = 0;
				return leftDepth + 1;
			} else if (root.left == null && root.right != null) {
				// int leftDepth = 0;
				int rightDepth = minDepth(root.right);
				return rightDepth + 1;
			} else {
				int leftDepth = minDepth(root.left);
				int rightDepth = minDepth(root.right);
				return Math.min(leftDepth, rightDepth) + 1;
			}
		}
		return 0;
	}
}
