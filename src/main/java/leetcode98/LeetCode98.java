package leetcode98;

import java.util.ArrayList;
import java.util.List;

public class LeetCode98 {
	public boolean isValidBST(TreeNode root) {
		if (root == null) {
			return true;
		}
		
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		inorder(root, treeNodeList);
		int size = treeNodeList.size();
		for(int i = 1; i < size; i++) {
			if (treeNodeList.get(i - 1).val >= treeNodeList.get(i).val) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获得二叉搜索树的线性表示，即有序链表
	 * @param root
	 * @param treeNodeList
	 */
	public void inorder(TreeNode root, List<TreeNode> treeNodeList) {
		if (root != null) {
			inorder(root.left, treeNodeList);
			treeNodeList.add(root);
			inorder(root.right, treeNodeList);
		}
	}
	
	public static void main(String[] args) {
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		TreeNode node3 = new TreeNode(3);
		node1.left = node2;
		node1.right = node3;
		LeetCode98 leetCode98 = new LeetCode98();
		System.out.println(leetCode98.isValidBST(node1));
	}
}
