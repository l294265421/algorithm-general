package leetcode11;

public class LeetCode111 {
	public int maxArea(int[] height) {
		int len = height.length;
		int maxArea = 0;
		int area = 0;
		for(int step = 1; step < len; step++) {
			area = maxArea(height, step);
			if (area > maxArea) {
				maxArea = area;
			}
		}
		
		return maxArea;
	}
	
	public int maxArea(int[] height, int step) {
		int len = height.length;
		int maxArea = 0;
		
		int i = 0;
		int area = 0;
		while (i + step < len) {
			area = computeArea(height[i], height[i + step], step);
			if (area > maxArea) {
				maxArea = area;
			}
			i++;
		}
		
		return maxArea;
	}
	
	public int computeArea(int value1, int value2, int step) {
		return value1 < value2 ? value1 * step : value2 * step;
	}
	public static void main(String[] args) {
		int[] height = new int[] {1,2,5,34,35,12,43};
		LeetCode111 leetCode11 = new LeetCode111();
		System.out.println(leetCode11.maxArea(height));
	}
}
