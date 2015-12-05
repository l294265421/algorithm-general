package tools;

public final class Printer {
	
	/**
	 * 打印一个数组中的元素，元素用指定的分割符分开
	 * @param delimiter 分割数组中元素的分隔符
	 * @param array 一维数组
	 */
	public static void printArray(String delimiter, int...array) {
		int argNum = array.length;
		for (int i = 0; i < argNum; i++) {
			System.out.print(array[i]);
			if (i == argNum - 1) {
				System.out.println();
			} else {
				System.out.print(delimiter);
			}
		}
	}
	
	/**
	 * 打印一个数组中的元素，元素用默认的分割符(空格)分开
	 * @param array 一维数组
	 */
	public static void printArray(int...array) {
		printArray(" ", array);
	}
	
	/**
	 * 打印一个数组中的元素，元素用指定的分割符分开
	 * @param delimiter 分割数组中元素的分隔符
	 * @param array 一维数组
	 */
	public static <T> void printArray(String delimiter, T...array) {
		int argNum = array.length;
		for (int i = 0; i < argNum; i++) {
			System.out.print(array[i].toString());
			if (i == argNum - 1) {
				System.out.println();
			} else {
				System.out.print(delimiter);
			}
		}
	}
	
	/**
	 * 打印一个数组中的元素，元素用默认的分割符(空格)分开
	 * @param array 一维数组
	 */
	public static <T> void printArray(T...array) {
		printArray(" ", array);
	}
	
	/**
	 * 打印一个二维数组，元素用指定分隔符分开
	 * @param delimiter 分割数组中元素的分隔符
	 * @param twodArray 二维数组
	 */
	public static void print2DArray(String delimiter, int[][] twodArray) {
		for (int[] is : twodArray) {
			printArray(delimiter, is);
		}
	}
	
	/**
	 * 打印一个二维数组，元素用默认的分割符(空格)分开
	 * @param twodArray 二维数组
	 */
	public static void print2DArray(int[][] twodArray) {
		print2DArray(" ", twodArray);
	}
	
	public static void main(String[] array) {
		Integer[] ints = new Integer[] {1,2};
		String[] strs = new String[]{"c", "d"};
		printArray("-",ints);
	}
}
