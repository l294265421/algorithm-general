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
		
		while (r != null) {
			p = q;
			q = r;
			r = r.next;
			q.next = p;
		}
		
		return q;
	}
	
	public static void main(String[] args) {
		LeetCode206 leetCode206 = new LeetCode206();
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(2);
		ListNode listNode3 = new ListNode(3);
		ListNode listNode4 = new ListNode(4);
		ListNode listNode5 = new ListNode(5);
		listNode1.next = listNode2;
		listNode2.next = listNode3;
		listNode3.next = listNode4;
		listNode4.next = listNode5;
		System.out.println(leetCode206.reverseList(listNode1));
	}
}
