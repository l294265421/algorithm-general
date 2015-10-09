package leetcode82;

public class LeetCode82 {
    public ListNode deleteDuplicates(ListNode head) {
		if (head == null) {
			return head;
		}

		ListNode startNode = head;
		ListNode endNode = startNode;
		ListNode cursor = head.next;
		while (cursor != null) {
			if (startNode.val == cursor.val) {
				endNode = cursor;
			} else {
				if (startNode != endNode) {
					startNode.next = endNode.next;
				}
				startNode = cursor;
				endNode = startNode;
			}
			cursor = cursor.next;
		}
		if (startNode != endNode) {
			startNode.next = endNode.next;
		}
		return head;
    }
}
