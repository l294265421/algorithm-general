package leetcode35;

public class LeetCode35 {
    public int searchInsert(int[] nums, int target) {
		if(nums == null) {
			return -1;
		}
		
		int len = nums.length;
		if (len == 0) {
			return -1;
		}
        return binarySearch(nums, target, 0, len - 1);
    }
    
	/**
	 * 二分查找动态生成一颗二叉查找树；只有某一个子树上有节点，就去查找；
	 * 当left>right，子树中没有节点了，停止查找；这里的实现与以往
	 * 有所不同，当没查到时，返回的是失败时的左边界，因为如果想把数组中
	 * 不存在的target放进数组并保持数组有序，最终的left就是它的正确
	 * 位置
	 * @param nums
	 * @param target
	 * @param left
	 * @param right
	 * @return
	 */
	public int binarySearch(int[] nums, int target, int left, int right) {
		if (left > right) {
			return left;
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
		LeetCode35 leetCode35 = new LeetCode35();
		int[] nums = new int[] {1,3,5,6};
		System.out.println(leetCode35.searchInsert(nums, 0));
	}
}
