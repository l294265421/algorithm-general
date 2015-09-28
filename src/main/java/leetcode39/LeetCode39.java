package leetcode39;

import java.util.ArrayList;
import java.util.List;

/**
 * 回溯
 * @author liyuncong
 *
 */
public class LeetCode39 {
	List<List<Integer>> resultList = new ArrayList<List<Integer>>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
    	if (candidates == null || target == 0) {
			return null;
		}
    	
    	int len = candidates.length;
    	
    	for(int i = 0; i < len; i++) {
    		int first = candidates[i];
    		// 第一个元素只有最大限制，没有最小限制
    		if (candidates[i] <= target) {
    			List<Integer> result = new ArrayList<Integer>();
    			result.add(first);
    			trial(candidates, target - first, candidates[i], result, 1);
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
    public void trial(int[] candidates, int target, int min, List<Integer> result, int depth) {
		if (target == 0) {
			List<Integer> r = new ArrayList<Integer>();
			for(int i = 0; i < depth; i++) {
				r.add(result.get(i));
			}
			resultList.add(r);
			return;
		} else if (target < 0) {
			return;
		}else {
			int len = candidates.length;
			for(int i = 0; i < len; i++) {
				int temp = candidates[i];
				if (temp >= min && temp <= target) {
					result.add(depth, temp);
					trial(candidates, target - temp, temp, result, depth + 1);
				}
			}
		}
	}
    
    public static void main(String[] args) {
		LeetCode39 leetCode39 = new LeetCode39();
		int[] candidates = new int[] {2,3,6,7};
		System.out.println(leetCode39.combinationSum(candidates, 7));
	}
}
