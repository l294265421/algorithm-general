package leetcode92;

public class LeetCode92 {
	public ListNode reverseBetween(ListNode head, int m, int n) {
		// 1 ≤ m ≤ n ≤ length

		// m等于n，不用做任何事情(意味着链表至少包含两个元素)
		if (m == n) {
			return head;
		}

		ListNode mParent = null;
		ListNode mNode = null;
		
		ListNode p = null;
		ListNode q = null;
		ListNode r = null;
		
		ListNode cursor = head;
		int count = 1;
		while (count <= n) {
			if (count < m - 1) {
				cursor = cursor.next;
			}else if (count == m - 1) {
				mParent = cursor;
				cursor = cursor.next;
			} else if (count == m) {
				mNode = cursor;
				
				p = mNode;
				q = p.next;
				r = q.next;
				q.next = p;
				p.next = null;
				
				count +=1;
			} else if (count <= n) {
				p = q;
				q = r;
				r = r.next;
				q.next = p;
			} else {
				break;
			}
			count++;
		}
		// 此时mParent是前一段的尾，mNode是中间的尾，q是中间的头，r是最后一段的头
		if (mParent == null) {
			head = q;
			mNode.next = r;
		} else {
			mParent.next = q;
			mNode.next = r;
		}
		return head;
	}

	public static void main(String[] args) {
		LeetCode92 leetCode93 = new LeetCode92();
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(2);
		ListNode listNode3 = new ListNode(3);
		ListNode listNode4 = new ListNode(4);
		ListNode listNode5 = new ListNode(5);
		listNode1.next = listNode2;
		listNode2.next = listNode3;
		listNode3.next = listNode4;
//		listNode4.next = listNode5;
		System.out.println(leetCode93.reverseBetween(listNode1, 1, 4));
	}
}
