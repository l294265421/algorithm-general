package leetcode82;

public class LeetCode82 {
    public ListNode deleteDuplicates(ListNode head) {
		if (head == null) {
			return head;
		}

		ListNode startNode = head;
		ListNode endNode = startNode;
		ListNode cursor = head.next;
		ListNode foreStartNode = new ListNode(0);
		foreStartNode.next = startNode;
		while (cursor != null) {
			if (startNode.val == cursor.val) {
				endNode = cursor;
			} else {
				if (startNode != endNode) {
					if (foreStartNode.next == head) {
						head = endNode.next;
					}
					foreStartNode.next = endNode.next;
				} else {
					foreStartNode = endNode;
				}
				
				startNode = cursor;
				endNode = startNode;
			}
			cursor = cursor.next;
		}
		if (startNode != endNode) {
			if (foreStartNode.next == head) {
				head = endNode.next;
			}
			foreStartNode.next = endNode.next;
		}
		return head;
    }
    
    public static void main(String[] args) {
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(1);
		ListNode listNode3 = new ListNode(2);
		listNode1.next = listNode2;
		listNode2.next = listNode3;
		LeetCode82 leetCode82 = new LeetCode82();
		System.out.println(leetCode82.deleteDuplicates(listNode1));
	}
}
