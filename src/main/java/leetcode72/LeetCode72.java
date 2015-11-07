package leetcode72;

public class LeetCode72 {
	private int count = 0;
    public int minDistance(String word1, String word2) {
        int i = word1.length();
        int j = word2.length();
        int min = minDistance(word1, i, word2, j);
        return min;
    }
    
    /**
     * word1的前i个字符组成的字符串和word2的前j个字符组成的字符串的最短距离
     * @param word1
     * @param i
     * @param word2
     * @param j
     * @return
     */
    public int minDistance(String word1 , int i, String word2, int j) {
    	System.out.println(count++);
        if (i == 0 && j != 0) {
			return j;
		} else if (i != 0 && j == 0) {
			return j;
		} else if (i == 0 && j == 0) {
			return 0;
		} else {
			int min = -1;
			if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
				min = minDistance(word1, i - 1, word2, j - 1);
			} else {
				// 在word1上插入
				int minTemp1 = minDistance(word1, i, word2, j - 1) + 1;
				// 在word1上删除
				int minTemp2 = minDistance(word1, i - 1, word2, j) + 1;
				// 在word1上修改
				int minTemp3 = minDistance(word1, i - 1, word2, j - 1) + 1;
				min = Math.min(Math.min(minTemp1, minTemp2), minTemp3);
			}
			return min;
		}
    }
    
    public static void main(String[] args) {
		LeetCode72 leetCode72 = new LeetCode72();
		System.out.println(leetCode72.minDistance("dinitrop",
				"benzalphen"));
	}
}
