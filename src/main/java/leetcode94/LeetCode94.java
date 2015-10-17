package leetcode94;

import java.util.ArrayList;
import java.util.List;

public class LeetCode94 {
	public List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> resultList = new ArrayList<Integer>();
		inorderTraversal(root, resultList);
		return resultList;
	}

	public void inorderTraversal(TreeNode root, List<Integer> resultList) {
		if (root != null) {
			inorderTraversal(root.left, resultList);
			resultList.add(root.val);
			inorderTraversal(root.right, resultList);
		}
	}
	
	public static void main(String[] args) {
		LeetCode94 leetCode94 = new LeetCode94();
		TreeNode one = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		one.right = two;
		TreeNode three = new TreeNode(3);
		two.left = three;
		System.out.println(leetCode94.inorderTraversal(one));
	}
}
