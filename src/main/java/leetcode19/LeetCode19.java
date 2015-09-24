package leetcode19;

public class LeetCode19 {
	/**
	 * 核心思想：
	 * 在单链表中要删除一个节点，只要找到它的前一个节点就行；
	 * 要删除的节点的前一个节点和尾节点的位置相差n
	 * @param head
	 * @param n
	 * @return
	 */
    public ListNode removeNthFromEnd(ListNode head, int n) {
    	if (head == null) {
			return null;
		}
    	
    	// 如果head不等于null，就必须删除一个
        ListNode beforeDeleteNode = null;
        // 刚遍历到的节点
        ListNode now = head;
        int i = 0;
        while (now != null) {
			if (i == n) {
				beforeDeleteNode = head;
			}
			if (i > n) {
				// 保证beforeDeleteNode和有效now的位置相差n
				beforeDeleteNode = beforeDeleteNode.next;
			}
			now = now.next;
			i++;
		}
        // 现在的i值可以表示节点个数
        
        //只有一个节点
        if (i == 1) {
			return null;
		}
        
        // 如果是删除头节点
        if(i == n) {
        	return head.next;
        }
        
        // 如果要删除的是尾节点
        if (n == 1) {
			beforeDeleteNode.next = null;
			return head;
		}
        
        // 正常情况：删除中间
        beforeDeleteNode.next = beforeDeleteNode.next.next;
        return head;
        
    }
    public static void main(String[] args) {
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(2);
		ListNode listNode3 = new ListNode(3);
		ListNode listNode4 = new ListNode(4);
		ListNode listNode5 = new ListNode(5);
		listNode1.next = listNode2;
		listNode2.next = listNode3;
		listNode3.next = listNode4;
		listNode4.next = listNode5;
		
		LeetCode19 leetCode19 = new LeetCode19();
		ListNode newHead = leetCode19.removeNthFromEnd(listNode1, 2);
		while (newHead != null) {
			System.out.println(newHead.val);
			newHead = newHead.next;
		}
	}
}
