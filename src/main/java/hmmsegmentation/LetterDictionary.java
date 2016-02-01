package hmmsegmentation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LetterDictionary {
	private Map<String, Integer> dictionary;
	
	private LetterDictionary() {
		int serialNumber = 0;
		dictionary = new HashMap<String, Integer>();
		String fileName = "icwb2-data/training/"
				+ "pku_training.utf8";
		List<String> sentences = CommonTools.
				findAllChineseSentence(fileName, "UTF-8");
		List<String> allWords = new LinkedList<String>();
		for (String sentence : sentences) {
			allWords.addAll(CommonTools.findAllChineseWord
					(sentence));
		}
		for (String word : allWords) {
			int len = word.length();
			for(int i = 0; i < len; i++) {
				String current = word.substring(i, i + 1);
				if (!dictionary.containsKey(current)) {
					dictionary.put(current, serialNumber);
					serialNumber++;
				}
			}
		}
	}
	
	private static class SingletonHolder {
		private static LetterDictionary dictionary = new LetterDictionary();
	}
	
	public static LetterDictionary getInstance() {
		return SingletonHolder.dictionary;
	}
	
	public int size() {
		return dictionary.size();
	}
	
	public Set<String> letters() {
		return dictionary.keySet();
	}
	
	public Integer value(String key) {
		return dictionary.get(key);
	}
}
