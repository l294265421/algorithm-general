package linkedlist;

import global.ListNode;

public class FindLoopStart2 {

	public static ListNode findLoopStart(ListNode nodeHead) {

		ListNode slowPointer, fastPointer;
		slowPointer = nodeHead;
		fastPointer = nodeHead;

		// 寻找第一次相遇点
		while (fastPointer != null) {
			fastPointer = fastPointer.next.next;
			slowPointer = slowPointer.next;

			if (fastPointer == slowPointer) {
				break;
			}
		}

		// 把慢指针移动到链表的开头
		slowPointer = nodeHead;

		while (slowPointer != fastPointer) {
			slowPointer = slowPointer.next;
			fastPointer = fastPointer.next;
		}
		return slowPointer;
	}

	public static void main(String[] args) {

		/*
		 * Creating a Linked List of eight nodes having a loop at (node 3)
		 * 
		 * HEAD-->1-->2-->3-->4-->5 ^ | | v 8<--7<--6
		 */

		// creating 8 independent nodes
		ListNode nodeOne = new ListNode(1);
		ListNode nodeTwo = new ListNode(2);
		ListNode nodeThree = new ListNode(3);
		ListNode nodeFour = new ListNode(4);
		ListNode nodeFive = new ListNode(5);
		ListNode nodeSix = new ListNode(6);
		ListNode nodeSeven = new ListNode(7);
		ListNode nodeEight = new ListNode(8);

		// Head node pointing to first node of linked list
		ListNode nodeHead = nodeOne;

		// creating a dependency in nodes by linking node to one another
		nodeOne.next = nodeTwo;
		nodeTwo.next = nodeThree;
		nodeThree.next = nodeFour;
		nodeFour.next = nodeFive;
		nodeFive.next = nodeSix;
		nodeSix.next = nodeSeven;
		nodeSeven.next = nodeEight;
		nodeEight.next = nodeThree; // this line creates the loop

		// calling method to evaluate
		System.out.println(findLoopStart(nodeHead).val);

	}
}
