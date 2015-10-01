package leetcode56;

public class LeetCode56 {
    public int lengthOfLastWord(String s) {
        if (s == null) {
			return 0;
		}
        s = s.trim();
        
        int len = s.length();
        if (len == 0) {
			return 0;
		}
        
        if(s.indexOf(' ') == -1) {
        	return len;
        }
        
        String[] words = s.split(" ");
        return words[words.length - 1].length();
    }
    
    public static void main(String[] args) {
		LeetCode56 leetCode56 = new LeetCode56();
		System.out.println(leetCode56.lengthOfLastWord("Hello World"));
	}
}
