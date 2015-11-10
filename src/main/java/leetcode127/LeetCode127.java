package leetcode127;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeetCode127 {
	public int ladderLength(String beginWord, String endWord,
			Set<String> wordList) {
		if (isAdjacent(beginWord, endWord)) {
			return 2;
		}

		int distance = 0;

		if (!wordList.isEmpty() && wordList.contains(beginWord)) {
			wordList.remove(beginWord);
		}
		if (wordList.isEmpty()) {
			return distance;
		}

		List<List<String>> past = new ArrayList<>();
		List<String> first = new ArrayList<>();
		first.add(beginWord);
		past.add(first);
		int preElementsIndex = 0;
		boolean isContinue = true;
		
		Set<String> rubbish = new HashSet<>();
		while (isContinue) {
			List<String> elements = new ArrayList<>();
			List<String> preElements = past.get(preElementsIndex);
			for (String word : wordList) {
				if (rubbish.contains(word)) {
					continue;
				}
				
				// 与preElements中任何一个相邻都意味着可到达
				for (String preWord : preElements) {
					boolean isAdjacent = isAdjacent(word, preWord);
					if (isAdjacent) {
						if (isAdjacent(word, endWord)) {
							distance = preElementsIndex + 3;
							isContinue = false;
						}
						
						elements.add(word);
						rubbish.add(word);
					}
				}
			}

			if (elements.size() == 0 || rubbish.size() == wordList.size()) {
				break;
			}
			
			past.add(elements);
			preElementsIndex++;
		}
		return distance;
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
		LeetCode127 leetCode127 = new LeetCode127();
		String beginWord = "hit";
		String endWord = "cog";
		String[] temp = new String[] {"hot","dot","dog","lot","log"};
		Set<String> wordList = new HashSet<String>(Arrays.asList(temp));
		System.out.println(leetCode127.ladderLength(beginWord, endWord,
				wordList));
	}
}
