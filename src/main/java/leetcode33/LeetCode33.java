package leetcode33;

public class LeetCode33 {
	public int search(int[] nums, int target) {
		if (nums == null) {
			return -1;
		}
		int len = nums.length;
		int i = 1;
		while (i < len) {
			if (nums[i] < nums[i - 1]) {
				break;
			}
			i++;
		}
		int result1 = binarySearch(nums, 0, i - 1, target);
		int result2 = binarySearch(nums, i, len - 1, target);
		
		if (result1 != -1) {
			return result1;
		}
		if (result2 != -1) {
			return result2;
		}
		return -1;
	}

	public int binarySearch(int[] nums, int left, int right, int target) {
		if (left > right) {
			return -1;
		}

		int mid = (left + right) / 2;
		if (nums[mid] == target) {
			return mid;
		} else if (nums[mid] < target) {
			return binarySearch(nums, mid + 1, right, target);
		} else {
			return binarySearch(nums, left, mid - 1, target);
		}
	}

	public static void main(String[] args) {
		LeetCode33 leetCode33 = new LeetCode33();
		int nums[] = new int[] { 4, 5, 6, 7, 0, 1, 2};
		System.out.println(leetCode33.search(nums, 0));
	}
}
