package leetcode5;

public class LeetCode52 {
    public String longestPalindrome(String s) {
    	String longestPalindrome = "";
    	int maxLenth = 0;
    	
    	int len = s.length();
    	
    	boolean isFindLongest = false;
    	for(int i = 0; i < len; i++) {
    		char fore = s.charAt(i);
    		
    		// 从最后一个相同字符开始，倒转过来寻找
    		int backSearchStartIndex = len - 1;
    		while (true) {
    			int lastHindeIndex = s.lastIndexOf(fore, backSearchStartIndex);
    			
        		if (lastHindeIndex == i) {
    				break;
    			}
        		
        		backSearchStartIndex = lastHindeIndex - 1;
        		
	   			String candidate = s.substring(i, lastHindeIndex + 1);
    			if (isPalindromic(candidate)) {
					int length = lastHindeIndex - i + 1;
					if (length > maxLenth) {
						longestPalindrome = candidate;
						maxLenth = length;
						// 如果后面不可能得到更长的回文字符串，就说明已经找到最长的了
						if (maxLenth >= len - i) {
							isFindLongest = true;
						}
						// 以该字符开头的回文字符串不可能比现在得到这个更长
						continue;
					}
				}
			}
    		// 如果已经找到最长的，就不用再继续找了
    		if (isFindLongest) {
				break;
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
		LeetCode52 leetCode5 = new LeetCode52();
		long start = System.currentTimeMillis();
		leetCode5.longestPalindrome("321012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210123210012321001232100123210123");
		System.out.println(System.currentTimeMillis() - start);
	}
}
