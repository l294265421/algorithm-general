package leetcode6;

public class LeetCode6 {
	/**
	 * @param s 一个字符串
	 * @param numRows
	 * @return 当s中的字符顺序摆放成z字形时，按行收集的结果
	 */
    public String convert(String s, int numRows) {
    	// z为一行，就直接返回
    	if (numRows == 1) {
			return s;
		}
    	
    	String result = "";
    	
    	StringBuffer[] rowArr = new StringBuffer[numRows];
    	for (int i = 0; i < numRows; i++) {
			rowArr[i] = new StringBuffer();
		}
    	
    	int sLen = s.length();
    	// 存放zigzag的周期
    	int cycle = 2 * numRows - 2;
    	// 记录下一个元素应该放在第几行
    	int rowth = 0;
    	for(int i = 0; i < sLen; i++) {
    		rowArr[rowth].append(s.charAt(i));
    		int orderInCycle = i % cycle;
    		// 根据目前元素在z周期中的位置，决定下一个元素在第几行
    		if (orderInCycle < numRows - 1) {
				rowth++;
			} else {
				rowth--;
			}
    	}
    	
    	// 收集各行
    	for(StringBuffer stringBuffer : rowArr) {
    		result += stringBuffer.toString();
    	}
        return result;
    }
    
    public static void main(String[] args) {
		LeetCode6 leetCode4 = new LeetCode6();
		// PAHNAPLSIIGYIR
		System.out.println(leetCode4.convert("PAYPALISHIRING", 1));
	}
}
