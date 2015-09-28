package leetcode40;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode40 {
	List<List<Integer>> resultList = new ArrayList<List<Integer>>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    	if (candidates == null || target == 0) {
			return null;
		}
    	
    	Arrays.sort(candidates);
    	
    	int len = candidates.length;
    	
    	for(int i = 0; i < len; i++) {
    		int first = candidates[i];
    		// 第一个元素只有最大限制，没有最小限制
    		if (candidates[i] <= target) {
    			List<Integer> result = new ArrayList<Integer>();
    			result.add(first);
    			trial(candidates, target - first, i + 1, result, 1);
			}
    	}
    	return resultList;
    }
    
    /**
     * depth解决result中元素积累的问题；
     * @param candidates
     * @param target
     * @param min
     * @param result
     * @param depth
     */
    public void trial(int[] candidates, int target, int minIndex, List<Integer> result, int depth) {
		if (target == 0) {
			List<Integer> r = new ArrayList<Integer>();
			for(int i = 0; i < depth; i++) {
				r.add(result.get(i));
			}
			if (!resultList.contains(r)) {
				resultList.add(r);
			}
			return;
		} else if (target < 0) {
			return;
		}else {
			int len = candidates.length;
			for(int i = minIndex; i < len; i++) {
				int temp = candidates[i];
				if (temp <= target) {
					result.add(depth, temp);
					trial(candidates, target - temp, i + 1, result, depth + 1);
				}
			}
		}
	}
    
    public static void main(String[] args) {
		LeetCode40 leetCode40= new LeetCode40();
		int[] candidates = new int[] {10,1,2,7,6,1,5};
		System.out.println(leetCode40.combinationSum2(candidates, 8));
	}
}
