package leetcode50;

public class LeetCode50 {
    public double myPow(double x, int n) {
        if (n == 0) {
			return 0;
		}
        
        boolean isDaoshu = false;
        if (n < 0) {
			isDaoshu = true;
			n = -n;
		}
        double result = x;
        for(int i = 1; i < n; i++) {
        	result *= x;
        }
        if (isDaoshu) {
			result = 1 / result;
		}
        return result;
    }
    
    public static void main(String[] args) {
		LeetCode50 leetCode50 = new LeetCode50();
		System.out.println(leetCode50.myPow(2, -2));
	}
}
