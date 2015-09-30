package leetcode49;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class LeetCode491 {
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
        
        Arrays.sort(strs);
        
        List<char[]> charsList = convert(strs);
        
        List<List<String>> resultList = new ArrayList<List<String>>();
        // 用于标记哪些已经找到的归属
        BitSet bitSet = new BitSet(len);
        for(int i = 0; i < len; i++) {
        	if (!bitSet.get(i)) {
        		List<String> result = new ArrayList<String>();
        		result.add(strs[i]);
        		bitSet.set(i);
        		char[] icharArr = charsList.get(i);
        		for(int j = i + 1; j < len; j++) {
        			if (!bitSet.get(j)) {
						if (isEqual(icharArr, charsList.get(j))) {
							result.add(strs[j]);
							bitSet.set(j);
						}
					}
        		}
        		resultList.add(result);
			}
        }
        return resultList;
    }
	
	public List<char[]> convert(String[] strs) {
		int len = strs.length;
		List<char[]> charsList = new ArrayList<char[]>(len);
		for(int i = 0; i < len; i ++) {
			String s = strs[i];
			int l = s.length();
			char[] temp = new char[l];
			for(int j = 0; j < l; j++) {
				temp[j] = s.charAt(j);
			}
			Arrays.sort(temp);
			charsList.add(temp);
		}

		return charsList;
	}
	
	/**
	 * 判断两个字符数组是否一样
	 * @param one
	 * @param two
	 * @return
	 */
	public boolean isEqual(char[] one, char[] two) {
		int len1 = one.length;
		int len2 = two.length;
		if (len1 != len2) {
			return false;
		}
		for(int i = 0; i < len1; i++) {
			if(one[i] != two[i]) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		LeetCode491 leetCode491 = new LeetCode491();
		String[] strs = new String[] {"eat", "tea", "tan", "ate", "nat", "bat"};
 		System.out.println(leetCode491.groupAnagrams(strs));
	}
}
