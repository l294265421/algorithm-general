package levenshteinDistance;

import java.util.LinkedList;
import java.util.List;

public class LevenshteinDistance {
	
	/**
	 * 返回一个矩阵，矩阵的元素ij，表示word1的前i个元素到word2的前j个元素的
	 * 最短编辑距离。
	 * @param word1 字符串1
	 * @param word2 字符串2
	 * @return
	 */
    private static int[][] getMinDistanceMatrix(String word1, String word2) {
        // for all i and j, d[i,j] will hold the Levenshtein distance between  
        // the first i characters of s and the first j characters of t;  
        // note that d has (m+1)x(n+1) values  
        int[][] distance = new int[word1.length() + 1][word2.length() + 1];  
        // the distance of any first string to an empty second string
        for (int i = 0; i <= word1.length(); i++) {  
            distance[i][0] = i;  
        }  
        // the distance of any second string to an empty first string
        for (int i = 0; i <= word2.length(); i++) {  
            distance[0][i] = i;  
        }  
          
        for (int i = 1; i <= word1.length(); i++) {  
            for (int j = 1; j <= word2.length(); j++) {  
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {  
                    distance[i][j] = distance[i-1][j-1];  
                } else {  
                    distance[i][j] = Math.min(Math.min(distance[i-1][j], distance[i][j-1]), distance[i-1][j-1]) + 1;  
                }  
            }  
        }  
        return distance; 
    }
    
    /**
     * 获得一条最短编辑路径（最短编辑路径可能存在多条）
     * @param word1
     * @param word2
     * @param distance
     * @return
     */
    public static EditPath getOnePath(String word1, String word2, int[][] distance) {
		List<Station> stationList = new LinkedList<Station>();
		int i = word1.length();
		int j = word2.length();
		stationList.add(new Station(i, j));
		while(i > 0 || j > 0) {
			char char1 = word1.charAt(i - 1);
			char char2 = word2.charAt(j - 1);
			if (char1 == char2) {
				i = i - 1;
				j = j - 1;
				stationList.add(new Station(i, j));
			} else {
				int thisDistance = distance[i][j];
				
				// 删除（不为空才能删出）
				int up = distance[i - 1][j];
				if (up + 1 == thisDistance && i > 0) {
					i = i - 1;
					stationList.add(new Station(i, j));
					continue;
				}
				
				// 插入（word2不为空才需要插入）
				int left = distance[i][j - 1];
				if (left + 1 == thisDistance && j > 0) {
					j = j - 1;
					stationList.add(new Station(i, j));
					continue;
				}
				
				// 修改(word1和word2都不为空才需要修改)
				int leftUp = distance[i - 1][j - 1];
				if (leftUp + 1 == thisDistance && i > 0 && j > 0) {
					i = i - 1;
					j = j - 1;
					stationList.add(new Station(i, j));
				}
			}
		}
		EditPath editPath = new EditPath(stationList);
		return editPath;
	}
    
	
	public static void main(String[] args) {
//		int[][] test = new int[][] {{1,2,3},{4,5,6}};
//		System.out.println(test[1][2]);
		
		String word1 = "XGYXYXYX";
		String word2 = "XYXYXYTX";
		int[][] test = LevenshteinDistance.getMinDistanceMatrix(word1, word2);
		for (int[] is : test) {
			for (int i : is) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
		System.out.println(LevenshteinDistance.getOnePath(word1, word2, test));
	}
}
