package leetcode131;

import java.util.LinkedList;
import java.util.List;

/**
 * 开始使用的是ArrayList存储，但是超过了内存限制，然后改为了
 * LinkedList，就保持在内存限制之内了；这让我对它们的性质有了
 * 更深刻的认识，ArrayList用数组作为存储，检索快，但是每次增加
 * 的容量比较大，LinkedList只在需要时才增加一个容量
 * @author yuncong
 *
 */
public class LeetCode131 {
	public List<List<String>> partition(String s) {
		List<List<String>> resultList = new LinkedList<>();
		int len = s.length();
		if (len == 0) {
			return resultList;
		}
		if (len == 1) {
			List<String> result = new LinkedList<>();
			result.add(s);
			resultList.add(result);
			return resultList;
		}
		
		// 长度大于等于2才需要分
		partition(s, 0, len, "", resultList);
		return resultList;
	}

	/**
	 * 
	 * @param s 待切分字符串
	 * @param start 当前起始点
	 * @param len s的长度
	 * @param seperators 所有分割点组成的字符串
	 * @return
	 */
	public void partition(String s, int start, int len,
			String seperators, List<List<String>> resultList) {
		if (start == len) {
			List<String> result = generateResult(s, len, seperators);
			if (result != null) {
				resultList.add(result);
			}
		}
		
		if (!seperators.equals("")) {
			seperators += "|";
		}
		int maxSize = len - start;
		for(int size = 1; size <= maxSize; size++) {
			int newStart = size + start;
			String candidate = s.substring(start, newStart);
			boolean isPalindrome = isPalindrome(candidate);
			if (isPalindrome) {
				String newSeperators = seperators + newStart;
				partition(s, newStart, len, newSeperators, resultList);
			}
		}
	}
	
	/**
	 * 
	 * @param s 待切分字符串
	 * @param sLen s的长度
	 * @param seperators 所有分割点组成的字符串
	 * @return
	 */
	private List<String> generateResult(String s, int sLen, String seperators) {
		String[] seperatorArr = seperators.split("\\|");
		if (seperatorArr[0].equals("")) {
			return null;
		}
		
		List<String> result = new LinkedList<>();
		int elementNum = seperatorArr.length;
		int start = 0;
		int end = -1;
		for(int i = 0; i < elementNum; i++) {
			end = Integer.valueOf(seperatorArr[i]);
			result.add(s.substring(start, end));
			start = end;
		}
		
		return result;
	}

	private boolean isPalindrome(String candidate) {
		int len = candidate.length();
		if(len == 1) {
			return true;
		}
		
		int hasfLen = len / 2;
		for (int i = 0; i < hasfLen; i++) {
			if (candidate.charAt(i) != candidate.charAt(len - 1 - i)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		LeetCode131 leetCode131 = new LeetCode131();
		System.out.println(leetCode131.partition("ab"));
	}
}
