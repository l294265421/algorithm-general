package leetcode89;

import java.util.ArrayList;
import java.util.List;

public class LeetCode89 {
    public List<Integer> grayCode(int n) {
    	List<Integer> r = new ArrayList<Integer>();
    	List<List<Integer>> result = classify(n);
    	
    	r.add(0);
    	
    	int preClass = 0;
    	
    	int nsquare = (int) Math.pow(2, n);
    	for(int i = 1; i <= nsquare; i++) {
    		int candidate1 = preClass - 1;
    		int candidate2 = preClass + 1;
    		// 优先选取candidate1，只有在candidate1没有元素了才去取
    		// candidate2的元素
    		if (candidate1 > 0 && result.get(candidate1).get(0) < 
    				result.get(candidate1).size()) {
				r.add(result.get(candidate1).get(result.get(candidate1).get(0)));
				result.get(candidate1).set(0, result.get(candidate1).get(0) + 1);
				preClass = candidate1;
			} else if(candidate2 <= n && result.get(candidate2).get(0) < 
    				result.get(candidate2).size()) {
				r.add(result.get(candidate2).get(result.get(candidate2).get(0)));
				result.get(candidate2).set(0, result.get(candidate2).get(0) + 1);
				preClass = candidate2;
			}
    		
    	}
    	
    	return r;
    }
	
	/**
	 * 给数字按包含1的个数分类
	 * @param n 二进制位数
	 * @return
	 */
	public  List<List<Integer>> classify(int n) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		for(int i = 0; i <= n; i++) {
			List<Integer> element = new ArrayList<Integer>();
			// 第一个元素用于保存该类中下一个被获取元素的位置
			element.add(1);
			result.add(element);
		}
		
		int nsquare = (int) Math.pow(2, n);
		for(int i = 0; i < nsquare; i++) {
			String numBinaryStr = Integer.toBinaryString(i).toString();
			int countOne = 0;
			int len = numBinaryStr.length();
			for(int j = 0; j < len; j++) {
				if (numBinaryStr.charAt(j) == '1') {
					countOne++;
				}
			}
			result.get(countOne).add(i);
		}
		return result;
	}
	
	public static void main(String[] args) {
		LeetCode89 leetCode89 = new LeetCode89();
		System.out.println(leetCode89.grayCode(3));
	}
}


