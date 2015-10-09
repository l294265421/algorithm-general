package leetcode83;

public class LeetCode83 {
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

	public static void main(String[] args) {
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(1);
		ListNode listNode3 = new ListNode(2);
		listNode1.next = listNode2;
//		listNode2.next = listNode3;
		LeetCode83 leetCode83 = new LeetCode83();
		leetCode83.deleteDuplicates(listNode1);
		System.out.println(listNode1);
	}
}
