package leetcode88;

public class LeetCode88 {
	public void merge(int[] nums1, int m, int[] nums2, int n) {
		// 这个插入元素必须插在上一个元素的后面
		int lastInsertPosition = -1;
		for(int i = 0; i < n; i++) {
			int temp = nums2[i];
			// 寻找插入位置
			int thisInsertPosition = -1;
			for(int j = lastInsertPosition + 1; j < m; j++) {
				if (nums1[j] > temp) {
					thisInsertPosition = j;
					break;
				}
			}
			// 插入元素
			if (thisInsertPosition == -1) {
				thisInsertPosition = m;
				nums1[m] = temp;
				m++;
			} else {
				// 右移元素
				for(int k = m - 1; k >= thisInsertPosition; k--) {
					nums1[k + 1] = nums1[k];
				}
				nums1[thisInsertPosition] = temp;
				m++;
			}
			lastInsertPosition = thisInsertPosition;
		}
	}
	
	public static void main(String[] args) {
		LeetCode88 leetCode88 = new LeetCode88();
		int[] nums1 = new int[] {1,3,5,0,0,0};
		int[] nums2 = new int[] {2,4,6};
		leetCode88.merge(nums1, 3, nums2, 3);
		for(int num : nums1) {
			System.out.println(num);
		}
	}
}
