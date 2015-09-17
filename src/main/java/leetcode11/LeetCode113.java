package leetcode11;

public class LeetCode113 {
	public int maxArea(int[] height) {
		int len = height.length;
		int maxArea = 0;
		
		for(int i = 0; i < len - 1; i++) {
			int right = len - 1;
			while (height[i] > height[right] && i < right) {
				maxArea = Math.max(maxArea, (right - i) * height[right]);
				right--;
			}
			maxArea = Math.max(maxArea, (right - i) * height[i]);
		}
		
		for(int i = len - 1; i >= 0; i--) {
			int left = 0;
			while (height[i] > height[left] && left < i) {
				maxArea = Math.max(maxArea, (i - left) * height[left]);
				left++;
			}
			maxArea = Math.max(maxArea, (i - left) * height[i]);
		}
		
		return maxArea;
	}
	
	public static void main(String[] args) {
		int[] height = new int[] {1,2,5,34,35,12,43};
		LeetCode113 leetCode11 = new LeetCode113();
		System.out.println(leetCode11.maxArea(height));
	}
}
