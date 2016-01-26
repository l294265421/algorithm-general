package hmmsegmentation;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class HmmFactoryTest {
	private SegmentationHmmFactory factory;
	
	@Before
	public void before() {
		factory = new SegmentationHmmFactory();
	}

	@Test
	public void testFindAllChineseWord() {
		String fileName = "icwb2-data/training/"
				+ "pku_training.utf8";
		List<String> sentences = CommonTools.
				findAllChineseSentence(fileName, "UTF-8");
		List<String> words = CommonTools.findAllChineseWord
				(sentences.get(2));
		for (String word : words) {
			System.out.println(word);
		}
	}

	@Test
	public void testTranferWordToClassRepresentation() {
		String word = "李云聪王芳";
		System.out.println(factory.
				tranferWordToClassRepresentation(word));
	}

	@Test
	public void testCount() {
		List<String> words = new LinkedList<String>();
		words.add("BME");
		words.add("BE");
		words.add("S");
		words.add("BMMME");
		System.out.println(factory.count(words, "BM"));
	}

	@Test
	public void testComputTransitionMatrix() {
		String fileName = "icwb2-data/training/"
				+ "pku_training.utf8";
		List<String> sentences = CommonTools.
				findAllChineseSentence(fileName, "UTF-8");
		List<String> sentencesClassRepresentation = factory.
				transferAllSentenceToClassRepresentation(sentences);
		double[][] transitionMatrix = factory.
				computTransitionMatrix(sentencesClassRepresentation);
		for (double[] ds : transitionMatrix) {
			for (double d : ds) {
				System.out.print(d);
				System.out.print(", ");
			}
			System.out.println();
		}
	}
	
	@Test
	public void testFindAllChineseSentence() {
		String fileName = "icwb2-data/training/"
				+ "pku_training.utf8";
		List<String> sentences = CommonTools.
				findAllChineseSentence(fileName, "UTF-8");
		for(int i = 0; i < 5; i++) {
			System.out.println(sentences.get(i));
		}
	}
}
