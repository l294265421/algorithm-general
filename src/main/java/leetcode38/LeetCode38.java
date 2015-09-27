package leetcode38;

/**
 * 题意：第一个字符串是1，后一个字符串是通过
 * 计数前一个字符串中各个数字出现的个数得到
 * @author yuncong
 *
 */
public class LeetCode38 {
	public String countAndSay(int n) {
		int resultIndex = 1;
		String result = "1";
		if (n == resultIndex) {
			return result;
		}
		
		resultIndex = 2;
		while (resultIndex <= n) {
			int len = result.length();
			StringBuffer resultStrBuf = new StringBuffer();
			char nowChar = result.charAt(0);
			int count = 1;
			for (int i = 1; i < len; i++) {
				char temp = result.charAt(i);
				if (temp == nowChar) {
					count++;
				} else {
					// 碰到不一样的才会保存之前的记录，
					// 这会导致最后一个数字的记录保存
					// 不了
					resultStrBuf.append(count);
					resultStrBuf.append(nowChar);
					nowChar = temp;
					count = 1;
				}
			}
			// 解决最后一个数字无法保存的问题
			resultStrBuf.append(count);
			resultStrBuf.append(nowChar);
			
			result = resultStrBuf.toString();
			resultIndex++;
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		LeetCode38 leetCode38 = new LeetCode38();
		System.out.println(leetCode38.countAndSay(2));
	}
}
