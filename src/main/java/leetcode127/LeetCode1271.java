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

public class LeetCode1271 {
	public int ladderLength(String beginWord, String endWord,
			Set<String> wordList) {
		Queue<String> helper = new LinkedList<>();
		helper.offer(beginWord);
		
		Map<String, Integer> distances = new HashMap<>();
		distances.put(beginWord, 1);
		
		wordList.add(endWord);
		
		while (!helper.isEmpty()) {
			String word = helper.poll();
			int distance = distances.get(word);
			if (word.equals(endWord)) {
				return distance;
			}
			
			for(String wordInDic : wordList) {
				if (!distances.containsKey(wordInDic) && isAdjacent(word, wordInDic)) {
					helper.offer(wordInDic);
					distances.put(wordInDic, distance + 1);
				}
			}
		}
		
		return 0;
	}

	private boolean isAdjacent(String word1, String word2) {
		int count = 0;
		int len = word1.length();
		for (int i = 0; i < len; i++) {
			if (word1.charAt(i) != word2.charAt(i)) {
				count++;
				if (count > 1) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		LeetCode1271 leetCode127 = new LeetCode1271();
		String beginWord = "hit";
		String endWord = "cog";
		String[] temp = new String[] {"hot","dot","dog","lot","log"};
		Set<String> wordList = new HashSet<String>(Arrays.asList(temp));
		System.out.println(leetCode127.ladderLength(beginWord, endWord,
				wordList));
	}
}
