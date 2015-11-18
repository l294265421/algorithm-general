package leetcode131;

import java.util.ArrayList;
import java.util.List;

public class LeetCode131 {
	public List<List<String>> partition(String s) {
		List<List<String>> resultList = new ArrayList<>();
		int len = s.length();
		if (len == 0) {
			return resultList;
		}
		if (len == 1) {
			List<String> result = new ArrayList<>();
			result.add(s);
			resultList.add(result);
			return resultList;
		}
		
		// 长度大于等于2才需要分
		resultList = partition(s, 0, len, new ArrayList<String>());
		return resultList;
	}

	public List<List<String>> partition(String s, int start, int len,
			List<String> result) {
		List<List<String>> resultList = new ArrayList<>();
		if (start == len) {
			resultList.add(result);
			return resultList;
		}
		
		int maxSize = len - start;
		for(int size = 1; size <= maxSize; size++) {
			int newStart = size + start;
			String candidate = s.substring(start, newStart);
			boolean isPalindrome = isPalindrome(candidate);
			if (isPalindrome) {
				List<String> newResult = myClone(result);
				newResult.add(candidate);
				resultList.addAll(partition(s, start, len, newResult));
			}
		}
		return resultList;
	}

	private List<String> myClone(List<String> fore) {
		//这不是深度拷贝，但是因为字符串是不可变的，所以不会出错
		List<String> nowList = new ArrayList<>(fore);
		return nowList;
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
	}
}
