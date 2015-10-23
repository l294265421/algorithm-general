package leetcode106;

public class LeetCode106 {
	private int rootTh;
	public TreeNode buildTree(int[] preorder, int[] inorder) {
		if (preorder == null) {
			return null;
		}
		int len = preorder.length;
		if (len == 0) {
			return null;
		}
		rootTh = len - 1;
		
		int numZero = preorder[rootTh];
		this.rootTh--;
		TreeNode root = new TreeNode(numZero);
		int rootNumIndexInorder = indexOf(inorder, 0, len - 1, numZero);
		buildTree(preorder, root, inorder, rootNumIndexInorder + 1, len - 1, 1);
		buildTree(preorder, root, inorder, 0, rootNumIndexInorder - 1, 0);
		return root;
	}
	
	public void buildTree(int[] preorder, TreeNode parent, int[] inorder, 
			int left, int right, int leftOrRight) {
		if (left > right) {
			return;
		}
		int rootTempNum = preorder[this.rootTh];
		this.rootTh--;
		TreeNode rootTemp = new TreeNode(rootTempNum);
		if (leftOrRight == 0) {
			parent.left = rootTemp;
		} else {
			parent.right = rootTemp;
		}
		int rootTempNumIndexInorder = indexOf(inorder, left, right, rootTempNum);
		buildTree(preorder, rootTemp, inorder, rootTempNumIndexInorder + 1, right, 1);
		buildTree(preorder, rootTemp, inorder, left, rootTempNumIndexInorder - 1, 0);
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
		int[] preorder = new int[] {1,3,2};
		int[] inorder = new int[] {3,2,1};
		LeetCode106 leetCode105 = new LeetCode106();
		System.out.println(leetCode105.buildTree(preorder, inorder));
	}
}
