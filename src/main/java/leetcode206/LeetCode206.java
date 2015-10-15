package leetcode206;

public class LeetCode206 {
	public ListNode reverseList(ListNode head) {
		// 当链表中有两个及以上元素时才有必要反转
		if (head == null || head.next == null) {
			return head;
		}
		ListNode p = head;
		ListNode q = head.next;
		p.next = null;
		ListNode r = q.next;
		q.next = p;
		
		
		return head;
	}
}
