package eightqueens;

public class EightQueens {
	static final int QueenMax = 8;
	static int oktimes = 0;
	static int chess[] = new int[QueenMax];// 每一个Queen的放置位置

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		for (int i = 0; i < QueenMax; i++)
			chess[i] = -1;
		placequeen(0);
		System.out.println("\n\n\n八皇后共有" + oktimes + "个解法 made by yifi 2003");
		System.out.println((System.currentTimeMillis() - time) + "毫秒");
	}

	public static void placequeen(int num) { // num 为现在要放置的行数
		int i = 0;
		boolean qsave[] = new boolean[QueenMax];
		for (; i < QueenMax; i++)
			qsave[i] = true;

		// 下面先把安全位数组完成
		i = 0;// i 是现在要检查的数组值
		while (i < num) {
			qsave[chess[i]] = false;
			int k = num - i;
			if ((chess[i] + k >= 0) && (chess[i] + k < QueenMax))
				qsave[chess[i] + k] = false;
			if ((chess[i] - k >= 0) && (chess[i] - k < QueenMax))
				qsave[chess[i] - k] = false;
			i++;
		}
		// 下面历遍安全位
		for (i = 0; i < QueenMax; i++) {
			if (qsave[i] == false)
				continue;
			if (num < QueenMax - 1) {
				chess[num] = i;
				placequeen(num + 1);
			} else { // num is last one
				chess[num] = i;
				oktimes++;
				System.out.println("这是第" + oktimes + "个解法 如下:");
				System.out.println("第n行: 1 2 3 4 5 6 7 8");

				for (i = 0; i < QueenMax; i++) {
					String row = "第" + (i + 1) + "行: ";
					if (chess[i] == 0)
						;
					else
						for (int j = 0; j < chess[i]; j++)
							row += "--";
					row += "++";
					int j = chess[i];
					while (j < QueenMax - 1) {
						row += "--";
						j++;
					}
					System.out.println(row);
				}
			}
		}
		// 历遍完成就停止
	}
	
}
