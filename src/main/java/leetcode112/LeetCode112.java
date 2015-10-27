package leetcode112;

import global.TreeNode;

public class LeetCode112 {
	public boolean hasPathSum(TreeNode root, int sum) {
		if (root != null) {
			// 叶子节点
			if (root.left == null && root.right == null) {
				if (root.val == sum) {
					return true;
				} else {
					return false;
				}
			} else if (root.left != null && root.right == null) {
				return hasPathSum(root.left, sum - root.val);
			} else if (root.left == null && root.right != null) {
				return hasPathSum(root.right, sum - root.val);
			} else {
				return hasPathSum(root.left, sum - root.val)||
						hasPathSum(root.right, sum - root.val);
			}
		}
		return false;
	}
}
