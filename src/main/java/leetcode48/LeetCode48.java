package leetcode48;

public class LeetCode48 {
    public void rotate(int[][] matrix) {
    	if (matrix == null) {
			return;
		}
    	int rowNum = matrix.length;
    	if (rowNum == 0) {
			return;
		}
    	int columnNum = matrix[0].length;
    	int[][] afterRotate = new int[rowNum][columnNum];
    	for(int rrow = 0; rrow < rowNum; rrow++) {
    		int[] temp = matrix[rrow];
    		int acolumn = rowNum - 1 - rrow;
    		for(int arow = 0; arow < rowNum; arow++) {
    			afterRotate[arow][acolumn] = temp[arow];
    		}
    	}
    	
    	for(int row = 0; row < rowNum; row++) {
    		for(int column = 0; column < columnNum; column++) {
    			matrix[row][column] = afterRotate[row][column];
    		}
    	}
    }
    
    public static void main(String[] args) {
    	LeetCode48 leetCode48 = new LeetCode48();
    	int[][] matrix = new int[][] {{1,2},{3,4}};
    	leetCode48.rotate(matrix);
    	for(int row = 0; row < matrix.length; row++) {
    		for(int column = 0; column < matrix.length; column++) {
    			System.out.println(matrix[row][column]);
    		}
    	}
	}
}
