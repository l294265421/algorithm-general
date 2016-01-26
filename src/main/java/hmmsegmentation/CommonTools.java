package hmmsegmentation;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liyuncong.application.commontools.FileTools;

public class CommonTools {
	public static List<String> findAllChineseSentence(String fileName, 
			String charsetName) {
		List<String> sentences = new LinkedList<String>();
		try {
			String text = FileTools.readFile(fileName, charsetName);
			// 中文汉字、空格和中文0-9
			Pattern pattern = Pattern.compile
					("([\u4e00-\u9fa5]|\\s|[\uff10-\uff19])+");
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
				String sentence = matcher.group();
				sentences.add(sentence);
			}
		} catch (Exception e) {
		}
		return sentences;
	}
	
	public static List<String> findAllChineseWord(String text) {
		List<String> words = new LinkedList<String>();
		// 中文汉字和中文0-9
		Pattern pattern = Pattern.compile("([\u4e00-\u9fa5]"
				+ "|[\uff10-\uff19])+");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			String word = matcher.group();
			words.add(word);
		}
		return words;
	}
}
