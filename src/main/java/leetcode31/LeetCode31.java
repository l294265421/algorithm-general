package leetcode31;

import java.util.Arrays;

/**
 * 实现前的思考点滴：
 * 1.对一个升序数组（a1,a2,a3...an）完成从小到大全排列的过程
 * （实际上就是一步步从升序排列到降序排列的过程）：
 * 我们可以这样划分数组中的元素(a1...|...an),|从an前面移动到
 * a1前面，|后面的元素集合都必须经历从升序排列到降序排列的过程，
 * 当|后面的元素变成降序排列时，这个子集变为最大，需要向前进位（
 * 类似十进制进位），进位的方式就是：交换|后面的所有元素中比|前面一个
 * 元素大的最小元素，当这个子集的前面一位得到进位变大之后，|后面
 * 的元素又需要再次经历从升序排列到降序排列的过程，这时，这个子集的
 * 元素个数没变，但是元素有所变化；而对在对整个升序数组一步步变为
 * 降序数组的过程中遇到的所有升序子数组，也需要经历同样的过程。
 * 2.也可以这样来理解实现全排列的过程：在数组中，从右至左，数组中的
 * 每一个元素位置都经历这样一个过程，从最初的元素一步步变为它后面
 * 的元素中的最大元素，而每一次变化，它后面的元素集合都需要经历从
 * 升序排列到降序排列的完整过程。
 * 3.现在又来思考，这种实现方式如何对等我们在数学中学习的，第一个
 * 位置有n种选择，第二个位置有n-1个选择，最后一个位置只有一个选择，
 * 这样的方式呢？我们通过第二点的简化理解可以看到，第一个位置确实有
 * n个选择，第二个位置有n-1个选择，最后一个位置只有一个选择。（
 * 从宏观上，我算是理解了，但是具体到细节里，我也想象不出）
 * 4.在完成全排列的过程中，我注意到这样一个事实：数组在变大，变大是通
 * 过进位来实现的；进位作用在这样一个元素身上：
 * 它后面的元素是降序排列的，它及它后面的元素不是降序排列的；
 * （类比于从十进制从0变大某个数）
 * @author yuncong
 *
 */
public class LeetCode31 {
	/**
	 * 核心思想：从右到左，找到下一步需要变化的元素的位置，将它后面的比它大
	 * 的最小元素（这个元素一定存在）和它互换位置，并对后面的元素进行升序排列；
	 * 如果原数组本身就降序排列，就反转使之升序排列
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
			for(int i = firstSmallIndex + 1; i < len; i++) {
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
		for(int i = 0; i < limit; i++) {
			int temp = nums[i];
			nums[i] = nums[len - 1 - i];
			nums[len - 1 - i] = temp;
		}
	}
	
	public static void main(String[] args) {
		int[] nums = new int[] {1,1,5};
		LeetCode31 leetCode31 = new LeetCode31();
		leetCode31.nextPermutation(nums);
		for (int i : nums) {
			System.out.println(i);
		}
	}
}
