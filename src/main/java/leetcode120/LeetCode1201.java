package leetcode120;

import java.util.ArrayList;
import java.util.List;

public class LeetCode1201 {
	/**
	 * 把三角形看成一棵二叉树
	 * @param triangle
	 * @return
	 */
    public int minimumTotal(List<List<Integer>> triangle) {
        return minimumTotal(triangle, 0, 0);
    }
    
    /**
     * 后序遍历二叉树
     * @param triangle
     * @param depth
     * @param offset
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle, 
    		int depth, int offset) {
        if (depth == triangle.size() - 1) {
			return triangle.get(depth).get(offset);
		}
        
        int left = minimumTotal(triangle, depth + 1, offset);
        int right = minimumTotal(triangle, depth + 1, offset + 1);
        return Math.min(left, right) + triangle.get(depth).get(offset);
        
    }
    
    public static void main(String[] args) {
		LeetCode1201 leetCode1201 = new LeetCode1201();
		List<List<Integer>> triangle = new ArrayList<List<Integer>>();
		List<Integer> element1 = new ArrayList<Integer>();
		element1.add(-1);
		List<Integer> element2 = new ArrayList<Integer>();
		element2.add(2);
		element2.add(3);
		List<Integer> element3 = new ArrayList<Integer>();
		element3.add(1);
		element3.add(-1);
		element3.add(-3);
		triangle.add(element1);
		triangle.add(element2);
		triangle.add(element3);
		System.out.println(leetCode1201.minimumTotal(triangle));
	}
}
