package leetcode8;


public class LeetCode8 {
	public int myAtoi(String str) {
		// 去掉开头的空白字符
		while (str.startsWith(" ")) {
			str = str.substring(1);
		}

		String numberRealStr = findNumberStr(str);

		if (numberRealStr.equals("")) {
			return 0;
		}

		// 判定数的符号
		int sign = 1;
		if (numberRealStr.startsWith("-")) {
			numberRealStr = numberRealStr.substring(1);
			sign = -1;
		} else if (numberRealStr.startsWith("+")) {
			numberRealStr = numberRealStr.substring(1);
		}

		// 计算结果
		int result = 0;
		int len = numberRealStr.length();
		int pow = len - 1;
		try {
			for (int i = 0; i < len; i++) {
				result += (Integer.valueOf(numberRealStr.substring(i, i + 1))
						* Math.pow(10, pow)) * sign;
				pow--;
			}
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 找到字符串str开头表示数字的部分
	 * @param str
	 * @return
	 */
	public String findNumberStr(String str) {
		if (str == null || str.equals("")) {
			return "";
		}
		
		int len = str.length();
		
		String firstChar = str.substring(0, 1);
		if (!firstChar.equals("-") && !firstChar.equals("+") 
				&& !isDigit(firstChar)) {
			return "";
		}
		
		if (firstChar.equals("-") || firstChar.equals("+")) {
			// 如果以-或+开头，长度必须大于2
			if (len < 2) {
				return "";
			}

			// 如果以-或+开头，第二个字符必须是数字
			if (!isDigit(str.substring(1, 2))) {
				return "";
			}
		}

		// 第一个不是表示数字的字母的位置
		int endIndex = 1;
		for (int i = 1; i < len; i++) {
			if (!isDigit(str.substring(i, i + 1))) {
				break;
			}
			endIndex++;
		}
		
		return str.substring(0, endIndex);
	}

	/**
	 * 判断一个字符串表示的是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public boolean isDigit(String str) {
		String digits = "1234567890";
		if (digits.indexOf(str) == -1) {
			return false;
		} else {
			return true;
		}

	}

	public static void main(String[] args) {
		System.out.println(new LeetCode8().myAtoi("-2147483648"));
	}
}
