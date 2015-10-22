package leetcode103;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyuncong on 10/22/15.
 */
public class LeetCode103 {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> resultList = new ArrayList<List<Integer>>();
        preorder(root, resultList, 0);
        int size = resultList.size();
        for(int i = 0; i < size; i++) {
            if(i % 2 == 1) {
                reverse(resultList.get(i));
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

    public void reverse(List<Integer> result) {
        int size = result.size();
        for(int i = 0; i < size / 2; i++) {
            int temp = result.get(i);
            result.set(i, result.get(size - 1 - i));
            result.set(size - 1 - i, temp);
        }
    }
}
