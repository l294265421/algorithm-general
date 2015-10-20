package leetcode98;

public class LeetCode98 {
	public boolean isValidBST(TreeNode root) {
		if (root == null) {
			return true;
		}
		TreeNode leftNode = root.left;
		boolean left = false;
		if (leftNode == null) {
			left = true;
		} else {
			if (leftNode.val >= root.val) {
				left = false;
			} else {
				left = isValidBST(leftNode);
			}
		}
		
		TreeNode rightNode = root.right;
		boolean right = false;
		if (rightNode == null) {
			right = true;
		} else {
			if (rightNode.val <= root.val) {
				right = false;
			} else {
				right = isValidBST(rightNode);
			}
		}
		
		return left  && right;
	}
	
	public static void main(String[] args) {
		TreeNode node1 = new TreeNode(2);
		TreeNode node2 = new TreeNode(1);
		TreeNode node3 = new TreeNode(3);
		node1.left = node2;
		node1.right = node3;
		LeetCode98 leetCode98 = new LeetCode98();
		System.out.println(leetCode98.isValidBST(node1));
	}
}
