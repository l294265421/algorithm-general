package leetcode22;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定括号对数量，生成所有合理组合：
 * 任何一个位置，要么(,要么)，那在
 * 某一个位置可以是哪一个呢或都可以呢？
 * 标准是：如果剩下的(的个数大于0，则
 * 可以是（;如果剩下的)的个数大于0且比(
 * 的个数多，则可以是)。
 * 我的疑问是：正确的序列中，任何从头到
 * 某一个位置的子序列中，左括号的个数大于等于
 * 右括号的个数；但是一个字符串，任何从头到某一
 * 个位置的子序列都满足左括号的个数大于等于
 * 右括号的个数，就是合法括号序列吗？
 * 
 * 这是一个回溯算法实现：遍历所有情况，如果一直是
 * 左括号个数大于等于右括号个数，则输出最终结果；
 * 否则，裁剪掉当前结果。（对比八皇后问题）
 * @author liyuncong
 *
 */
public class LeetCode22 {
	public List<String> generateParenthesis(int n) {
	    List<String> list = new ArrayList<String>();
	    generateOneByOne("", list, n, n);
	    return list;
	}
	/**
	 * 只是保证每一个位置放置括号之后，字符串中左括号的个数大于等于
	 * 右括号的个数
	 * @param sublist
	 * @param list
	 * @param left
	 * @param right
	 */
	public void generateOneByOne(String sublist, List<String> list, int left, int right){
	    // 剩下的)数量必须大于等于(数量
		if(left > right){
	        return;
	    }
	    
	    if(left > 0){
	        generateOneByOne( sublist + "(" , list, left-1, right);
	    }
	    
	    if(right > 0){
	        generateOneByOne( sublist + ")" , list, left, right-1);
	    }
	    if(left == 0 && right == 0){
	        list.add(sublist);
	        return;
	    }
	}
	public static void main(String[] args) {
		LeetCode22 leetCode22 = new LeetCode22();
		System.out.println(leetCode22.generateParenthesis(2));
	}

}
