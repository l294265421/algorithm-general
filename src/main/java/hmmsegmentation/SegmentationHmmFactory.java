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
	
	/**
	 * 
	 * @param word 词
	 * @return word的类别表示，比如 美丽 -> BE
	 */
	public static String tranferWordToClassRepresentation(String word) {
		if (word == null) {
			throw new IllegalArgumentException("转化为类别表示形式的词不能为null");
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		int len = word.length();
		
		if (len == 0) {
			throw new IllegalArgumentException("转化为类别表示的词的长度不能为0");
		}
		
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
	
	/**
	 * 
	 * @param sentence 句子
	 * @return 句子的类别表示 比如 我 喜欢 她 -> SBES
	 */
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
	
	/**
	 * 
	 * @param sentences 句子集合
	 * @return 句子集合中所有句子的类别表示的集合
	 */
	public static List<String> transferAllSentenceToClassRepresentation(
			List<String> sentences) {
		List<String> sentenceClassRepresentations = new 
				LinkedList<String>();
		for (String sentence : sentences) {
			sentenceClassRepresentations.add(transferSentenceToClassRepresentation(sentence));
		}
		return sentenceClassRepresentations;
	}
	
	/**
	 * 
	 * @param sentences 句子（类别）集合
	 * @param pattern 模式 比如 美丽或BE
	 * @return 模式在集合中出现的次数
	 */
	public static int count(List<String> sentences, String pattern) {
		int count = 0;
		int subWordLen = pattern.length();
		for (String sentence : sentences) {
			int len = sentence.length();
			int upBound = len - subWordLen;
			for(int i = 0; i <= upBound; i++) {
				if (sentence.subSequence(i, i + subWordLen).
						equals(pattern)) {
					count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * 计算转移矩阵。
	 * @param sentencesClassRepresentation 句子类别表示集合
	 * @return 转移矩阵
	 */
	public static double[][] computeTransitionMatrix(List<String> 
		sentencesClassRepresentation) {
		int classNum = ClassDictionary.getInstance().size();
		double[][] transitionMatrix = new double[classNum][classNum];
		double BNum = count(sentencesClassRepresentation, "B");
		double MNum = count(sentencesClassRepresentation, "M");
		double ENum = count(sentencesClassRepresentation, "E");
		double SNum = count(sentencesClassRepresentation, "S");
		String[] classs = new String[] {"B", "M", "E", "S"};
		double[] counts = new double[] {BNum, MNum, ENum, SNum};
		for(int i = 0; i < classNum; i++) {
			for(int j = 0; j < classNum; j++) {
				String search = classs[i] + classs[j];
				double searchNum = count(sentencesClassRepresentation, search);
				 // Aij = P(Cj|Ci)  =  P(Ci,Cj) / P(Ci) = Count(Ci,Cj) / Count(Ci)
				transitionMatrix[i][j] = searchNum / counts[i];
			}
		}
		return transitionMatrix;
	}
	
	/**
	 * 计算发射矩阵
	 * @param text 文本 比如 我喜欢你你喜欢我...
	 * @param classRepresentations 文本的列别表示 SBESSBES
	 * @return
	 */
	public static double[][] computeEmissionMatrix(String text, 
			String classRepresentations) {
		int classNum = ClassDictionary.getInstance().size();
		int letterNum = LetterDictionary.getInstance().size();
		double[][] emissionMatrix = new double[classNum][letterNum];
		
		// 统计每个类别出现了多少次
		Map<String, Integer> classCount = new HashMap<String, Integer>();
		List<String> sentences = new LinkedList<String>();
		sentences.add(classRepresentations);
		for(String classRepresentation : ClassDictionary.getInstance().classs()) {
			int num = count(sentences, classRepresentation);
			classCount.put(classRepresentation, num);
		}
		
		// 统计每种字符/类别对出现了多少次
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
			// Bij = P(Oj|Ci)  =  P(Oj,Ci) / P(Ci) = Count(Oj,Ci) / Count(Ci)
			// Bij = P(Oj|Ci)  =  (Count(Oj,Ci) + 1)/ Count(Ci)
			double probability = (double) (letterClassPairNum + 1) / classNumTemp;
			emissionMatrix[firstIndex][secondIndex] = probability;
		}
		
		// 处理矩阵中的0
		for(int i = 0; i < classNum; i ++) {
			for(int j = 0; j < letterNum; j++) {
				if (emissionMatrix[i][j] == 0) {
					String classRepresentation = ClassDictionary.
							getInstance().key(i);
					int classNumTemp = classCount.get(classRepresentation);
					// Bij = P(Oj|Ci)  =  (Count(Oj,Ci) + 1)/ Count(Ci)
					double probability = (double) 1 / classNumTemp;
					emissionMatrix[i][j] = probability;
				}
			}
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
		double[][] transitionMatrix = computeTransitionMatrix(sentencesClassRepresentation);
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
