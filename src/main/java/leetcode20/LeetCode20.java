package leetcode20;

import java.util.Stack;

public class LeetCode20 {
	/**
	 * 当左右括号一样多，且完全匹配时，才返回true
	 * @param s
	 * @return
	 */
    public boolean isValid(String s) {
    	int len = s.length();
    	if (len < 2) {
			return false;
		}
        Stack<Character> lefts = new Stack<Character>();
        for(int i = 0; i < len; i++) {
        	Character c = s.charAt(i);
        	if (isLeft(c)) {
				lefts.push(c);
			} else {
				//需要做括号来匹配
				// lefts不能为空
				if (lefts.isEmpty()) {
					return false;
				}
				
				Character left = lefts.pop();
				// 判断应匹配的左右括号是否匹配
				if (isPair(left, c)) {
					continue;
				} else {
					return false;
				}
			}
        }
        // 判断左右括号是否一样多
        if (lefts.isEmpty()) {
			return true;
		}
        return false;
    }
    
    /**
     * 判断一个括号是否是左括号
     * @param bracket
     * @return
     */
    public boolean isLeft(Character bracket) {
		if (bracket.equals('(') || bracket.equals('[')
				 || bracket.equals('{')) {
			return true;
		}
		return false;
	}
    
    /**
     * 判断左右括号是否匹配
     * @param left
     * @param right
     * @return
     */
    public boolean isPair(Character left, Character right) {
		if ((left.equals('(') && right.equals(')')) 
				|| (left.equals('[') && right.equals(']'))
				|| (left.equals('{') && right.equals('}')) ) {
			return true;
		}
		return false;
	}
    
    public static void main(String[] args) {
		LeetCode20 leetCode20 = new LeetCode20();
		System.out.println(leetCode20.isValid("([()]"));
	}
}
