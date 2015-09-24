package leetcode21;

public class LeetCode21 {
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		if (l1 == null && l2 != null) {
			return l2;
		}
		
		if (l1 != null && l2 == null) {
			return l1;
		}
		
		ListNode resultHead = null;
		ListNode other = null;
		if (l1.val < l2.val) {
			resultHead = l1;
			other = l2;
		} else {
			resultHead = l2;
			other = l1;
		}
		ListNode pointer = resultHead;
		wh
		
	}
}
