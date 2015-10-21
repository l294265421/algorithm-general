package leetcode226;

public class LeetCode226 {
    public TreeNode invertTree(TreeNode root) {
    	reversBinaryTree(root);
    	return root;
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
}
