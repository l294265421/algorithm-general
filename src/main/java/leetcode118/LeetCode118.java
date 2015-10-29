package leetcode118;

import java.util.ArrayList;
import java.util.List;

public class LeetCode118 {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> resultList = new ArrayList<List<Integer>>();
        if (numRows == 0) {
			return resultList;
		}
        
        for(int i = 0; i < numRows; i++) {
        	List<Integer> result = new ArrayList<Integer>();
        	if (i == 0) {
				result.add(1);
				resultList.add(result);
				continue;
			}
        	
        	int size = i + 1;
        	for(int j = 0; j < size - 1; j++) {
        		if (j == 0) {
					result.add(1);
					continue;
				}
        		int numiMinusOnej = resultList.get(i - 1).get(j);
        		int numiMinusOnejMinusOne = resultList.get(i - 1)
        				.get(j - 1);
        		result.add(numiMinusOnej + numiMinusOnejMinusOne);
        	}
        	result.add(1);
        	resultList.add(result);
        }
        return resultList;
    }
    public static void main(String[] args) {
		LeetCode118 leetCode118 = new LeetCode118();
		System.out.println(leetCode118.generate(3));
	}
}
