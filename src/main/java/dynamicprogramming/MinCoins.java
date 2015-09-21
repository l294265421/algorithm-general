package dynamicprogramming;

public class MinCoins {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] coins = { 1, 3, 5 };
		int value = 11;
		int[] solu = new int[value];
		int min = new MinCoins().solution(coins, value, solu);
		for (int i = value - 1; i >= 0;) {
			System.out.print(solu[i] + "->");
			i = i - solu[i];
		}
		System.out.println();
		System.out.println(min);

	}

	private int solution(int[] coins, int value, int[] solu) {
		int[] mins = new int[value + 1];
		mins[0] = 0;
		for (int i = 1; i <= value; i++)
			mins[i] = Integer.MAX_VALUE;
		for (int i = 1; i <= value; i++) {
			for (int j = 0; j < coins.length; j++) {
				// coins[j]表示硬币的值，i表示要求的值
				// 不断优化solu[i - 1]
				if (coins[j] <= i && mins[i] > mins[i - coins[j]] + 1) {
					mins[i] = mins[i - coins[j]] + 1;
					solu[i - 1] = coins[j];
				}
			}
		}
		return mins[value];
	}
}
