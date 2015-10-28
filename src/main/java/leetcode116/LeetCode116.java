package leetcode116;

import global.TreeLinkNode;

import java.util.ArrayList;
import java.util.List;

public class LeetCode116 {
    public void connect(TreeLinkNode root) {
    	List<List<TreeLinkNode>> resultList = levelOrder(root);
    	for (List<TreeLinkNode> list : resultList) {
			int size = list.size();
			int i = 0;
			for(; i < size - 1; i++) {
				list.get(i).next =list.get(i + 1);
			}
			list.get(i).next = null;
		}
    }
    
    public List<List<TreeLinkNode>> levelOrder(TreeLinkNode root) {
        List<List<TreeLinkNode>> resultList = new ArrayList<List<TreeLinkNode>>();
        preorder(root, resultList, 0);
        return resultList;
    }

    public void preorder(TreeLinkNode root, List<List<TreeLinkNode>> resultList, int depth) {
        if (root != null) {
            int resultListSize = resultList.size();
            if (resultListSize == depth) {
                resultList.add(new ArrayList<TreeLinkNode>());
            }
            resultList.get(depth).add(root);
            preorder(root.left, resultList, depth + 1);
            preorder(root.right, resultList, depth + 1);
        }
    }
    public static void main(String[] args) {
		TreeLinkNode treeLinkNode1 = new TreeLinkNode(1);
		TreeLinkNode treeLinkNode2 = new TreeLinkNode(2);
		TreeLinkNode treeLinkNode3 = new TreeLinkNode(3);
		treeLinkNode1.left = treeLinkNode2;
		treeLinkNode1.right = treeLinkNode3;
		LeetCode116 leetCode116 = new LeetCode116();
		leetCode116.connect(treeLinkNode1);
		System.out.println(treeLinkNode1);
	}
}
