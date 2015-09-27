package leetcode34;

public class LeetCode34 {
	public int[] searchRange(int[] nums, int target) {
		int[] result = new int[] {-1, -1};
		if(nums == null) {
			return result;
		}
		
		int len = nums.length;
		if (len == 0) {
			return result;
		}
		
		int searchIndex = binarySearch(nums, target, 0, len - 1);
		if (searchIndex == -1) {
			return result;
		} else {
			// 左边界
			int j = searchIndex;
			while (j - 1 >= 0 && nums[j - 1] == target) {
				j--;
			}
			// 右边界
			int k = searchIndex;
			while (k + 1 < len && nums[k + 1] == target) {
				k++;
			}
			result[0] = j;
			result[1] = k;
			return result;
		}
		
	}
	
	/**
	 * 二分查找动态生成一颗二叉查找树；只有某一个子树上有节点，就去查找；
	 * 当left>right，子树中没有节点了，停止查找
	 * @param nums
	 * @param target
	 * @param left
	 * @param right
	 * @return
	 */
	public int binarySearch(int[] nums, int target, int left, int right) {
		if (left > right) {
			return -1;
		}
		
		int mid = (left + right) / 2;
		if (nums[mid] == target) {
			return mid;
		} else if (nums[mid] < target) {
			return binarySearch(nums, target, mid + 1, right);
		} else {
			return binarySearch(nums, target, left, mid - 1);
		}
	}
	
	public static void main(String[] args) {
		LeetCode34 leetCode34 = new LeetCode34();
		int[] nums = new int[]{5, 7, 7, 8, 8, 10};
		int[] result = leetCode34.searchRange(nums, 8);
		System.out.println(result[0]);
		System.out.println(result[1]);
	}
}
