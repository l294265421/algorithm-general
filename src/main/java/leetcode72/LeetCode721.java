package leetcode72;

public class LeetCode721 {
    public int minDistance(String word1, String word2) {
        if (word2.length() == 0) return word1.length();  
        
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
        return distance[word1.length()][word2.length()]; 
    }
    
       public static void main(String[] args) {
		LeetCode721 leetCode721 = new LeetCode721();
		System.out.println(leetCode721.minDistance("dinitrop",
				"benzalphen"));
	}
}
