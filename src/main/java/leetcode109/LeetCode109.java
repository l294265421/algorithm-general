package leetcode109;

import java.util.ArrayList;
import java.util.List;

import global.ListNode;
import global.TreeNode;

public class LeetCode109 {
    public TreeNode sortedListToBST(ListNode head) {
    	if (head == null) {
			return null;
		}
    	
        List<Integer> numsTemp = new ArrayList<Integer>();
        ListNode cursor = head;
        while (cursor != null) {
			numsTemp.add(cursor.val);
		}
        
        int len = numsTemp.size();
        int[] nums = new int[len];
        for(int i = 0; i < len; i++) {
        	nums[i] = numsTemp.get(i).intValue();
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
}
