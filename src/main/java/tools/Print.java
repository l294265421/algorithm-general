package tools;

public class Print {
	/**
	 * 打印一个数组中的元素，元素用默认的分割符分开
	 * @param args
	 */
	public static <T> void printArray(T...args) {
		printArray(" ", args);
	}
	
	/**
	 * 打印一个数组中的元素，元素用指定的分割符分开
	 * @param delimiter
	 * @param args
	 */
	public static <T> void printArray(String delimiter, T...args) {
		int argNum = args.length;
		for (int i = 0; i < argNum; i++) {
			System.out.print(args[i].toString());
			if (i == argNum - 1) {
				System.out.println();
			} else {
				System.out.print(delimiter);
			}
		}
	}
	
	public static void main(String[] args) {
		Integer[] ints = new Integer[] {1,2};
		String[] strs = new String[]{"c", "d"};
		printArray("-",ints);
	}
}
