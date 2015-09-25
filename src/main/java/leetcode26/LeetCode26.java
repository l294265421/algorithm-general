package leetcode26;

public class LeetCode26 {
	public int removeDuplicates(int[] nums) {
		if (nums == null) {
			return 0;
		}
		
		int len = nums.length;
		if (len < 2) {
			return len;
		}
		
		// 记录数组中已经发现的有效元素最右边的位置
		int right = 0;
		for(int i = 1; i < len; i++) {
			if (nums[i] != nums[right]) {
				nums[right + 1] = nums[i];
				right++;
			}
		}
		return right + 1;
	}
	
	public static void main(String[] args) {
		LeetCode26 leetCode26 = new LeetCode26();
		int[] nums = new int[] {1,1,2};
		System.out.println(leetCode26.removeDuplicates(nums));
	}
}
