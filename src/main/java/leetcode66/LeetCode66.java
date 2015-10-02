package leetcode66;

public class LeetCode66 {
    public int[] plusOne(int[] digits) {
        if (digits == null) {
			return digits;
		}
        int len = digits.length;
        if (len == 0) {
			return digits;
		}
        
        int fromPre = 1;
        int i = len - 1;
        for(; i >= 0; i--) {
        	int result = digits[i] + 1;
        	if (result >= 10) {
				fromPre = 1;
				result = result % 10;
				digits[i] = result;
			} else {
				digits[i] = result;
				break;
			}
        }
        if (i == -1) {
        	int[] temp = new int[len + 1];
        	temp[0] = 1;
        	for(int j = 1; j < len + 1; j++) {
        		temp[j] = digits[j - 1];
        	}
        	digits = temp;
		}
        return digits;
    }
    
    public static void main(String[] args) {
		LeetCode66 leetCode66 = new LeetCode66();
		int [] digits = new int[] {9,8};
		digits = leetCode66.plusOne(digits);
		for(int num : digits) {
			System.out.print(num);
		}
	}
}
