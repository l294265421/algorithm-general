package leetcode5;

public class LeetCode51 {
    public String longestPalindrome(String s) {
    	String longestPalindrome = "";
    	int maxLenth = 0;
    	
    	int len = s.length();
    	for(int i = 0; i < len; i++) {
    		char fore = s.charAt(i);
    		
    		int lastHindeIndex = i + 1;
    		while (true) {
				int index = s.indexOf(fore, lastHindeIndex);
				if (index == -1) {
					break;
				}
				
				lastHindeIndex = index + 1;
	   			String candidate = s.substring(i, lastHindeIndex);
    			if (isPalindromic(candidate)) {
					int length = lastHindeIndex - i;
					if (length > maxLenth) {
						longestPalindrome = candidate;
						maxLenth = length;
						// 如果后面不可能得到更长的回文字符串，就退出
						if (maxLenth >= len - i) {
							break;
						}
					}
				}
			}
    		
    	}
    	
        return longestPalindrome;
    }
    
	public boolean isPalindromic(String s) {
		if (s == null || s.equals("")) {
			return false;
		}
		
		int len = s.length();
		int bound = len / 2;
		for(int i = 0; i < bound; i++) {
			char fore = s.charAt(i);
			char hind = s.charAt(len - 1 - i);
			if (fore != hind) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		LeetCode51 leetCode5 = new LeetCode51();
		long start = System.currentTimeMillis();
		leetCode5.longestPalindrome("321012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210123210012321001232100123210123");
		System.out.println(System.currentTimeMillis() - start);
	}
}
