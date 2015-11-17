package leetcode130;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 核心思想：寻找O区域，判定其中是否有边界元素，有的话， 就把所有O变为X，否则什么都不做。
 * 
 * @author yuncong
 *
 */
public class LeetCode130 {
	public void solve(char[][] board) {
		int rowNum = board.length;
		int columnNum = 0;
		if (rowNum != 0) {
			columnNum = board[0].length;
		} else {
			return;
		}

		List<Set<Coordinate>> alreadyCheck = new LinkedList<>();
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				if (board[i][j] == 'O') {
					Coordinate startPoint = new Coordinate(i, j);
					boolean isChecked = false;
					for (Set<Coordinate> temp : alreadyCheck) {
						if (temp.contains(startPoint)) {
							isChecked = true;
							break;
						}
					}
					if (isChecked) {
						continue;
					}
					
					boolean isConvert = true;
					if (startPoint.x == rowNum - 1
							|| startPoint.y == columnNum - 1
							|| startPoint.x == 0 || startPoint.y == 0) {
						isConvert = false;
					}

					Set<Coordinate> next = new HashSet<>();
					next.add(startPoint);
					LinkedList<Coordinate> queue = new LinkedList<>();
					queue.add(startPoint);
					// 只需要向右和向下寻找，寻找那些已经确定是同一类元素的邻居
					while (!queue.isEmpty()) {
						Coordinate center = queue.pop();
						Coordinate right = center.right();
						if (right.y < columnNum
								&& board[right.x][right.y] == 'O') {
							boolean isNotContain = next.add(right);
							if (isNotContain) {
								queue.add(right);
								if (isConvert && right.y == columnNum - 1) {
									isConvert = false;
								}
							}
						}
						
						Coordinate left = center.left();
						if (left.y >= 0
								&& board[left.x][left.y] == 'O') {
							boolean isNotContain = next.add(left);
							if (isNotContain) {
								queue.add(left);
								if (isConvert && left.y == 0) {
									isConvert = false;
								}
							}
						}

						Coordinate down = center.down();
						if (down.x < rowNum && board[down.x][down.y] == 'O') {
							boolean isNotContain = next.add(down);
							if (isNotContain) {
								queue.add(down);
								if (isConvert && down.x == rowNum - 1) {
									isConvert = false;
								}
							}
						}
						
						Coordinate up = center.up();
						if (up.x >= 0 && board[up.x][up.y] == 'O') {
							boolean isNotContain = next.add(up);
							if (isNotContain) {
								queue.add(up);
								if (isConvert && up.x == 0) {
									isConvert = false;
								}
							}
						}
					}
					

					if (isConvert) {
						for (Coordinate coordinate2 : next) {
							board[coordinate2.x][coordinate2.y] = 'X';
						}
					}
					alreadyCheck.add(next);

				}
			}
		}
	}

	private static class Coordinate {
		// 原点在左上角
		// 行
		private int x;
		// 列
		private int y;

		public Coordinate(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public Coordinate right() {
			return new Coordinate(x, y + 1);
		}
		
		public Coordinate left() {
			return new Coordinate(x, y - 1);
		}

		public Coordinate down() {
			return new Coordinate(x + 1, y);
		}
		
		public Coordinate up() {
			return new Coordinate(x - 1, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof Coordinate)) {
				return false;
			}

			Coordinate otherCoordinate = (Coordinate) obj;

			boolean result = this.x == otherCoordinate.x
					&& this.y == otherCoordinate.y;
			return result;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + this.x;
			result = 31 * result + this.y;
			return result;
		}

		@Override
		public String toString() {
			return "Coordinate [x=" + x + ", y=" + y + "]";
		}

	}
	
	public char[][] generateInput(String input) {
		input = input.substring(1, input.length() - 1);
		String[] inputArr = input.split(",");
		int rowNum = inputArr.length;
		int columnNum = inputArr[0].length() - 2;
		char[][] output = new char[rowNum][columnNum];
		for (int i = 0; i < rowNum; i++) {
			for(int j = 1; j < columnNum + 1; j++) {
				char temp = inputArr[i].charAt(j);
				output[i][j - 1] = temp;
			}
		}
		
		for (char[] cs : output) {
			for (char c : cs) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
		System.out.println();
		
		return output;
	}

	public static void main(String[] args) {
		LeetCode130 leetCode130 = new LeetCode130();
		char[][] test = leetCode130.generateInput("[\"OXOOXX\",\"OXXXOX\",\"XOOXOO\",\"XOXXXX\",\"OOXOXX\",\"XXOOOO\"]");
		leetCode130.solve(test);
		for (char[] cs : test) {
			for (char c : cs) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
	}

}
