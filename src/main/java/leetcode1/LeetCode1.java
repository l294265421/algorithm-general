package leetcode1;

public class LeetCode1 {

	/**
	 * 在nums中找到两个元素，和为target
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum(int[] nums, int target) {
		// 存储nums中的第几个和第几个元素之和等于target，从1开始
		int[] indexs = new int[2];
		int len = nums.length;
		boolean outLoopBreakFlag = false;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (nums[i] + nums[j] == target) {
					indexs[0] = i + 1;
					indexs[1] = j + 1;
					outLoopBreakFlag = true;
					break;
				}
			}

			if (outLoopBreakFlag) {
				break;
			}
		}

		return indexs;
	}

	public static void main(String[] args) {
		int[] nums = new int[] { 4, 6, 8, 2 };
		int[] indexs = new LeetCode1().twoSum(nums, 8);
		for (int i = 0; i < indexs.length; i++) {
			System.out.println(indexs[i]);
		}
	}

}
