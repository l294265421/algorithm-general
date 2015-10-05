package leetcode73;

import java.util.Arrays;

public class LeetCode73 {
    public void setZeroes(int[][] matrix) {
        if (matrix == null) {
			return;
		}
        int rowLen = matrix.length;
        if (rowLen == 0) {
			return;
		}
        int columnLen = matrix[0].length;
        int[] zeroColumn = new int[columnLen];
        Arrays.fill(zeroColumn, -1);
        for(int[] row : matrix) {
        	boolean isHaveZero = false;
        	for(int i = 0; i < columnLen; i++) {
        		if (row[i] == 0) {
					zeroColumn[i] = 0;
					isHaveZero = true;
				}
        	}
        	if (isHaveZero) {
				Arrays.fill(row, 0);
			}
        }
        
        // 把应该为0的列设置为0
        for(int i = 0; i < columnLen; i++) {
        	if (zeroColumn[i] == 0) {
				for(int j = 0; j < rowLen; j++) {
					matrix[j][i] = 0;
				}
			}
        }
    }
    
    public static void main(String[] args) {
		LeetCode73 leetCode73 = new LeetCode73();
		int[][] matrix = new int[][]{{1,2},{0, 1}};
		leetCode73.setZeroes(matrix);
		for(int[] row : matrix) {
			for(int num : row) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}
}
