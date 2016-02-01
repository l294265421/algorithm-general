package hmmsegmentation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;

public class SegmentationHmmFactory {
	private static Hmm<ObservationInteger> hmm;
	
	private SegmentationHmmFactory() {
		
	}
	
	public static String tranferWordToClassRepresentation(String word) {
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
	
	public static String transferSentenceToClassRepresentation(
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
	
	public static List<String> transferAllSentenceToClassRepresentation(
			List<String> sentences) {
		List<String> sentenceClassRepresentations = new 
				LinkedList<String>();
		for (String sentence : sentences) {
			sentenceClassRepresentations.add(transferSentenceToClassRepresentation(sentence));
		}
		return sentenceClassRepresentations;
	}
	
	public static int count(List<String> sentences, String pattern) {
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
	
	public static double[][] computTransitionMatrix(List<String> 
		sentencesClassRepresentation) {
		int classNum = ClassDictionary.getInstance().size();
		double[][] transitionMatrix = new double[classNum][classNum];
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
	
	public static double[][] computeEmissionMatrix(String text, 
			String classRepresentations) {
		int classNum = ClassDictionary.getInstance().size();
		int letterNum = LetterDictionary.getInstance().size();
		double[][] emissionMatrix = new double[classNum][letterNum];
		
		Map<String, Integer> classCount = new HashMap<String, Integer>();
		List<String> sentences = new LinkedList<String>();
		sentences.add(classRepresentations);
		for(String classRepresentation : ClassDictionary.getInstance().classs()) {
			int num = count(sentences, classRepresentation);
			classCount.put(classRepresentation, num);
		}
		
		Map<LetterClassPair, Integer> letterClassPairCount = 
				new HashMap<LetterClassPair, Integer>();
		int len = text.length();
		for(int i = 0; i < len; i++) {
			LetterClassPair letterClassPair = new LetterClassPair
					(text.substring(i, i + 1), classRepresentations.substring(i, i + 1));
			if (letterClassPairCount.keySet().contains(letterClassPair)) {
				letterClassPairCount.put(letterClassPair, letterClassPairCount.get(letterClassPair) + 1);
			} else {
				letterClassPairCount.put(letterClassPair, 1);
			}
		}
		
		for(LetterClassPair letterClassPair : letterClassPairCount.keySet()) {
			int firstIndex = ClassDictionary.getInstance().value(letterClassPair.getClassRepresentation());
			int secondIndex = LetterDictionary.getInstance().value(letterClassPair.getLetter());
			int letterClassPairNum = letterClassPairCount.get(letterClassPair);
			int classNumTemp = classCount.get(letterClassPair.getClassRepresentation());
			double probability = (double) letterClassPairNum / classNumTemp;
			emissionMatrix[firstIndex][secondIndex] = probability;
		}
		return emissionMatrix;
	}
	
	public static Hmm<ObservationInteger> hmm() {
		if (hmm != null) {
			return hmm;
		}
		
		int hiddenStatesNum = ClassDictionary.getInstance().size();
		int observedStates = LetterDictionary.getInstance().size();
		Hmm<ObservationInteger> hmm = new 
				Hmm<ObservationInteger>(hiddenStatesNum, 
						new OpdfIntegerFactory(observedStates));
		hmm.setPi(0, 0.5);
		hmm.setPi(1, 0);
		hmm.setPi(2, 0);
		hmm.setPi(3, 0.5);
		
		String fileName = "icwb2-data/training/"
				+ "pku_training.utf8";
		List<String> sentences = CommonTools.
				findAllChineseSentence(fileName, "UTF-8");
		List<String> sentencesClassRepresentation = transferAllSentenceToClassRepresentation(sentences);
		double[][] transitionMatrix = computTransitionMatrix(sentencesClassRepresentation);
		for(int i = 0; i < transitionMatrix.length; i++) {
			for(int j = 0; j < transitionMatrix[0].length; j++) {
				hmm.setAij(i, j, transitionMatrix[i][j]);
			}
		}
		
		StringBuilder text = new StringBuilder();
		for (String sentence : sentences) {
			text.append(sentence);
		}
		StringBuilder classRepresentations = new StringBuilder();
		for (String sentenceClassRepresentation : sentencesClassRepresentation) {
			classRepresentations.append(sentenceClassRepresentation);
		}
		double[][] emissionMatrix = computeEmissionMatrix(text.toString(), classRepresentations.toString());
		for(int i = 0; i < emissionMatrix.length; i++) {
			hmm.setOpdf(i, new OpdfInteger(emissionMatrix[i]));
		}
		
		return hmm;
	}
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("[\uff10-\uff19]");
		Matcher matcher = pattern.matcher("１");
		if (matcher.find()) {
			System.out.println(matcher.group());
		}
	}
	
}
