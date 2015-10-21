package leetcode101;

public class LeetCode101 {
    public boolean isSymmetric(TreeNode root) {
    	if (root == null) {
			return true;
		}
    	TreeNode mirror = new TreeNode(-1);
    	copyBinaryTree(root, mirror);
        reversBinaryTree(mirror);
        return isSameTree(root, mirror);
    }
	
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
	
	/**
	 * 翻转二叉树
	 * @param root
	 */
	public void reversBinaryTree(TreeNode root) {
		if (root != null) {
			TreeNode temp = root.left;
			root.left = root.right;
			root.right = temp;
			reversBinaryTree(root.left);
			reversBinaryTree(root.right);
		}
	}
	
	/**
	 * 复制二叉树
	 * @param root1
	 * @param root2
	 */
	public void copyBinaryTree(TreeNode root1, TreeNode root2) {
		if (root1 == null) {
			return;
		}
		root2.val = root1.val;
		
		if (root1.left != null) {
			TreeNode leftNode = new TreeNode(-1);
			root2.left = leftNode;
			copyBinaryTree(root1.left, leftNode);
		}
		
		if (root1.right != null) {
			TreeNode rightNode = new TreeNode(-1);
			root2.right = rightNode;
			copyBinaryTree(root1.right, rightNode);
		}
	}
}
