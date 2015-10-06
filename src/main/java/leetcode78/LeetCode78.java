package leetcode78;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode78 {
	List<List<Integer>> resultList = new ArrayList<List<Integer>>();
	public List<List<Integer>> subsets(int[] nums) {
		if (nums == null) {
			return resultList;
		}
		Arrays.sort(nums);
		int len = nums.length;
		for(int i = 0; i <= len; i++) {
			combine(nums, i);
		}
		return this.resultList;
	}
	
	/**
	 * 获得nums所有包含k个元素的子集
	 * @param nums
	 * @param k
	 * @return
	 */
	public void combine(int[] nums, int k) {
		int len = nums.length;
		
		if (k == 0) {
			this.resultList.add(new ArrayList<Integer>());
			return;
		}
		
		List<Integer> result = new ArrayList<Integer>();
		for(int i = 0; i < k; i++) {
			result.add(-1);
		}
		
		for(int i = 0; i <= len - k; i++) {
			result.set(0, nums[i]);
			trial(nums, i, len, k - 1, result, 1);
		}
	}
	
	/**
	 * 已知第一个，选择第二个
	 * @param start 第一个元素
	 * @param n
	 * @param k 还有k个元素需要选择
	 * @param result
	 * @param depth
	 */
	public void trial(int[] nums, int start, int len, int k, 
			List<Integer> result, int depth) {
		// 需要的元素个数已经选完
		if (k == 0) {
			List<Integer> temp = new ArrayList<Integer>();
			temp.addAll(result);
			resultList.add(temp);
			return;
			// 还没找齐
		} else {
			for(int i = start + 1; i <= len - k; i++) {
				result.set(depth, nums[i]);
				trial(nums, i, len, k - 1, result, depth + 1);
			}
		}
	}
	
	public static void main(String[] args) {
		LeetCode78 leetCode78 = new LeetCode78();
		int[] nums = new int[] {1,2,3};
		System.out.println(leetCode78.subsets(nums));
	}
}
