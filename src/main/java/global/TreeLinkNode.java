package global;

public class TreeLinkNode {
	public int val;
	public TreeLinkNode left, right, next;

	public TreeLinkNode(int x) {
		val = x;
	}

	@Override
	public String toString() {
		return "TreeLinkNode [val=" + val + ", left=" + left + ", right="
				+ right + ", next=" + next + "]";
	}
	
}
