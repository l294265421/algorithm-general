package leetcode86;

public class LeetCode86 {
	public ListNode partition(ListNode head, int x) {
		ListNode smallListNodeHead = null;
		ListNode smallListNodeTail = null;

		ListNode preCursor = null;
		ListNode cursor = head;

		while (cursor != null) {
			ListNode temp = cursor.next;
			if (cursor.val < x) {
				// 添加到small里
				if (smallListNodeHead == null) {
					smallListNodeHead = cursor;
					smallListNodeTail = cursor;
				} else {
					smallListNodeTail.next = cursor;
					smallListNodeTail = smallListNodeTail.next;
				}
				
				// 从big里删除
				if (cursor == head) {
					head = head.next;
				} else {
					preCursor.next = cursor.next;
				}
			} else {
				preCursor = cursor;
			}
			cursor = temp;
		}
		if (smallListNodeTail != null) {
			smallListNodeTail.next = head;
		} else {
			return head;
		}
		return smallListNodeHead;
	}
	
	public static void main(String[] args) {
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(4);
		ListNode listNode3 = new ListNode(3);
		ListNode listNode4 = new ListNode(2);
		ListNode listNode5 = new ListNode(5);
		ListNode listNode6 = new ListNode(2);
		listNode1.next = listNode2;
		listNode2.next = listNode3;
		listNode3.next = listNode4;
		listNode4.next = listNode5;
		listNode5.next = listNode6;
		LeetCode86 leetCode86 = new LeetCode86();
		ListNode result = leetCode86.partition(listNode1, 3);
		System.out.println(result);
	}
}
