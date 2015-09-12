package leetcode2;

public class LeetCode21 {
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	// 引用l1中某个元素
    	ListNode now = l1;
    	// now的上一个元素
    	ListNode preListNode = null;
    	
    	// 进位值
    	int extraPlus = 0;
    	// 只是修改l1中的值，这里只针对l1和l2中共有的位
    	while (now != null && l2 != null) {
			int val = now.val + l2.val + extraPlus;
			// 判断是否需要进位
			if (val > 9) {
				extraPlus = 1;
				val = val % 10;
			} else {
				extraPlus = 0;
			}
			now.val = val;
			preListNode = now;
			now = now.next;
			l2 = l2.next;
		}
    	
    	if (now == null && l2 == null) {
			if (extraPlus == 1) {
				preListNode.next = new ListNode(1); 
			}
		}
    	
    	if (l2 != null) {
    		now = l2;
    		preListNode.next = now;
		}
    	
    	// 针对剩下的部分作处理，这剩下的部分既可能来自l1，也可能来自l2
    	while (now != null) {
			if (extraPlus == 1) {
				int val = now.val + 1;
				// 判断是否需要进位
				if (val > 9) {
					extraPlus = 1;
					val = val % 10;
				} else {
					extraPlus = 0;
				}
				now.val = val;
				preListNode = now;
				now = now.next;
				
			} else {
				break;
			}
		}
    	
    	if (now == null) {
			if (extraPlus == 1) {
				preListNode.next = new ListNode(1);
			}
		}
    	
    	return l1;
    }

	public static void main(String[] args) {
    	ListNode l1 = new ListNode(3);
    	ListNode l2 = new ListNode(7);
    	ListNode l3 = new ListNode(9);
    	ListNode l4 = new ListNode(9);
    	ListNode l5 = new ListNode(9);
    	ListNode l6 = new ListNode(9);
    	ListNode l7 = new ListNode(9);
    	ListNode l8 = new ListNode(9);
    	ListNode l9 = new ListNode(9);
    	ListNode l0 = new ListNode(9);
    	l1.next = l2;
//    	l2.next = l3;
//    	l3.next = l4;
//    	l4.next = l5;
//    	l5.next = l6;
//    	l6.next = l7;
//    	l7.next = l8;
//    	l8.next = l9;
//    	l9.next = l0;
    	ListNode l21 = new ListNode(9);
    	ListNode l22 = new ListNode(2);
    	l21.next = l22;
    	LeetCode21 leetCode21 = new LeetCode21();
    	System.out.println(leetCode21.addTwoNumbers(l1, l21));
    	System.out.println(l1.val);
	}
}
