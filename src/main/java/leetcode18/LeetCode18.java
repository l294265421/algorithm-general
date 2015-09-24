package leetcode18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode18 {
	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> resultList = new ArrayList<List<Integer>>();
		int len = nums.length;
		if (len < 4) {
			return resultList;
		}

		Arrays.sort(nums);

		int i = 0;
		while (i < len - 3) {
			int j = i + 1;
			while (j < len - 2) {
				int nowTarget = target - nums[i] - nums[j];
				int k = j + 1;
				int l = len - 1;
				while (k < l) {
					int sum = nums[k] + nums[l];
					if (sum == nowTarget) {
						List<Integer> result = new ArrayList<Integer>();
						result.add(nums[i]);
						result.add(nums[j]);
						result.add(nums[k]);
						result.add(nums[l]);
						resultList.add(result);

						// 需要不一样的l和k，它们两任何一个和之前的组合
						// 一样，得到的正确组合必然也和之前的组合一样
						while (k < l && nums[k] == nums[k + 1]) {
							k++;
						}
						while (l > k && nums[l] == nums[l - 1]) {
							l--;
						}
						k++;
						l--;
						// 逼近nowTarget
					} else if (sum < nowTarget) {
						k++;
					} else {
						l--;
					}
				}

				while (j < len - 2 && nums[j] == nums[j + 1]) {
					j++;
				}
				j++;
			}
			// 以nums[i]开头的组合，后面出现的nums[i]的后面的元素
			// 是第一个nums[i]的后面
			// 的元素的子集，也就是说，第一个出现的nums[i]与后面元
			// 素的组合包含了后面的nums[i]与之后面的元素的组合
			while (i < len - 3 && nums[i] == nums[i + 1]) {
				i++;
			}
			i++;
		}
		return resultList;
	}
	
	public static void main(String[] args) {
		System.out.println(1);
		LeetCode18 leetCode18 = new LeetCode18();
		int[] nums = new int[]{1, 0, -1, 0, -2, 2};
		System.out.println(leetCode18.fourSum(nums, 0));
	}
}
