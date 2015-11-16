package leetcode130;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 核心思想：寻找O区域，判定其中是否有边界元素，有的话，
 * 就把所有O变为X，否则什么都不做。
 * @author yuncong
 *
 */
public class LeetCode130 {
	public void solve(char[][] board) {
		int yLen = board.length;
		int xLen = 0;
		if (yLen != 0) {
			xLen = board[0].length;
		} else {
			return;
		}

		List<HashSet<Coordinate>> alreadyCheck = new LinkedList<>();
		for(int i = 0; i <= xLen; i++) {
			for(int j = 0; j <= yLen; j++) {
				if (board[i][j] == 'O') {
					Coordinate coordinate = new Coordinate(i, j);
					for(HashSet<Coordinate> temp : alreadyCheck) {
						if (temp.contains(coordinate)) {
							break;
						}
					}
					boolean isConvert = false;
					Set<Coordinate> next = new HashSet<>();
					LinkedList<Coordinate> queue = new LinkedList<>();
					queue.add(coordinate);
					// 只需要向右和向下寻找，寻找那些已经确定是同一类元素的邻居
					while (!queue.isEmpty()) {
						Coordinate center = queue.pop();
						Coordinate right = center.right();
						if (right.x < xLen && board[right.x][right.y] == 'O') {
							boolean isNotContain = next.add(right);
							if (isNotContain) {
								queue.add(right);
								if (!isConvert && right.y == yLen - 1) {
									isConvert = true;
								}
							}
						}
					}
					
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
		
		public Coordinate down() {
			return new Coordinate(x + 1, y);
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
			
			boolean result = this.x == otherCoordinate.x &&
							 this.y == otherCoordinate.y;
			return result;
		}
		
		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + this.x;
			result = 31 * result + this.y;
			return result;
		}

	}

	public static void main(String[] args) {
	}

}
