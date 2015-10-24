package leetcode107;

import java.util.ArrayList;
import java.util.List;

public class LeetCode107 {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
    	List<List<Integer>> resultList = new ArrayList<List<Integer>>();
        preorder(root, resultList, 0);
        int size = resultList.size();
        if (size == 0 || size == 1) {
			return resultList;
		} else {
			for(int i = 0; i < size / 2; i++) {
				List<Integer> temp = resultList.get(i);
				resultList.set(i, resultList.get(size - 1 - i));
				resultList.set(size - 1 - i, temp);
			}
		}
        return resultList;
    }
    
    public void preorder(TreeNode root, List<List<Integer>> resultList, int depth) {
        if (root != null) {
            int resultListSize = resultList.size();
            if (resultListSize == depth) {
                resultList.add(new ArrayList<Integer>());
            }
            resultList.get(depth).add(root.val);
            preorder(root.left, resultList, depth + 1);
            preorder(root.right, resultList, depth + 1);
        }
    }
}
