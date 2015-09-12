package leetcode2;

public class ListNode {
	int val;
	ListNode next;

	public ListNode(int x) {
		val = x;
	}

	@Override
	public String toString() {
		String str = "";
		ListNode temp = this;
		while (temp != null) {
			str += String.valueOf(temp.val);
			temp = temp.next;
		}
		return str;
	}
}
