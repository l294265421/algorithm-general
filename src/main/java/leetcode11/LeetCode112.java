package leetcode11;

public class LeetCode112 {
	/**
	 * 贪心算法实现；
	 * 证明算法的正确性
	 * The other answers with explanations all prove that, starting 
	 * from the edge, with the moving strategy ( i.e. move the 
	 * pointer of the smaller value to the inner side ), the amount 
	 * of water will increase sometime in the future.
	 * However, can the maximum value be reached? That's the key problem.

	 * Here is my proof of why the maximum value can be reached definitely.

	 * Given a1, a2, ..., an.

	 * First of all, let's assume that the optimal choice of the container 
	 * with the most water is ( ai, aj ) ( i < j ). Then I just need to 
	 * prove that there will definitely exist one situation where the left 
	 * pointer is at ai while the right pointer is at aj.
	 *
	 * Then I will prove that a1, a2, ...and ai-1 are all smaller than 
	 * aj. This proof is simple, because if ak >= aj and 1 <= k <= i-1, 
	 * then the optimal choice would be ( ak, aj ), not ( ai, aj ).

	 * Symmetrically, aj+1, aj+2, ...and an are all smaller than ai.

	 * Finally, during the movement of the pointers, either ai or aj 
	 * will be reached at first, so let's assume ai is reached before 
	 * aj. Then because aj+1, aj+2, ...and an are all smaller than ai, 
	 * so the right pointer will continue moving ( because the value 
	 * it points to is smaller ) until it reaches aj. Now the left 
	 * pointer is at ai and the right pointer is at aj.

	 * And if aj is reached before ai, the proof is also obvious.

	 * @param height
	 * @return
	 */
	public int maxArea(int[] height) {
		int maxArea = 0;
		int left = 0;
		int right = height.length - 1;
		while (left < right) {
			int minHeight = Math.min(height[left], height[right]);
			maxArea = Math.max(maxArea, (right - left) * minHeight);
			if (height[left] < height[right]) {
				left ++;
			} else {
				right--;
			}
		}
		return maxArea;
	}

	public static void main(String[] args) {
		int[] height = new int[] {1,2,5,34,35,12,43};
		LeetCode112 leetCode11 = new LeetCode112();
		System.out.println(leetCode11.maxArea(height));
	}
}
