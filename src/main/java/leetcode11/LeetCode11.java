package leetcode11;

public class LeetCode11 {
	public int maxArea(int[] height) {
		int len = height.length;
		int maxArea = 0;
		for(int i = 0; i < len - 1; i++) {
			for(int j = i + 1; j < len; j++) {
				int area = computeArea(i, height[i], j, height[j]);
				if (area > maxArea) {
					maxArea = area;
				}
			}
		}
		return maxArea;
	}
	
	public int computeArea(int index1, int value1, int index2, int value2) {
		int width = index2 - index1;
		return value1 < value2 ? value1 * width : value2 * width;
	}
	public static void main(String[] args) {
		int[] height = new int[] {1,2,5,34,35,12,43};
		LeetCode11 leetCode11 = new LeetCode11();
		System.out.println(leetCode11.maxArea(height));
	}
}
