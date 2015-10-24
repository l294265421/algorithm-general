package leetcode106;

/**
 * 核心思想：后根遍历树得到的根元素顺序刚好和先根遍历相反；其它的和105题一样
 * @author yuncong
 *
 */
public class LeetCode106 {
	private int rootTh;
	public TreeNode buildTree(int[] inorder, int[] postorder) {
		if (postorder == null) {
			return null;
		}
		int len = postorder.length;
		if (len == 0) {
			return null;
		}
		rootTh = len - 1;
		
		int numZero = postorder[rootTh];
		this.rootTh--;
		TreeNode root = new TreeNode(numZero);
		int rootNumIndexInorder = indexOf(inorder, 0, len - 1, numZero);
		buildTree(postorder, root, inorder, rootNumIndexInorder + 1, len - 1, 1);
		buildTree(postorder, root, inorder, 0, rootNumIndexInorder - 1, 0);
		return root;
	}
	
	public void buildTree(int[] postorder, TreeNode parent, int[] inorder, 
			int left, int right, int leftOrRight) {
		if (left > right) {
			return;
		}
		int rootTempNum = postorder[this.rootTh];
		this.rootTh--;
		TreeNode rootTemp = new TreeNode(rootTempNum);
		if (leftOrRight == 0) {
			parent.left = rootTemp;
		} else {
			parent.right = rootTemp;
		}
		int rootTempNumIndexInorder = indexOf(inorder, left, right, rootTempNum);
		buildTree(postorder, rootTemp, inorder, rootTempNumIndexInorder + 1, right, 1);
		buildTree(postorder, rootTemp, inorder, left, rootTempNumIndexInorder - 1, 0);
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
		int[] inorder = new int[] {1,3,2};
		int[] postorder = new int[] {3,2,1};
		LeetCode106 leetCode105 = new LeetCode106();
		System.out.println(leetCode105.buildTree(inorder, postorder));
	}
}
