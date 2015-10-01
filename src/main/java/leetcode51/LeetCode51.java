package leetcode51;

import java.util.ArrayList;
import java.util.List;

public class LeetCode51 {
	private int queenNum;
	// 位置索引表示行数，值表示列数
	private int[] queenArr;
	// 临时设置用于保存能成功摆放皇后的位置种数
	private List<List<String>> goodPositionList;
	
    public List<List<String>> solveNQueens(int n) {
		this.queenNum = n;
		queenArr = new int[queenNum];
		for (int i = 0; i < queenNum; i++) {
			queenArr[i] = -1;
		}
		this.goodPositionList = new ArrayList<List<String>>();
		// 从第0行开始摆放皇后
		trial(0);
		return this.goodPositionList;
    }
	
    /**
	 * 
	 * @param i 第几行
	 */
	public void trial(int i) {
		if (i == queenNum) {
			List<String> goodPosition = new ArrayList<String>(queenNum);
			for(int j = 0; j < queenNum; j++) {
				String rowStr = generateRow(queenArr[j]);
				goodPosition.add(j, rowStr);
			}
			goodPositionList.add(goodPosition);
		} else {
			for(int j = 0; j < this.queenNum; j++) {
				if (this.judge(i, j)) {
					this.queenArr[i] = j;
					trial(i + 1);
				} else {
					continue;
				}
			}
		}
	}
	
	/**
	 * 判断把皇后放在第i行第j列是否安全；是通过
	 * 前面第i行前面各行中的皇后的摆放位置
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean judge(int i, int j) {
		// 判断在地j列是否已经摆放过皇后
		for(int k = 0; k < i; k++) {
			if (queenArr[k] == j) {
				return false;
			}
		}
		
		// 判断正对角线上是否已经摆放过皇后 
		for(int k = 0; k < i; k++) {
			if ((k + queenArr[k]) == (i + j)) {
				return false;
			}
		}
		
		// 判断反对角线上是否已经摆放了皇后
		for(int k = 0; k < i; k++) {
			if ((k - queenArr[k]) == (i - j)) {
				return false;
			}
		}
		
		return true;
	}
	
    /**
     * 生成某一行的字符串
     * @param column
     * @return
     */
    public String generateRow(int column) {
		StringBuffer rowStr = new StringBuffer();
		for(int i = 0; i < this.queenNum; i++) {
			if (i == column) {
				rowStr.append("Q");
			} else {
				rowStr.append(".");
			}
		}
		return rowStr.toString();
	}
    
    public static void main(String[] args) {
		LeetCode51 leetCode51 = new LeetCode51();
		System.out.println(leetCode51.solveNQueens(4));
	}
}
