package leetcode113;

import global.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LeetCode113 {
	public List<List<Integer>> pathSum(TreeNode root, int sum) {
		List<List<Integer>> resultList = pathSum(root, sum,
				new ArrayList<Integer>());

		return resultList;
	}

	public List<List<Integer>> pathSum(TreeNode root, int sum,
			List<Integer> candidate) {
		List<List<Integer>> resultList = new ArrayList<List<Integer>>();
		if (root != null) {
			List<Integer> resultTemp = new ArrayList<Integer>(candidate);
			resultTemp.add(root.val);
			if (root.left == null && root.right == null) {
				if (sum == root.val) {
					resultList.add(resultTemp);
				}
			} else {
				List<List<Integer>> resultListLeftTemp = pathSum(root.left, sum
						- root.val, resultTemp);
				resultList.addAll(resultListLeftTemp);
				List<List<Integer>> resultListRightTemp = pathSum(root.right,
						sum - root.val, resultTemp);
				resultList.addAll(resultListRightTemp);

			}
		}
		return resultList;
	}

	public static void main(String[] args) {
		List<Integer> test = new ArrayList<Integer>();
		test.add(1);
		List<Integer> test1 = new ArrayList<Integer>(test);
		test1.set(0, 2);
		System.out.println(test);
		System.out.println(test1);
	}
}
