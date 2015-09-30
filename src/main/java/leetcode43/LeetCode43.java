package leetcode43;

public class LeetCode43 {
    public String multiply(String num1, String num2) {
        if (num1 == null || num2 == null) {
			return null;
		}
        int oneLen = num1.length();
        int twoLen = num2.length();
        if (oneLen == 0 || twoLen == 0) {
			return null;
		}
        
        String result = "0";
        if (num1.equals(result) || num2.equals(result)) {
			return result;
		}
        boolean isFirst = true;
        for(int i = twoLen - 1; i >= 0; i--) {
        	String two = num2.substring(i, i + 1);
        	if (two.equals("0")) {
				continue;
			}
        	String temp = multiply(num1, two, twoLen - 1 - i);
        	if (isFirst) {
				result = temp;
				isFirst = false;
			} else {
				int resultLen = result.length();
				int tempLen = temp.length();
				if (resultLen > tempLen) {
					result = add(result, temp);
				} else {
					result = add(temp, result);
				}
			}
        }
        return result;
    }
	/**
	 * 大小是通过字符串长度来区分的
	 * @param big
	 * @param small
	 * @return
	 */
	public String add(String big, String small) {
		StringBuffer resultBuffer = new StringBuffer();
		int bigLen = big.length();
		int smallLen = small.length();
		int fromPre = 0;
		for(int i = 0; i < smallLen; i++) {
			int first = Integer.valueOf(big.substring(bigLen - 1 - i, bigLen - 1 - i + 1));
			int second = Integer.valueOf(small.substring(smallLen - 1 -i, smallLen - 1 - i + 1));
			int result = first + second + fromPre;
			if (result >= 10) {
				result = result % 10;
				fromPre = 1;
			} else {
				fromPre = 0;
			}
			resultBuffer.append(String.valueOf(result));
		}
		if (bigLen > smallLen) {
			for(int i = bigLen - smallLen - 1; i >= 0; i--) {
				int first = Integer.valueOf(big.substring(i, i + 1));
				int result = first + fromPre;
				if (result >= 10) {
					result = result % 10;
					fromPre = 1;
				} else {
					fromPre = 0;
				}
				resultBuffer.append(String.valueOf(result));
			}
		}
		
		if (fromPre == 1) {
			resultBuffer.append("1");
		}
		return resultBuffer.reverse().toString();
	}
	
	/**
	 * two是处在某个数某个位置上的数，position是这个数在某个数中从右至左的位置,
	 * 从0开始计算
	 * @param one
	 * @param two
	 * @param position
	 * @return
	 */
	public String multiply(String one, String two, int position) {
		StringBuffer resultBuffer = new StringBuffer();
		int len = one.length();
		int t = Integer.valueOf(two);
		int fromPre = 0;
		for(int i = len - 1; i >= 0; i--) {
			int first = Integer.valueOf(one.substring(i, i+1));
			int result = first * t + fromPre;
			fromPre = result / 10;
			result = result % 10;
			resultBuffer.append(result);
		}
		if (fromPre != 0) {
			resultBuffer.append(fromPre);
		}
		resultBuffer = resultBuffer.reverse();
		for(int i = 0; i < position; i++) {
			resultBuffer.append("0");
		}
		return resultBuffer.toString();
	}
	
	public static void main(String[] args) {
		LeetCode43 leetCode43 = new LeetCode43();
		System.out.println(leetCode43.add("1123", "9134"));
		System.out.println(leetCode43.multiply("0", "52"));
	}
}
