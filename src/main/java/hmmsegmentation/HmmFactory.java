package hmmsegmentation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HmmFactory {
	
	public List<String> findAllChineseWordFromFile(String fileName, 
			Charset cs) {
		List<String> words = new LinkedList<String>();
		
		Path path = Paths.get(fileName);
		try {
			byte[] bytes = Files.readAllBytes(path);
			String text = new String(bytes, cs);
			Pattern pattern = Pattern.compile("([\u4e00-\u9fa5]+)");
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
				String word = matcher.group();
				words.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return words;
	}
	
	public String tranferWordToClassRepresentation(String word) {
		StringBuilder stringBuilder = new StringBuilder();
		int len = word.length();
		assert len != 0;
		if (len == 1) {
			stringBuilder.append("S");
		} else {
			stringBuilder.append("B");
			for(int i = 1; i <= len - 2; i++) {
				stringBuilder.append("M");
			}
			stringBuilder.append("E");
		}
		return stringBuilder.toString();
	}
	
	public List<String> transferAllWordToClassRepresentation(
			List<String> words) {
		List<String> wordClassRepresentations = new 
				LinkedList<String>();
		for (String word : words) {
			wordClassRepresentations.
			add(tranferWordToClassRepresentation(word));
		}
		return wordClassRepresentations;
	}
	
	public int count(List<String> words, String subWord) {
		int count = 0;
		int subWordLen = subWord.length();
		for (String word : words) {
			int len = word.length();
			int upBound = len - subWordLen;
			for(int i = 0; i <= upBound; i++) {
				if (word.subSequence(i, i + subWordLen).
						equals(subWord)) {
					count++;
				}
			}
		}
		return count;
	}
	
}
