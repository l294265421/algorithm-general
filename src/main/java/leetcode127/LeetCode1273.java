package leetcode127;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class LeetCode1273 {
	public int ladderLength(String beginWord, String endWord,
			Set<String> wordList) {
		Queue<String> helper = new LinkedList<>();
		helper.offer(beginWord);
		wordList.remove(beginWord);
		
		Map<String, Integer> distances = new HashMap<>();
		distances.put(beginWord, 1);
		
		wordList.add(endWord);
		
		while (!helper.isEmpty()) {
			String word = helper.poll();
			int distance = distances.get(word);
			if (word.equals(endWord)) {
				return distance;
			}
			
			StringBuilder wordStringBuilder = new StringBuilder(word);
			int len = wordStringBuilder.length();
			// 每次只改变单词中一个字符，查看是否这个词是否不被distances包含并且被字典包含
			for(int i = 0; i < len; i++) {
				char original = wordStringBuilder.charAt(i);
				for(char c = 'a'; c <= 'z'; c++) {
					if (c != original) {
						wordStringBuilder.setCharAt(i, c);
						String temp = wordStringBuilder.toString();
						if (wordList.contains(temp)) {
							helper.add(temp);
							distances.put(temp, distance + 1);
							wordList.remove(temp);
						}
					}
				}
				wordStringBuilder.setCharAt(i, original);
			}
		}
		
		return 0;
	}

	public static void main(String[] args) {
		LeetCode1273 leetCode127 = new LeetCode1273();
		String beginWord = "hit";
		String endWord = "cog";
		String[] temp = new String[] {"hot","dot","dog","lot","log"};
		Set<String> wordList = new HashSet<String>(Arrays.asList(temp));
		System.out.println(leetCode127.ladderLength(beginWord, endWord,
				wordList));
	}
}
