package leetcode77;

import java.util.ArrayList;
import java.util.List;

public class LeetCode77 {
	private List<List<Integer>> resultList = new ArrayList<List<Integer>>();
	public List<List<Integer>> combine(int n, int k) {
		if (k > n) {
			return resultList;
		}
		
		List<Integer> result = new ArrayList<Integer>();
		for(int i = 0; i < k; i++) {
			result.add(-1);
		}
		for(int i = 1; i <= n - k + 1; i++) {
			result.set(0, i);
			trial(i, n, k - 1, result, 1);
		}
		return this.resultList;
	}
	
	/**
	 * 已知第一个，选择第二个
	 * @param start 第一个元素
	 * @param n
	 * @param k 还有k个元素需要选择
	 * @param result
	 * @param depth
	 */
	public void trial(int start, int n, int k, List<Integer> result, 
			int depth) {
		// 需要的元素个数已经选完
		if (k == 0) {
			List<Integer> temp = new ArrayList<Integer>();
			temp.addAll(result);
			resultList.add(temp);
			return;
			// 还没找齐
		} else {
			for(int i = start + 1; i <= n - k + 1; i++) {
				result.set(depth, i);
				trial(i, n, k - 1, result, depth + 1);
			}
		}
	}
	
	public static void main(String[] args) {
		LeetCode77 leetCode77 = new LeetCode77();
		System.out.println(leetCode77.combine(4, 2));
	}
}
