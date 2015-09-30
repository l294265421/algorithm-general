package leetcode49;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class LeetCode49 {
	/**
	 * 核心思想：两个anagrams，对其中的字母排序后，必然相等
	 * 实现方式：确定一个词，找所有的anagrams,把确定了归属的
	 * 做个标记
	 * @param strs
	 * @return
	 */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null) {
			return null;
		}
        int len = strs.length;
        if (len == 0) {
			return null;
		}
        
        String[] temp = new String[len];
        for(int i = 0; i < len; i++) {
        	temp[i] = sort(strs[i]);
        }
        
        List<List<String>> resultList = new ArrayList<List<String>>();
        // 用于标记哪些已经找到的归属
        BitSet bitSet = new BitSet(len);
        for(int i = 0; i < len; i++) {
        	if (!bitSet.get(i)) {
        		List<Integer> resultIndex = new ArrayList<Integer>();
        		resultIndex.add(i);
        		bitSet.set(i);
        		for(int j = i + 1; j < len; j++) {
        			if (!bitSet.get(j)) {
						if (temp[j].equals(temp[i])) {
							resultIndex.add(j);
							bitSet.set(j);
						}
					}
        		}
        		List<String> result = new ArrayList<String>();
        		for(Integer index : resultIndex) {
        			result.add(strs[index]);
        		}
        		resultList.add(result);
			}
        }
        return resultList;
    }
	
	public String sort(String s) {
		int len = s.length();
		char[] temp = new char[len];
		for(int i = 0; i < len; i++) {
			temp[i] = s.charAt(i);
		}
		Arrays.sort(temp);
		StringBuffer resultBuffer = new StringBuffer();
		for (char c: temp) {
			resultBuffer.append(c);
		}
		return resultBuffer.toString();
	}
	
	public static void main(String[] args) {
		LeetCode49 leetCode49 = new LeetCode49();
		String[] strs = new String[] {"eat", "tea", "tan", "ate", "nat", "bat"};
 		System.out.println(leetCode49.groupAnagrams(strs));
	}
}
