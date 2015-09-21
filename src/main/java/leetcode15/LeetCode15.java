package leetcode15;

import java.util.ArrayList;
import java.util.List;

public class LeetCode15 {
    public List<List<Integer>> threeSum(int[] nums) {
    	List<List<Integer>> integerListList = new ArrayList<List<Integer>>();
    	
    	int len = nums.length;
    	if (len < 3) {
			return integerListList;
		}
    	
    	sort(nums);
    	
    	int seprator = findFirstNonnegtiveIndex(nums);
    	if (seprator == -1) {
			return integerListList;
		}
    	
    	// 从三元组中间元素开始
    	for(int i = 1; i < len - 2; i++) {
    		oneCenter(nums, i, seprator, integerListList);
    	}
        return integerListList;
    }
    
    public void oneCenter(int[] nums, int centerIndex, int seprator, List<List<Integer>> integerListList) {
    	int center = nums[centerIndex];
    	int len = nums.length;
    	if (center >= 0) {
    		// 左边元素必须是负数
    		int left = seprator - 1;
    		for(; left >= 0; left--) {
    			int right = centerIndex + 1;
    			for(; right < len; right++) {
    				if (center + nums[left] + nums[right] == 0) {
    					List<Integer> integerList = new ArrayList<Integer>();
    			    	integerList.add(nums[left]);
    			    	integerList.add(center);
    			    	integerList.add(nums[right]);
    			    	if (!integerListList.contains(integerList)) {
							integerListList.add(integerList);
						}
    			    	break;
					}
    			}
    		}
		} else {
			// 右边必须是正数
			int right = seprator;
    		for(; right < len; right++) {
    			int left = centerIndex - 1;
    			for(; left >= 0; left--) {
    				if (center + nums[left] + nums[right] == 0) {
    					List<Integer> integerList = new ArrayList<Integer>();
    			    	integerList.add(nums[left]);
    			    	integerList.add(center);
    			    	integerList.add(nums[right]);
    			    	if (!integerListList.contains(integerList)) {
							integerListList.add(integerList);
						}
    			    	break;
					}
    			}
    		}
		}
	}
    
    public int findFirstNonnegtiveIndex(int[] nums) {
    	int len = nums.length;
		for(int i = 0; i < len; i++) {
			if (nums[i] >= 0) {
				return i;
			}
		}
		return -1;
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
    	LeetCode15 leetCode15 = new LeetCode15();
		int[] heap = new int[]{-1, 0, 1, 2, -1, -4};
		List<List<Integer>> integerListList = leetCode15.threeSum(heap);
		System.out.println(integerListList);
	}
}
