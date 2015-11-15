package leetcode10;

/**
 * 核心思想：一段s匹配一段p，匹配成功了再匹配下一段；
 * 能都达到终点的才算匹配成功；s已经结束了，p和没有
 * 结束，可能成功；s还没有结束，p已经结束，失败
 * @author yuncong
 *
 */
public class LeetCode10 {
    public boolean isMatch(String s, String p) {
        int sl = s.length();
        int pl = p.length();
        boolean isMatch = false;
        if (sl == 0 && pl == 0) {
			isMatch = true;
		} else {
			isMatch = isMatch(s, sl, 0, p, pl, 0);
		}
        
        return isMatch;
    }
   
    /**
     * 
     * @param s 待匹配字符串
     * @param sl s的长度
     * @param i 即将被匹配的字符的索引
     * @param p 正则表达式
     * @param pl p的长度
     * @param j 即将用来匹配的正则表达式局部的索引
     * @return
     */
    public boolean isMatch(String s, int sl,  int i, String p, int pl, int j) {
    	// 当 i 和 j刚好同时达到终点，匹配成功
        if (i == sl && j == pl) {
			return true;
		}
        // p已经结束，但是s还没有匹配完，就匹配失败
        if (i != sl && j == pl) {
			return false;
		}
        
        boolean isMatch = true;
        
        char pChar = p.charAt(j);
        boolean isFollowStar = false;
        if (j + 1 < pl && p.charAt(j + 1) == '*') {
			isFollowStar = true;
		}
        
        if (isFollowStar) {
			// 这里可以匹配0个或者多个pChar，优先匹配最多个
        	int equalPCharNum = 0;
        	// 查看从i开始有多少个pChar
        	while (equalPCharNum + i < sl && (s.charAt(equalPCharNum + i) == pChar
        			|| pChar == '.')) {
				equalPCharNum++;
			}
        	// 匹配所有
        	isMatch = isMatch(s, sl, i + equalPCharNum, p, pl, j + 2);
        	for(int k = equalPCharNum - 1; k >= 0; k--) {
        		isMatch = isMatch || isMatch(s, sl, i + k, p, pl, j + 2);
        	}
		} else {
			// 这一点是否匹配
			boolean isThereMatch = false;
			if (i < sl && (pChar == s.charAt(i) || pChar == '.')) {
				isThereMatch = true;
			}
			// 这一点匹配了才有必要继续匹配后面的
			if (isThereMatch) {
				isMatch = isMatch(s, sl, i + 1, p, pl, j + 1);
			} else {
				isMatch = false;
			}
		}
        return isMatch;
    }
	public static void main(String[] args) {
		LeetCode10 leetCode10 = new LeetCode10();
		System.out.println(leetCode10.isMatch("aa", ".*"));
	}

}
