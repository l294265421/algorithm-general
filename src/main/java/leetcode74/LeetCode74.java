package leetcode74;

public class LeetCode74 {
	public boolean searchMatrix(int[][] matrix, int target) {
		 if (matrix == null) {
				return false;
			}
	        int rowLen = matrix.length;
	        if (rowLen == 0) {
				return false;
			}
	        int columnLen = matrix[0].length;
	        
	        int targetRowIndex = -1;
	        for(int i = 0; i < rowLen; i++) {
	        	if (matrix[i][0] == target) {
					return true;
				} else if (matrix[i][0] > target) {
					if (i == 0) {
						return false;
					} else {
						targetRowIndex = i - 1;
						break;
					}
				}
	        }
	        if (targetRowIndex == -1) {
	        	targetRowIndex = rowLen - 1;
			}
	        
	        int[] targetRow = matrix[targetRowIndex];
	        return binarySearch(targetRow, target, 0, columnLen - 1);
	}
	
	public boolean binarySearch(int[] array, int target, 
			int left, int right) {
		if (left > right) {
			return false;
		}
		int mid = (left + right) / 2;
		if (target == array[mid]) {
			return true;
		} else if (target > array[mid]) {
			return binarySearch(array, target, mid + 1, right);
		} else {
			return binarySearch(array, target, left, mid - 1);
		}
	}
	
	public static void main(String[] args) {
		LeetCode74 leetCode74 = new LeetCode74();
		int[] array = new int[] {1,2,3};
		System.out.println(leetCode74.binarySearch(array, 0, 0, array.length - 1));
	}
}
