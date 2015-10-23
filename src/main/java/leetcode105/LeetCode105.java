package leetcode105;

/**
 * 核心思想：树的中序遍历中，根节点的左子树中所有元素都在根节点的左边，右子树中得所有元素都在根节点的右边；
 * 先序遍历中，根先出现
 * @author yuncong
 *
 */
public class LeetCode105 {
	private int rootTh = 0;
	public TreeNode buildTree(int[] preorder, int[] inorder) {
		if (preorder == null) {
			return null;
		}
		int len = preorder.length;
		if (len == 0) {
			return null;
		}
		int numZero = preorder[0];
		this.rootTh++;
		TreeNode root = new TreeNode(numZero);
		int rootNumIndexInorder = indexOf(inorder, 0, len - 1, numZero);
		buildTree(preorder, root, inorder, 0, rootNumIndexInorder - 1, 0);
		buildTree(preorder, root, inorder, rootNumIndexInorder + 1, len - 1, 1);
		return root;
	}
	
	public void buildTree(int[] preorder, TreeNode parent, int[] inorder, 
			int left, int right, int leftOrRight) {
		if (left > right) {
			return;
		}
		int rootTempNum = preorder[this.rootTh];
		this.rootTh++;
		TreeNode rootTemp = new TreeNode(rootTempNum);
		if (leftOrRight == 0) {
			parent.left = rootTemp;
		} else {
			parent.right = rootTemp;
		}
		int rootTempNumIndexInorder = indexOf(inorder, left, right, rootTempNum);
		buildTree(preorder, rootTemp, inorder, left, rootTempNumIndexInorder - 1, 0);
		buildTree(preorder, rootTemp, inorder, rootTempNumIndexInorder + 1, right, 1);
	}
	
	public int indexOf(int[] arr, int left , int right, int num) {
		for(int i = left; i <= right; i++) {
			if (arr[i] == num) {
				return i;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int[] preorder = new int[] {1,2};
		int[] inorder = new int[] {1,2};
		LeetCode105 leetCode105 = new LeetCode105();
		System.out.println(leetCode105.buildTree(preorder, inorder));
	}
}
