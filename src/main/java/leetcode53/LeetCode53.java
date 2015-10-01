package leetcode53;

public class LeetCode53 {
	/**
	 * 任意最大子序列必定以a中某个数字结束；
	 * 任意最大子序列必定由a中某个数字结束加上某个序列前缀组成；
	 * 一个候选最大子序列结尾数字的前缀序列必须为不为负
	 * @param nums
	 * @return
	 */
    public int maxSubArray(int[] nums) {
    	if (nums == null) {
			return 0;
		}
    	int len = nums.length;
    	if (len == 0) {
			return 0;
		}
    	
        int max = 0;
        
        // 这一部份判断数组中元素是否全负，如果全负，就找出最大负数，这个
        // 最大负数就是最大子序列和
        boolean isAllMinus = true;
        for(int num : nums) {
        	if (num >= 0) {
				isAllMinus = false;
				break;
			}
        }
        if (isAllMinus) {
			max = Integer.MIN_VALUE;
			for(int num : nums) {
				if (num > max) {
					max = num;
				}
			}
			return max;
		}
        
        // 这一部份只能找到数组中包含非负数的最大子序列和
        int thisMax = 0;
        for(int i = 0; i < len; i++) {
        	thisMax += nums[i];
        	if (thisMax > max) {
				max = thisMax;
			}
        	// 小于0的序列作为前缀使整个序列变小；
        	if (thisMax < 0) {
				thisMax = 0;
			}
        }
        return max;
    }
    
    public static void main(String[] args) {
		LeetCode53 leetCode53 = new LeetCode53();
		int[] nums = new int[] {-2};
		System.out.println(leetCode53.maxSubArray(nums));
	}
}
