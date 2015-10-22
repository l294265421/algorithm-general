package leetcode95;

import java.util.ArrayList;
import java.util.List;

public class LeetCode95 {
	
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> resultList = new ArrayList<TreeNode>();
        if (n == 0) {
        	// 奇怪
        	resultList.add(null);
			return resultList;
		}
        
        resultList = generateTrees(1, n);
       
       return resultList;
    }
    
    /**
     * 以某一个节点作为根的所有可能二叉搜索树是以该节点为根，从所有
     * 可能左子树中选一棵，再从所有可能右子树中选一棵，组成的所有
     * 可能的二叉树
     * @param leftBound
     * @param rightBound
     * @return
     */
    public List<TreeNode> generateTrees(int leftBound, int rightBound) {
    	if (leftBound > rightBound) {
			return null;
		}
    	
    	List<TreeNode> resultList = new ArrayList<TreeNode>();
    	
    	for(int i = leftBound; i <= rightBound; i++) {
    		
    		List<TreeNode> leftList = generateTrees(leftBound, i - 1);
    		List<TreeNode> rightList = generateTrees(i + 1, rightBound);
    		if (leftList == null && rightList == null) {
    			TreeNode now = new TreeNode(i);
    			resultList.add(now);
			} else if (leftList != null && rightList == null) {
				for (TreeNode treeNode : leftList) {
					TreeNode now = new TreeNode(i);
					now.left = treeNode;
					TreeNode rootTemp = new TreeNode(-1);
					copyBinaryTree(now, rootTemp);
					resultList.add(rootTemp);
				}
			} else if (leftList == null && rightList != null) {
				for (TreeNode treeNode : rightList) {
					TreeNode now = new TreeNode(i);
					now.right = treeNode;
					TreeNode rootTemp = new TreeNode(-1);
					copyBinaryTree(now, rootTemp);
					resultList.add(rootTemp);
				}
			} else {
				for (TreeNode leftTreeNode : leftList) {
					TreeNode now = new TreeNode(i);
					now.left = leftTreeNode;
					for (TreeNode rightTreeNode : rightList) {
						now.right = rightTreeNode;
						TreeNode rootTemp = new TreeNode(-1);
						copyBinaryTree(now, rootTemp);
						resultList.add(rootTemp);
					}
				}
			}
    	}
    	return resultList;
	}
	
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
	
	public static void main(String[] args) {
		LeetCode95 leetCode95 = new LeetCode95();
		List<TreeNode> resultList = leetCode95.generateTrees(3);
		for (TreeNode treeNode : resultList) {
			System.out.println(treeNode);
		}
		System.out.println(resultList.size());
	}
}
