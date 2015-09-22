package leetcode16;

import java.util.Arrays;

public class LeetCode161 {
	public int threeSumClosest(int[] nums, int target) {
        int closestSum = Integer.MAX_VALUE;
    	int len = nums.length;
    	if (len < 3) {
			return closestSum;
		}
        Arrays.sort(nums);
    	
        int minDistance = Integer.MAX_VALUE;
        for(int i = 0; i < len - 2; i++) {
        	int j = i + 1;
        	int k = len - 1;
        	int thisDistance = Integer.MAX_VALUE;
        	if (target < (nums[i] + nums[j] + nums[j + 1])) {
        		int sum = nums[i] + nums[j] + nums[j + 1];
        		int minus = target - sum;
        		thisDistance = Math.abs(minus);
				if (thisDistance < minDistance) {
					minDistance = thisDistance;
					closestSum = sum;
				}
				continue;
			}
        	
        	if (target > (nums[i] + nums[k] + nums[k - 1])) {
        		int sum = nums[i] + nums[k] + nums[k - 1];
        		int minus = target - sum;
        		thisDistance = Math.abs(minus);
				if (thisDistance < minDistance) {
					minDistance = thisDistance;
					closestSum = sum;
				}
				continue;
			}
        	// target介于最大最小之间
        	while (j < k) {
        		int sum = nums[i] + 
        				nums[j] + nums[k];
        		int minus = target - sum;
        		thisDistance = Math.abs(minus);
        		if (thisDistance == 0) {
        			return target;
				}
        		
        		// 距离是不断减小的，如果不变小了，也就没必要继续了
        		if (thisDistance < minDistance) {
					minDistance = thisDistance;
					closestSum = sum;
				}
        		
        		// 从左右两侧靠近
        		if (minus > 0) {
					j++;
				} else {
					k--;
				}
        		
			}
        	
        }
        
        return closestSum;
    }

	public static void main(String[] args) {
		LeetCode161 leetCode16 = new LeetCode161();
		int[] nums = new int[] { 1, 1, 1, 0 };
		System.out.println(leetCode16.threeSumClosest(nums, -100));
	}
}
