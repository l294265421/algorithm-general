package leetcode100;

public class LeetCode100 {
	public boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) {
			return true;
		} else if (p != null && q != null) {
			if (p.val == q.val) {
				boolean left = isSameTree(p.left, q.left);
				boolean right = isSameTree(p.right, q.right);
				return left && right;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
	}
}
