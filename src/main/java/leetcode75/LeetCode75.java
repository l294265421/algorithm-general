package leetcode75;

public class LeetCode75 {
	public void sortColors(int[] nums) {
		if (nums == null) {
			return;
		}
		int len = nums.length;
		if (len == 1) {
			return;
		}
		
		int redRightIndex = -1;
		int blueLeftIndex = len;
		int i = 0;
		while (i < blueLeftIndex) {
			int color = nums[i];
			if (color == 0) {
				redRightIndex++;
				// temp只可能是1
				int temp = nums[redRightIndex];
				nums[redRightIndex] = color;
				nums[i] = temp;
				i++;
			} else if (color == 2) {
				blueLeftIndex--;
				// temp可能是0，1或者2,这样，i不能增加，
				// i位置的元素还待处理
				int temp = nums[blueLeftIndex];
				nums[blueLeftIndex] = color;
				nums[i] = temp;
			} else {
				i++;
			}
		}
	}
	
	public static void main(String[] args) {
		LeetCode75 leetCode75 = new LeetCode75();
		int[] colors = new int[] {2,1,0,2,0,2};
		leetCode75.sortColors(colors);
		for(int color : colors) {
			System.out.println(color);
		}
	}
}
