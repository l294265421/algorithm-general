package leetcode27;

public class LeetCode27 {
	public int removeElement(int[] nums, int val) {
		if (nums == null) {
			return 0;
		}
		
		int len = nums.length;
		if (len == 0) {
			return 0;
		}
		
		// 记录数组中最右边有效元素的位置
		int right = -1;
		for(int i = 0; i < len; i++) {
			if (nums[i] != val) {
				nums[right + 1] = nums[i];
				right++;
			}
		}
		return right + 1;
	}
}
