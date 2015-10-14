package dynamicprogramming;

public class MinCoins {
	public static void main(String[] args) {
		int[] coins = { 1, 3, 5 };
		int value = 11;
		int min = new MinCoins().solution(coins, value);
		System.out.println(min);

	}

	private int solution(int[] coins, int value) {
		int[] mins = new int[value + 1];
		mins[0] = 0;
		for (int i = 1; i <= value; i++)
			mins[i] = Integer.MAX_VALUE;
		for (int i = 1; i <= value; i++) {
			for (int j = 0; j < coins.length; j++) {
				// coins[j]表示硬币的值，i表示要求的值
				if (coins[j] <= i && mins[i] > mins[i - coins[j]] + 1) {
					mins[i] = mins[i - coins[j]] + 1;
				}
			}
		}
		return mins[value];
	}
}
