package leetcode7;

public class LeetCode7 {
	/**
	 * 反转int
	 * @param x
	 * @return
	 */
	public int reverse(int x) {
		String xStr = String.valueOf(x);
		boolean isMinus = false;
		if (xStr.startsWith("-")) {
			xStr = xStr.substring(1);
			isMinus = true;
		}
		while (xStr.endsWith("0")) {
			xStr = xStr.substring(0, xStr.length() - 1);
		}
		String resultStr = reverse(xStr);
		if (isMinus) {
			resultStr = "-" + resultStr;
		}
		
		int result = 0;
		try {
			result = Integer.valueOf(resultStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 反转int数组
	 * @param numberArr
	 */
	public void reverse(int[] numberArr) {
		int len = numberArr.length;
		int bound = len / 2;
		for(int i = 0; i < bound; i++) {
			int temp = numberArr[i];
			int endIndex = len - 1 - i;
			numberArr[i] = numberArr[endIndex];
			numberArr[endIndex] = temp;
		}
	}
	
	/**
	 * 反转字符串
	 * @param string
	 * @return
	 */
	public String reverse(String string) {
		StringBuffer stringBuffer = new StringBuffer();
		int len = string.length();
		for(int i = len - 1; i >= 0; i--) {
			stringBuffer.append(string.charAt(i));
		}
		return stringBuffer.toString();
	}
	
	
	public static void main(String[] args) {
		LeetCode7 leetCode7 = new LeetCode7();
		System.out.println(leetCode7.reverse(123));
	}
}
