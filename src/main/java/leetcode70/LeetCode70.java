package leetcode70;

public class LeetCode70 {
	private int num;
    public int climbStairs(int n) {
    	if (n == 0) {
			return 0;
		}
    	trial(n);
        return num;
    }
    
    public void trial(int n) {
    	if (n < 0) {
			return;
		} else if (n == 0) {
			this.num++;
		} else {
			// 从这里可以看出，爬n阶楼梯的的方法数
			// 是爬n-1阶楼梯和爬n-2阶楼梯的方法数
			// 的和
			trial(n - 1);
			trial(n - 2);
		}
    }
    
    public static void main(String[] args) {
		LeetCode70 leetCode70 = new LeetCode70();
		System.out.println(leetCode70.climbStairs(0));
	}
}
