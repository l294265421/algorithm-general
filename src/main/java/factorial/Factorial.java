package factorial;

public class Factorial {
	/**
	 * 递归计算阶乘
	 * @param n
	 * @return
	 */
	public static int compute1(int n) {
		if (n == 0) {
			return 1;
		}
		return n * compute1(n - 1);
	}
	
	/**
	 * 递推计算阶乘
	 * @param n
	 * @return
	 */
	public static int compute2(int n) {
		if (n == 0) {
			return 1;
		}
		int result = 1;
		for(int i = 1; i <= n; i++) {
			result *= i;
		}
		return result;
	}
	
	/**
	 * 尾递归计算阶乘;
	 * 当递归调用是整个函数体中最后执行的语句且它的返回值不属于表达式的一部分时，
	 * 这个递归调用就是尾递归。尾递归函数的特点是在回归过程中不用做任何操作，
	 * 这个特性很重要，因为大多数现代的编译器会利用这种特点自动生成优化的代码。
	 * @param n
	 * @return
	 */
	public static int compute3(int n, int accumulator) {
		if (n == 0) {
			return accumulator;
		}
		return compute3(n - 1, accumulator * n);
	}
	
	/**
	 * 消除尾递归计算阶乘
	 * @param n
	 * @return
	 */
	public static int compute4(int n, int accumulator) {
	       while (true)
           {
               int tempN = n;
               int tempAccumulator = accumulator;

               if (tempN == 0)
               {
                   return tempAccumulator;
               }

               n = tempN - 1;
               accumulator = tempN * tempAccumulator;
           }
	}
	
	public static void main(String[] args) {
		System.out.println(Factorial.compute1(12));
		System.out.println(Factorial.compute2(12));
		System.out.println(Factorial.compute3(12, 1));
		System.out.println(Factorial.compute4(12, 1));
	}
}
