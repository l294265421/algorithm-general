package leetcode67;

public class LeetCode67 {
	public String addBinary(String a, String b) {
		if (a.equals("") && !b.equals("")) {
			return b;
			// 包含了a等于""和不等于""两种情况
		} else if (b.equals("")) {
			return a;
		}
		int aLen = a.length();
		int blen = b.length();
		StringBuffer resultBuffer = new StringBuffer();
		int i = aLen - 1, j = blen - 1;
		int fromPre = 0;
		// 共有元素
		for (; i >= 0 && j >= 0; i--, j--) {
			int froma = Integer.valueOf(a.substring(i, i + 1));
			int fromb = Integer.valueOf(b.substring(j, j + 1));
			int result = froma + fromb + fromPre;
			if (result >= 2) {
				fromPre = 1;
				result = result % 2;
				resultBuffer.append(result);
			} else {
				fromPre = 0;
				resultBuffer.append(result);
			}
		}
		// 仅自己有
		if (i != -1) {
			for (int k = i; k >= 0; k--) {
				int froma = Integer.valueOf(a.substring(k, k + 1));
				int result = froma + fromPre;
				if (result >= 2) {
					fromPre = 1;
					result = result % 2;
					resultBuffer.append(result);
				} else {
					fromPre = 0;
					resultBuffer.append(result);
				}
			}
		} else if(j != -1){
			for (int k = j; k >= 0; k--) {
				int fromb = Integer.valueOf(b.substring(k, k + 1));
				int result = fromb + fromPre;
				if (result >= 2) {
					fromPre = 1;
					result = result % 2;
					resultBuffer.append(result);
				} else {
					fromPre = 0;
					resultBuffer.append(result);
				}
			}
		}

		// a和b中所有元素都已经参与了计算
		if (fromPre == 1) {
			resultBuffer.append("1");
		}
		return resultBuffer.reverse().toString();
	}
	
	public static void main(String[] args) {
		LeetCode67 leetCode67 = new LeetCode67();
		System.out.println(leetCode67.addBinary("11", "1"));
	}
}
