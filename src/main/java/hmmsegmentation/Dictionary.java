package hmmsegmentation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Dictionary {
	private Map<String, Integer> dictionary;
	
	private Dictionary() {
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
		private static Dictionary dictionary = new Dictionary();
	}
	
	public static Dictionary getInstance() {
		return SingletonHolder.dictionary;
	}
	
}
