package leetcode4;

public class LeetCode4 {
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		// 假设nums1和nums2都不为null，元数个数不都为0
		int len1 = nums1.length;
		int len2 = nums2.length;
		
		int len3 = len1 + len2;
		int[] nums3 = new int[len3];
		if (len1 == 0) {
			nums3 = nums2;
		} else if (len2 == 0) {
			nums3 = nums1;
		} else {
			for(int i = 0; i < len1; i++) {
				nums3[i] = nums1[i];
			}
			merge(nums3, len1, nums2, len2);
		}
		
		if (len3 % 2 == 1) {
			return (double)nums3[len3 / 2];
		} else {
			return (double)(nums3[len3 / 2] + nums3[len3 / 2 - 1]) / 2;
		}
	}
	
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
		int[] nums1 = new int[] {1,2};
		int[] nums2 = new int[] {3,4};
		LeetCode4 leetCode4 = new LeetCode4();
		System.out.println(leetCode4.findMedianSortedArrays(nums1, nums2));
	}
}
