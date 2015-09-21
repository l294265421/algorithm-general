package leetcode15;

import java.util.ArrayList;
import java.util.List;

public class LeetCode151 {
	public List<List<Integer>> threeSum(int[] nums) {
	    List<List<Integer>> triplets = new ArrayList<List<Integer>>();
	    if (nums.length < 3)
	        return triplets;
	    else {
	        this.sort(nums);
	        int i = 0;
	        // 以i开头
	        while(i < nums.length - 2 && nums[i] <= 0) {
	            int j = i + 1, k = nums.length - 1;
	            int sum = 0 - nums[i];
	            while (j < k) {
	                if ((nums[j] + nums[k]) == sum) {
	                    List<Integer> triplet = new ArrayList<Integer>();
	                    triplet.add(new Integer(nums[i]));
	                    triplet.add(new Integer(nums[j]));
	                    triplet.add(new Integer(nums[k]));
	                    triplets.add(triplet);

	                    //skip duplicates. Key to pass the test cases.
	                    while (k > j && nums[k] == nums[k - 1]) k--;
	                    while (j < k && nums[j] == nums[j + 1]) j++;

	                    j++; k--;           
	                } else if (nums[j] + nums[k] > sum)
	                    k--;
	                else
	                    j++;    
	            }
	            //skip duplicates.Key to pass the test cases.
	            while (i < nums.length - 2 && nums[i] == nums[i+1]) i++;

	            i++;
	        }
	    return triplets;
	    }
	}
	
	public void sort(int[] nums) {
    	this.sort(nums, 0, nums.length - 1);
	}
    
    public void  sort(int[] nums, int p, int r) {
		// 确保待排序的子数组中至少有两个元素
		if (p < r) {
			int q = this.partition(nums, p, r);
			sort(nums, 0, q - 1);
			sort(nums, q + 1, r);
		}
	}
    
    public int partition(int[] nums, int p, int r) {
    	int x = nums[r];
    	int i = p - 1;
    	for(int j = p; j < r; j++) {
    		if (nums[j] < x) {
				i++;
				int temp = nums[j];
				nums[j] = nums[i];
				nums[i] = temp;
			}
    	}
    	i++;
    	nums[r] = nums[i];
    	nums[i] = x;
    	return i;
    }
    
    public static void main(String[] args) {
    	LeetCode151 leetCode15 = new LeetCode151();
		int[] heap = new int[]{-1, 0, 1, 2, -1, -4};
		List<List<Integer>> integerListList = leetCode15.threeSum(heap);
		System.out.println(integerListList);
	}
}
