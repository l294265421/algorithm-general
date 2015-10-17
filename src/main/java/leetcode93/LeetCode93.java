package leetcode93;

import java.util.ArrayList;
import java.util.List;

public class LeetCode93 {
	public List<String> restoreIpAddresses(String s) {
		List<String> resultList = new ArrayList<String>();
		if (s == null) {
			return resultList;
		}
		int sLen = s.length();
		if (sLen < 4 || sLen > 12) {
			return resultList;
		}
		restoreIpAddresses(s, 0, 0, "", resultList);
		return resultList;
	}

	public void restoreIpAddresses(String s, int startIndex, int dotTh,
			String result, List<String> resultList) {
		int sLen = s.length();
		if (startIndex >= sLen) {
			return;
		}

		if (dotTh == 3) {
			if (sLen > startIndex + 1) {
				if (s.charAt(startIndex) == '0') {
					return;
				}
			}

			String resultTemp = new String(result);
			if (dotTh != 0) {
				resultTemp += ".";
			}

			String end = s.substring(startIndex);
			Integer temp = Integer.valueOf(end);
			if (temp > 255) {
				return;
			} else {
				resultTemp += end;
				resultList.add(resultTemp);
			}
		}

		if (startIndex < sLen) {
			String resultTemp = new String(result);
			if (dotTh != 0) {
				resultTemp += ".";
			}

			String temp = s.substring(startIndex, startIndex + 1);
			resultTemp += temp;
			restoreIpAddresses(s, startIndex + 1, dotTh + 1, resultTemp,
					resultList);
		}

		if (startIndex + 1 < sLen) {
			if (s.charAt(startIndex) == '0') {
				return;
			}

			String resultTemp = new String(result);
			if (dotTh != 0) {
				resultTemp += ".";
			}

			String temp = s.substring(startIndex, startIndex + 2);
			resultTemp += temp;
			restoreIpAddresses(s, startIndex + 2, dotTh + 1, resultTemp,
					resultList);
		}

		if (startIndex + 2 < sLen) {
			if (s.charAt(startIndex) == '0') {
				return;
			}

			String resultTemp = new String(result);
			if (dotTh != 0) {
				resultTemp += ".";
			}

			String temp = s.substring(startIndex, startIndex + 3);
			if (Integer.valueOf(temp) > 255) {
				return;
			}
			resultTemp += temp;
			restoreIpAddresses(s, startIndex + 3, dotTh + 1, resultTemp,
					resultList);
		}
	}

	public static void main(String[] args) {
		LeetCode93 leetCode93 = new LeetCode93();
		System.out.println(leetCode93.restoreIpAddresses("0000"));
	}
}
