package leetcode120;

import java.util.ArrayList;
import java.util.List;

public class LeetCode120 {
	public int minimumTotal(List<List<Integer>> triangle) {
	    int sz = triangle.size();
	    // 把三角形想成一棵树，树中每个节点到叶子节点路径的最小和
	    // 等于该节点加上它的左右节点到叶子节点路径的最小值；
	    // 数组中的每一个元素存储的是该节点左右节点的最小值
	    int[] results = new int[sz+1];

	    // 第一轮结果：数组中保存这最后一行的元素；
	    // 因为最后一个每个节点到叶子节点的路径最小
	    // 和就是该节点的值
	    for(int i=sz-1; i>=0; i--) {
	        List<Integer> tmp = triangle.get(i);

	        for(int j=0; j<tmp.size(); j++) {
	            results[j] = Math.min(results[j], results[j+1]) + tmp.get(j);
	        }
	    }
	    return results[0];
	}
    
    public static void main(String[] args) {
		LeetCode120 leetCode120 = new LeetCode120();
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
		System.out.println(leetCode120.minimumTotal(triangle));
	}
}
