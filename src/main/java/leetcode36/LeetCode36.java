package leetcode36;

import java.util.Arrays;

public class LeetCode36 {
    public boolean isValidSudoku(char[][] board) {
        // 验证行
    	for(int row = 0; row < 9; row++) {
    		char[] part = new char[9];
    		for(int column = 0; column < 9; column++) {
    			part[column] = board[row][column];
    		}
    		if (!isValidPart(part)) {
				return false;
			}
    	}
    	
    	// 验证列
    	for(int column = 0; column < 9; column++) {
    		char[] part = new char[9];
    		for(int row = 0; row < 9; row++) {
    			part[row] = board[row][column];
    		}
    		if (!isValidPart(part)) {
				return false;
			}
    	}
    	
    	// 验证九宫格
    	// i j k对应行数
    	for(int i = 0, j = 1, k = 2; i < 9; i+=3, j+=3, k+=3) {
    		// l对应列数
    		for(int l = 0; l < 9; l+=3) {
    			char[] part = new char[9];
    			int count = 0;
    			// m代表实际取的列数
    			for(int m = l; m < l + 3; m++) {
    				part[count] = board[i][m];
    				count++;
    				part[count] = board[j][m];
    				count++;
    				part[count] = board[k][m];
    				count++;
    			}
    			if (!isValidPart(part)) {
					return false;
				}
    		}
    	}
    	return true;
    }
    
    public boolean isValidPart(char[] part) {
		Arrays.sort(part);
		// .的值比1小
		for(int i = 0; i < 8; i++) {
			if (part[i] == '.') {
				continue;
			}
			if (part[i] == part[i+1]) {
				return false;
			}
		}
		return true;
	}
    
    public char[] transfer(String s) {
		char[] test = new char[9];
		for(int i = 0; i < s.length(); i++) {
			test[i] = s.charAt(i);
		}
		return test;
	}
    
    public static void main(String[] args) {
    	LeetCode36 leetCode36 = new LeetCode36();
    	char[][] board = new char[9][9];
    	String[] boardStr = new String[]{".87654321","2........","3........","4........","5........","6........","7........","8........","9........"};
    	for(int i = 0; i < 9; i++) {
    		board[i] = leetCode36.transfer(boardStr[i]);
    	}
    	System.out.println(leetCode36.isValidSudoku(board));
	}
    
}
