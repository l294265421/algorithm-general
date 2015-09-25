package leetcode24;

public class LeetCode24 {
    public ListNode swapPairs(ListNode head) {
    	if (head == null && head.next == null) {
			return head;
		}
    	
    	ListNode zeroListNode = new ListNode(0);
    	zeroListNode.next = head;
    	ListNode firstListNode = head;
    	ListNode secondListNode = head.next;
    	head = secondListNode;
    	while (true) {
    		// 改变0 1 2指针指向的值
			zeroListNode.next = secondListNode;
			firstListNode.next = secondListNode.next;
			secondListNode.next = firstListNode;
			// 改变指针本身
			zeroListNode = secondListNode;
			if (firstListNode.next != null && 
					firstListNode.next.next != null) {
				firstListNode = firstListNode.next;
				secondListNode = firstListNode.next.next;
			} else {
				break;
			}
			
		}
    	return head;
    }
    
    public static void main(String[] args) {
		LeetCode24 leetCode24 = new LeetCode24();
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(2);
		ListNode listNode3 = new ListNode(3);
		ListNode listNode4 = new ListNode(4);
		listNode1.next = listNode2;
		listNode2.next = listNode3;
		listNode3.next = listNode4;
		leetCode24.swapPairs(listNode1);
		while (listNode1 != null) {
		  System.out.println(listNode1.val);
		  listNode1 = listNode1.next;
		}
	}
}
