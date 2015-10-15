package leetcode92;

public class LeetCode92 {
	public ListNode reverseBetween(ListNode head, int m, int n) {
		// 1 ≤ m ≤ n ≤ length

		// m等于n，不用做任何事情
		if (m == n) {
			return head;
		}

		// head等于null，不做任何事情
		if (head == null) {
			return head;
		}

		// 寻第m和n个节点的父节点
		ListNode mParent = null;
		ListNode mNode = null;
		ListNode nParent = null;
		ListNode nNode = null;
		if (m == 1) {
			mNode = head;
		}

		ListNode cursorParent = head;
		ListNode cursor = head.next;
		int count = 1;
		while (cursor != null) {
			count++;
			if (count == m) {
				mParent = cursorParent;
				mNode = cursor;

			} else if (count == n) {
				nParent = cursorParent;
				nNode = cursor;
				break;
			}
			cursorParent = cursor;
			cursor = cursor.next;
		}

		// 交换 m n
		if (m == 1) {
			if (m + 1 == n) {
				mNode.next = nNode.next;
				nNode.next = mNode;
				head = nNode;

			} else {
				ListNode temp = nNode.next;

				nNode.next = mNode.next;
				head = nNode;

				nParent.next = mNode;
				mNode.next = temp;
			}
		} else {
			if (m + 1 == n) {
				mParent.next = nNode;
				mNode.next = nNode.next;
				nNode.next = mNode;

			} else {
				ListNode temp = nNode.next;

				mParent.next = nNode;
				nNode.next = mNode.next;

				nParent.next = mNode;
				mNode.next = temp;
			}
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
		leetCode93.reverseBetween(listNode1, 1, 4);
		System.out.println(listNode1);
	}
}
