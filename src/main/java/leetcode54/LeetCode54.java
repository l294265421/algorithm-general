package leetcode54;

import java.util.ArrayList;
import java.util.List;

public class LeetCode54 {
	public List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> result = new ArrayList<Integer>();

		if (matrix == null) {
			return result;
		}
		int rowLen = matrix.length;
		if (rowLen == 0) {
			return result;
		}
		int columnLen = matrix[0].length;

		int minColumn = 0;
		int maxColumn = columnLen - 1;
		int minRow = 0;
		int maxRow = rowLen - 1;
		while (true) {
			oneCollect(matrix, result, minColumn, maxColumn, minRow, maxRow);
			if (maxColumn - minColumn < 2 || maxRow - minRow < 2) {
				break;
			}
			minColumn++;
			maxColumn--;
			minRow++;
			maxRow--;
		}
		return result;
	}

	/**
	 * @param matrix
	 * @return
	 */
	public void oneCollect(int[][] matrix, List<Integer> result, int minColumn,
			int maxColumn, int minRow, int maxRow) {
		// 只有一行
		if(maxRow == minRow) {
			for(int i = minColumn; i <= maxColumn; i++) {
				result.add(matrix[minRow][i]);
			}
			return;
		}
		
		//只有一列
		if(maxColumn == minColumn) {
			for(int i = minRow; i <= maxRow; i++) {
				result.add(matrix[i][minColumn]);
			}
			return;
		}
		
		// 第一行
		for (int i = minColumn; i <= maxColumn; i++) {
			result.add(matrix[minRow][i]);
		}
		
		// 最后一列
		for (int i = minRow + 1; i <= maxRow; i++) {
			result.add(matrix[i][maxColumn]);
		}

		// 最后一行
		for (int i = maxColumn - 1; i >= minColumn; i--) {
			result.add(matrix[maxRow][i]);
		}

		// 第一列
		for (int i = maxRow - 1; i > minRow; i--) {
			result.add(matrix[i][minColumn]);
		}
	}

	public static void main(String[] args) {
		LeetCode54 leetCode54 = new LeetCode54();
//		int[][] matrix = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		int[][] matrix = new int[][] { { 3 }, { 2 }};
		System.out.println(leetCode54.spiralOrder(matrix));
	}
}
