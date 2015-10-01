package leetcode59;


public class LeetCode59 {
	private int currentValue = 1;
    public int[][] generateMatrix(int n) {
    	int[][] matrix = new int[n][n];
    	if (n == 0) {
			return matrix;
		}
		int minColumn = 0;
		int maxColumn = n - 1;
		int minRow = 0;
		int maxRow = n - 1;
		while (true) {
			oneAssign(matrix, minColumn, maxColumn, minRow, maxRow);
			if (maxColumn - minColumn < 2 || maxRow - minRow < 2) {
				break;
			}
			minColumn++;
			maxColumn--;
			minRow++;
			maxRow--;
		}
		return matrix;
    }
    
    /**
	 * @param matrix
	 * @return
	 */
	public void oneAssign(int[][] matrix, int minColumn,
			int maxColumn, int minRow, int maxRow) {
		// 只有一行
		if(maxRow == minRow) {
			for(int i = minColumn; i <= maxColumn; i++) {
				matrix[minRow][i]= this.currentValue;
				this.currentValue++;
			}
			return;
		}
		
		//只有一列
		if(maxColumn == minColumn) {
			for(int i = minRow; i <= maxRow; i++) {
				matrix[i][minColumn] = this.currentValue;
				this.currentValue++;
			}
			return;
		}
		
		// 第一行
		for (int i = minColumn; i <= maxColumn; i++) {
			matrix[minRow][i] = this.currentValue;
			this.currentValue++;
		}
		
		// 最后一列
		for (int i = minRow + 1; i <= maxRow; i++) {
			matrix[i][maxColumn] = this.currentValue;
			this.currentValue++;
		}

		// 最后一行
		for (int i = maxColumn - 1; i >= minColumn; i--) {
			matrix[maxRow][i] = this.currentValue;
			this.currentValue++;
		}

		// 第一列
		for (int i = maxRow - 1; i > minRow; i--) {
			matrix[i][minColumn] = this.currentValue;
			this.currentValue++;
		}
	}
	
	public static void main(String[] args) {
		LeetCode59 leetCode59 = new LeetCode59();
		int[][] matrix = leetCode59.generateMatrix(0);
		for(int[] row : matrix) {
			for(int num : row) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
		// 容量为0的数组
		int[] test = new int[0];
	}
}
