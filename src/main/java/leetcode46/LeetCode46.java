package leetcode46;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode46 {
	public List<List<Integer>> permute(int[] nums) {
		if (nums == null) {
			return null;
		}

		int len = nums.length;
		if (len == 0) {
			return null;
		}
		Arrays.sort(nums);
		int[] end = new int[len];
		for (int i = 0; i < len; i++) {
			end[i] = nums[i];
		}
		reverse(end);

		List<List<Integer>> resultList = new ArrayList<List<Integer>>();
		
		while (true) {
			resultList.add(Arrays.asList(intArrToIntegerArr(nums)));
			if (isEqualIntArray(nums, end)) {
				break;
			}
			
			nextPermutation(nums);
		}
		return resultList;
	}

	/**
	 * 核心思想：从右到左，找到下一步需要变化的元素的位置，将它后面的比它大 的最小元素（这个元素一定存在）和它互换位置，并对后面的元素进行升序排列；
	 * 如果原数组本身就降序排列，就反转使之升序排列
	 * 
	 * @param nums
	 */
	public void nextPermutation(int[] nums) {
		if (nums == null) {
			return;
		}

		int len = nums.length;
		if (len == 1) {
			return;
		}

		// 从后往前，出现的第一个比后面元素小的数的位置
		int firstSmallIndex = len - 2;
		while (firstSmallIndex >= 0) {
			if (nums[firstSmallIndex] < nums[firstSmallIndex + 1]) {
				break;
			} else {
				firstSmallIndex--;
			}
		}
		// 等于-1，说明数组是降序排列的
		if (firstSmallIndex == -1) {
			// 反转数组
			reverse(nums);
		} else {
			// 从firstSmallIndex后面的元素中找到比firstSmallIndex处元素大的最小的数
			int target = -1;
			for (int i = firstSmallIndex + 1; i < len; i++) {
				if (nums[i] > nums[firstSmallIndex]) {
					if (target == -1) {
						target = i;
					} else {
						if (nums[i] < nums[target]) {
							target = i;
						}
					}
				}
			}
			// 交换firstSmallIndex和找到的数的位置上的元素，然后对firstSmallIndex
			// 位置之后的元素升序排列
			int temp = nums[target];
			nums[target] = nums[firstSmallIndex];
			nums[firstSmallIndex] = temp;
			Arrays.sort(nums, firstSmallIndex + 1, len);
		}
	}

	public void reverse(int[] nums) {
		int len = nums.length;
		int limit = len / 2;
		for (int i = 0; i < limit; i++) {
			int temp = nums[i];
			nums[i] = nums[len - 1 - i];
			nums[len - 1 - i] = temp;
		}
	}

	public Integer[] intArrToIntegerArr(int[] nums) {
		int len = nums.length;
		Integer[] temp = new Integer[len];
		for (int i = 0; i < len; i++) {
			temp[i] = nums[i];
		}
		return temp;
	}

	public boolean isEqualIntArray(int[] nums1, int[] nums2) {
		int len = nums1.length;
		for (int i = 0; i < len; i++) {
			if (nums1[i] != nums2[i]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		LeetCode46 leetCode46 = new LeetCode46();
		int[] nums = new int[] { 0, 1 };
		int[] nums2 = new int[] { 1, 2, 3 };
		System.out.println(leetCode46.permute(nums));
	}
}
