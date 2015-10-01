package leetcode61;

public class LeetCode61 {
    public ListNode rotateRight(ListNode head, int k) {
    	if (head == null) {
			return head;
		}
    	
    	ListNode cursor = head;
        int len = 1;
        while (cursor.next != null) {
        	len++;
			cursor = cursor.next;
		}
        ListNode tail = cursor;
        
        int moveLen = k % len;
        // 不用移动
        if (moveLen == 0) {
			return head;
		}
        
        int sperateIndex = len - moveLen;
        
        ListNode newTail = head;
        for(int i = 1; i < sperateIndex; i++) {
        	newTail = newTail.next;
        }
        
        ListNode newHead = newTail.next;
        tail.next = head;
        newTail.next = null;
        return newHead;
    }
    
    public static void main(String[] args) {
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(2);
		ListNode listNode3 = new ListNode(3);
		ListNode listNode4 = new ListNode(4);
		ListNode listNode5 = new ListNode(5);
//		listNode1.next = listNode2;
//		listNode2.next = listNode3;
//		listNode3.next = listNode4;
//		listNode4.next = listNode5;
		LeetCode61 leetCode61 = new LeetCode61();
		System.out.println(leetCode61.rotateRight(listNode1, 0));
		
	}
}
