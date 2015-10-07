package leetcode79;

import java.util.ArrayList;
import java.util.List;

public class LeetCode79 {
	private boolean isExist = false;
	public boolean exist(char[][] board, String word) {
		if (board == null || word == null) {
			return false;
		}
		int wordLen = word.length();
		if (wordLen == 0) {
			return false;
		}

		int rowNum = board.length;
		if (rowNum == 0) {
			return false;
		}
		int columnNum = board[0].length;

		List<Point> result = new ArrayList<Point>();
		for (int i = 0; i < wordLen; i++) {
			result.add(new Point(-1, -1));
		}
		char c = word.charAt(0);
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				char temp = board[i][j];
				if (temp == c) {
					Point point = new Point(i, j);
					result.set(0, point);
					nextPoint(point, board, rowNum - 1,
							columnNum - 1, result, word, 1);
				}
			}
		}
		return this.isExist;
	}

	public void nextPoint(Point now, char[][] board, int endx, int endy,
			List<Point> result, String word, int offset) {
		if (offset == word.length()) {
			this.isExist = true;
		}

		
		// 向上找
		if (now.getX() - 1 >= 0 && this.isExist == false) {
			int x = now.getX() - 1;
			int y = now.getY();
			if (board[x][y] == word.charAt(offset)) {
				Point point = new Point(x, y);
				boolean isValidCoordinate = true;
				for (int i = 0; i < offset; i++) {
					if (result.get(i).equals(point)) {
						isValidCoordinate = false;
					}
				}
				if (isValidCoordinate) {
					result.set(offset, point);
					nextPoint(point, board, endx, endy, result, word,
							offset + 1);
				}
			}
		}

		// 向下找
		if (now.getX() + 1 <= endx && this.isExist == false) {
			int x = now.getX() + 1;
			int y = now.getY();
			if (board[x][y] == word.charAt(offset)) {
				Point point = new Point(x, y);
				boolean isValidCoordinate = true;
				for (int i = 0; i < offset; i++) {
					if (result.get(i).equals(point)) {
						isValidCoordinate = false;
					}
				}
				if (isValidCoordinate) {
					result.set(offset, point);
					nextPoint(point, board, endx, endy, result, word,
							offset + 1);
				}
			}
		}

		// 向左找
		if (now.getY() - 1 >= 0 && this.isExist == false) {
			int x = now.getX();
			int y = now.getY() - 1;
			if (board[x][y] == word.charAt(offset)) {
				Point point = new Point(x, y);
				boolean isValidCoordinate = true;
				for (int i = 0; i < offset; i++) {
					if (result.get(i).equals(point)) {
						isValidCoordinate = false;
					}
				}
				if (isValidCoordinate) {
					result.set(offset, point);
					nextPoint(point, board, endx, endy, result, word,
							offset + 1);
				}
			}
		}

		// 向右找
		if (now.getY() + 1 <= endy && this.isExist == false) {
			int x = now.getX();
			int y = now.getY() + 1;
			if (board[x][y] == word.charAt(offset)) {
				Point point = new Point(x, y);
				boolean isValidCoordinate = true;
				for (int i = 0; i < offset; i++) {
					if (result.get(i).equals(point)) {
						isValidCoordinate = false;
					}
				}
				if (isValidCoordinate) {
					result.set(offset, point);
					nextPoint(point, board, endx, endy, result, word,
							offset + 1);
				}
			}
		}
	}

	public static class Point {
		private int x;
		private int y;

		public Point() {
		}

		public Point(int x, int y) {
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}

	}

	public static void main(String[] args) {
		LeetCode79 leetCode79 = new LeetCode79();
		char[][] board = new char[][] { { 'A', 'B', 'C', 'E' },
				{ 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } };
		System.out.println(leetCode79.exist(board, "ABCCED"));
	}
}
