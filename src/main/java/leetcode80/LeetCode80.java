package leetcode80;

public class LeetCode80 {
	public int removeDuplicates(int[] nums) {
		if (nums == null) {
			return 0;
		}
		int len = nums.length;
		if (len == 0) {
			return 0;
		}
		int validIndexRight = 0;
		int count = 1;
		int now = nums[0];
		for (int i = 1; i < len; i++) {
			if (nums[i] == now) {
				if (count < 2) {
					// validIndexRight小于等于i,也就是说，在
					// validIndexRight和i之间存在无效位置
					validIndexRight++;
					nums[validIndexRight] = nums[i];
					count++;
				} else {
					count++;
				}
			} else {
				validIndexRight++;
				nums[validIndexRight] = nums[i];
				count = 1;
				now = nums[i];
			}
		}
		return validIndexRight + 1;
	}

	public static void main(String[] args) {
		LeetCode80 leetCode80 = new LeetCode80();
		int[] nums = new int[] { 1, 1, 1, 2, 2, 3 };
		System.out.println(leetCode80.removeDuplicates(nums));
	}
}
