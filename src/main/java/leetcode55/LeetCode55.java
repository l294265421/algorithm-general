package leetcode55;

public class LeetCode55 {
	/**
	 * 数组中没0或者只有最后一个是0就一定行；
	 * 有0，只要能调到0的另一边就行,也就是
	 * 说，只要0前面存在这样一个数，它的值大于
	 * 它到0的距离；
	 * 否则就不行
	 * @param nums
	 * @return
	 */
    public boolean canJump(int[] nums) {
        if (nums == null) {
			return false;
		}
        int len = nums.length;
        if (len == 0) {
			return false;
		}
        
        int lastZeroIndex = -1;
        for(int i = 0; i < len; i++) {
        	if (nums[i] == 0) {
        		// 第一个0是最后一个元素
        		if(lastZeroIndex == -1 && i == len - 1) {
        			return true;
        		}
        		// 判断是否能跳过这个0
        		boolean canJump = false;
				for(int j = lastZeroIndex + 1; j < i; j++) {
					if (nums[j] > i - j) {
						canJump = true;
						break;
					}
				}
				if (canJump) {
					continue;
				} else {
					return false;
				}
			}
        }
        return true;
    }
    
    public static void main(String[] args) {
		LeetCode55 leetCode55 = new LeetCode55();
		int[] nums = new int[] {3,2,1,0,4};
		System.out.println(leetCode55.canJump(nums));
	}
}
