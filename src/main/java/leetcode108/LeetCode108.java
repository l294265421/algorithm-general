package leetcode108;

public class LeetCode108 {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null) {
			return null;
		}
        
        int len = nums.length;
        
        if (len == 0) {
			return null;
		}
        
        int mid = (0 + len - 1) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        sortedArrayToBST(nums, 0, mid - 1, root, 0);
        sortedArrayToBST(nums, mid + 1, len - 1, root, 1);
        return root;
    }
    
    public void sortedArrayToBST(int[] nums, int leftBound, 
    		int rightBound, TreeNode parent, int leftOrRight) {
		if (leftBound > rightBound) {
			return;
		}
		
		int mid = (leftBound + rightBound) / 2;
		TreeNode rootNow = new TreeNode(nums[mid]);
		if (leftOrRight == 0) {
			parent.left = rootNow;
		} else {
			parent.right = rootNow;
		}
		sortedArrayToBST(nums, leftBound, mid - 1, rootNow, 0);
		sortedArrayToBST(nums, mid + 1, rightBound, rootNow, 1);
	}
    
    public static void main(String[] args) {
		LeetCode108 leetCode108 = new LeetCode108();
		int[] nums = new int[] {1, 3};
		System.out.println(leetCode108.sortedArrayToBST(nums));
	}
}
