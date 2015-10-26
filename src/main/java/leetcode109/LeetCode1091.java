package leetcode109;

import global.ListNode;
import global.TreeNode;

public class LeetCode1091 {
	public TreeNode sortedListToBST(ListNode head) {
	    if(head==null){
	        return null;
	    }else if(head.next==null){
	        return new TreeNode(head.val);
	    }
	    // 上面的条件语句保证链表中至少有两个元素
	    ListNode prev=null,cur=head,runner=head;
	    /**
	     * 寻找中间节点；
	     * 思想：用两个指针，慢指针（cur）一次前进一步，快指针（runner）
	     * 一次前进两步；
	     * 
	     */
	    while(runner!=null && runner.next!=null){
	        prev=cur;
	        cur=cur.next;
	        runner=runner.next.next;
	    }
	    TreeNode root=new TreeNode(cur.val);
	    if(prev!=null){// 这个条件语句为何能改善速度？运行到这里的prev不是不可能为null吗？
	        prev.next=null;
	        root.left=sortedListToBST(head);
	    }
	    root.right=sortedListToBST(cur.next);
	    return root;
	}
}
