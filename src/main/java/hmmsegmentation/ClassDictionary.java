package hmmsegmentation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassDictionary {
	private Map<String, Integer> dictionary;
	
	private ClassDictionary() {
		dictionary = new HashMap<String, Integer>();
		dictionary.put("B", 0);
		dictionary.put("M", 1);
		dictionary.put("E", 2);
		dictionary.put("S", 3);
	}
	
	private static class SingletonHolder {
		private static ClassDictionary classDictionary = new ClassDictionary();
	}
	
	public static ClassDictionary getInstance() {
		return SingletonHolder.classDictionary;
	}
	
	public int size() {
		return dictionary.size();
	}
	
	public Set<String> classs() {
		return dictionary.keySet();
	}
	

	public Integer value(String key) {
		return dictionary.get(key);
	}
}
