package leetcode2;

/**
 * 不能处理大数
 * @author yuncong
 *
 */
public class LeetCode2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	ListNode answer = null;
    	int sum = getNumber(l1) + getNumber(l2);
    	answer = getListNode(sum);
    	return answer;
    }

    public int getNumber(ListNode l) {
		int number = 0;
		int i = 0;
		while (l != null) {
			number += l.val * Math.pow(10, i);
			l = l.next;
			i++;
		}
		
		return number;
	}
    
    public ListNode getListNode(int number) {
		ListNode l = null;
		
		String numberStr = String.valueOf(number);
		int len = numberStr.length();
		for(int i = 0; i < len; i++) {
			int val = Integer.valueOf(numberStr.substring(i, i + 1));
			ListNode temp = new ListNode(val);
			temp.next = l;
			l = temp;
		}
		
		return l;
	}
    
    public static void main(String[] args) {
    	ListNode l1 = new ListNode(1);
    	ListNode l2 = new ListNode(9);
    	ListNode l3 = new ListNode(9);
    	ListNode l4 = new ListNode(9);
    	ListNode l5 = new ListNode(9);
    	ListNode l6 = new ListNode(9);
    	ListNode l7 = new ListNode(9);
    	ListNode l8 = new ListNode(9);
    	ListNode l9 = new ListNode(9);
    	ListNode l0 = new ListNode(9);
    	l1.next = l2;
    	l2.next = l3;
    	l3.next = l4;
    	l4.next = l5;
    	l5.next = l6;
    	l6.next = l7;
    	l7.next = l8;
    	l8.next = l9;
    	l9.next = l0;
    	ListNode l21 = new ListNode(3);
    	LeetCode2 leetCode2 = new LeetCode2();
    	System.out.println(leetCode2.addTwoNumbers(l1, l21));
    	
	}
}
