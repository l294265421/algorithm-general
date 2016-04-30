package hmmsegmentation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 类别/序号键值对集合。用于把转移矩阵的行列号或者发射矩阵中的行序号对应上类别。
 * @author yuncong
 *
 */
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
	
	public String key(Integer valuse) {
		for (Entry<String, Integer> entry : dictionary.entrySet()) {
			if (entry.getValue() == valuse) {
				return entry.getKey();
			}
		}
		return null;
	}
}
