package eightqueens;

public class EightQueens3 {
	private int queenNum;
	// 位置索引表示行数，值表示列数
	private int[] queenArr;
	// 临时设置用于保存能成功摆放皇后的位置种数
	public int goodPositionNum;
	
	public EightQueens3() {
		this(8);
	}
	
	public EightQueens3(int queenNum) {
		queenNum = 8;
		queenArr = new int[queenNum];
	}
	
	/**
	 * 
	 * @param i 第几行
	 */
	public void trial(int i) {
		if (i == queenNum) {
			this.goodPositionNum++;
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
	 * 判断把皇后放在第
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
	
	public static void main(String[] args) {
		EightQueens3 eightQueens3 = new EightQueens3();
		eightQueens3.trial(0);
		System.out.println(eightQueens3.goodPositionNum);
	}
}
