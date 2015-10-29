package leetcode119;

import java.util.ArrayList;
import java.util.List;

public class LeetCode119 {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> result = new ArrayList<Integer>();
        if (rowIndex < 0) {
			return result;
		}
        
        for(int i = 0; i <= rowIndex; i++) {
        	List<Integer> resultTemp = new ArrayList<Integer>();
        	if (i == 0) {
        		resultTemp.add(1);
        		result = resultTemp;
				continue;
			}
        	
        	int size = i + 1;
        	for(int j = 0; j < size - 1; j++) {
        		if (j == 0) {
        			resultTemp.add(1);
					continue;
				}
        		int numj = result.get(j);
        		int numjMinusOne = result.get(j - 1);
        		resultTemp.add(numj + numjMinusOne);
        	}
        	resultTemp.add(1);
        	result = resultTemp;
        }
        return result;
    }
    public static void main(String[] args) {
		LeetCode119 leetCode119 = new LeetCode119();
		System.out.println(leetCode119.getRow(3));
	}
}
