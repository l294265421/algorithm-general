package leetcode104;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyuncong on 10/22/15.
 */
public class LeetCode104 {
    public int maxDepth(TreeNode root) {
        List<Integer> resultAssistant = new ArrayList<Integer>();
        preOrder(root, resultAssistant, 0);
        return resultAssistant.size();
    }
    public void preOrder(TreeNode root, List<Integer> resultAssistant, int depth) {
        if(root != null) {
            if(resultAssistant.size() == depth) {
                resultAssistant.add(-1);
            }
            preOrder(root.left, resultAssistant, depth + 1);
            preOrder(root.right, resultAssistant, depth + 1);
        }
    }
}
