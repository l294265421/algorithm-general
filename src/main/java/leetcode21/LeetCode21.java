package leetcode21;

public class LeetCode21 {
	/**
	 * 合并两个有序链表
	 * @param l1
	 * @param l2
	 * @return
	 */
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		if (l1 == null && l2 != null) {
			return l2;
		}
		
		if (l1 != null && l2 == null) {
			return l1;
		}
		
		ListNode resultHead = new ListNode(0);
		// 指向结果链表的最后一个元素
		ListNode pointer = resultHead;
		while (l1 != null && l2 != null) {
			if (l1.val < l2.val) {
				pointer.next = l1;
				pointer = pointer.next;
				l1 = l1.next;
			} else {
				pointer.next = l2;
				pointer = pointer.next;
				l2 = l2.next;
			}
		}
		
		// 有且只有一个为null
		if (l1 == null) {
			pointer.next = l2;
		} else {
			pointer.next = l1;
		}
		
		return resultHead.next;
	}
	
	public static void main(String[] args) {
		ListNode listNode1 = new ListNode(0);
		ListNode listNode2 = new ListNode(2);
		listNode1.next = listNode2;
		
		ListNode listNode3 = new ListNode(1);
		ListNode listNode4 = new ListNode(3);
		listNode3.next = listNode4;
		
		LeetCode21 leetCode21 = new LeetCode21();
		ListNode resultNode = leetCode21.mergeTwoLists(listNode1, listNode3);
		while (resultNode != null) {
			System.out.println(resultNode.val);
			resultNode = resultNode.next;
		}
	}
}
