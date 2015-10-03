package leetcode70;

public class LeetCode701 {
	public int climbStairs(int n) {
		// // 从这里可以看出，爬n阶楼梯的的方法数
		// 是爬n-1阶楼梯和爬n-2阶楼梯的方法数
		// 的和；这一点可以从LeetCode70中得出
		if (n == 0)
			return 0;
		if (n == 1)
			return 1;
		if (n == 2)
			return 2;
		int step1 = 1, step2 = 2;
		int temp;
		for (int i = 3; i <= n; i++) {
			temp = step1;
			step1 = step2;
			step2 += temp;
		}
		return step2;
	}

	public static void main(String[] args) {
		LeetCode701 leetCode70 = new LeetCode701();
		System.out.println(leetCode70.climbStairs(44));
	}
}
