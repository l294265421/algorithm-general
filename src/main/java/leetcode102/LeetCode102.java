package leetcode102;

import global.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyuncong on 10/22/15.
 */
public class LeetCode102 {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> resultList = new ArrayList<List<Integer>>();
        preorder(root, resultList, 0);
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
