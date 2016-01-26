package hmmsegmentation;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SegmentationHmmFactory {
	
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
	
	public String transferSentenceToClassRepresentation(
			String sentence) {
		List<String> words = CommonTools.findAllChineseWord(sentence);
		StringBuilder sentenceClassRepresentation = new 
				StringBuilder();
		for (String word : words) {
			sentenceClassRepresentation.append(
					tranferWordToClassRepresentation(word));
		}
		return sentenceClassRepresentation.toString();
	}
	
	public List<String> transferAllSentenceToClassRepresentation(
			List<String> sentences) {
		List<String> sentenceClassRepresentations = new 
				LinkedList<String>();
		for (String sentence : sentences) {
			sentenceClassRepresentations.add(transferSentenceToClassRepresentation(sentence));
		}
		return sentenceClassRepresentations;
	}
	
	public int count(List<String> sentences, String pattern) {
		int count = 0;
		int subWordLen = pattern.length();
		for (String word : sentences) {
			int len = word.length();
			int upBound = len - subWordLen;
			for(int i = 0; i <= upBound; i++) {
				if (word.subSequence(i, i + subWordLen).
						equals(pattern)) {
					count++;
				}
			}
		}
		return count;
	}
	
	public double[][] computTransitionMatrix(List<String> 
		sentencesClassRepresentation) {
		double[][] transitionMatrix = new double[4][4];
		double BNum = count(sentencesClassRepresentation, "B");
		double MNum = count(sentencesClassRepresentation, "M");
		double ENum = count(sentencesClassRepresentation, "E");
		double SNum = count(sentencesClassRepresentation, "S");
		String[] classs = new String[] {"B", "M", "E", "S"};
		double[] counts = new double[] {BNum, MNum, ENum, SNum};
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				String search = classs[i] + classs[j];
				double searchNum = count(sentencesClassRepresentation, search);
				transitionMatrix[i][j] = searchNum / counts[i];
			}
		}
		return transitionMatrix;
	}
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("[\uff10-\uff19]");
		Matcher matcher = pattern.matcher("１");
		if (matcher.find()) {
			System.out.println(matcher.group());
		}
	}
	
}
