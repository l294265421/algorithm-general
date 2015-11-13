package leetcode129;

import java.util.ArrayList;
import java.util.List;

import global.TreeNode;

public class LeetCode129 {
    public int sumNumbers(TreeNode root) {
    	List<String> resultList = getStrRefOfInt(root, "");
    	int result = 0;
    	if (resultList.size() != 0) {
			for (String string : resultList) {
				result += Integer.valueOf(string);
			}
		}
    	
    	return result;
    }
    
    public List<String> getStrRefOfInt(TreeNode root, String prefix) {
    	List<String> resultList = new ArrayList<String>();
		if (root != null) {
			String newPrefix = prefix + root.val;
			if (root.left == null && root.right == null) {
				resultList.add(newPrefix);
				return resultList;
			} else {
				List<String> resultListLeft = getStrRefOfInt(root.left, newPrefix);
				List<String> resultListRight = getStrRefOfInt(root.right, newPrefix);
				resultList.addAll(resultListLeft);
				resultList.addAll(resultListRight);
			}
		}
		return resultList;
	}
    
    public static void main(String[] args) {
		LeetCode129 leetCode129 = new LeetCode129();
		TreeNode treeNode1 = new TreeNode(0);
		TreeNode treeNode2 = new TreeNode(1);
		treeNode1.left = treeNode2;
		System.out.println(leetCode129.sumNumbers(treeNode1));
	}
}
