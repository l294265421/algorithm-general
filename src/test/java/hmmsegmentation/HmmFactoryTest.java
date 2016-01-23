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
	private HmmFactory factory;
	
	@Before
	public void before() {
		factory = new HmmFactory();
	}

	@Test
	public void testFindAllChineseWordFromFile() {
		String fileName = "icwb2-data/training/"
				+ "pku_training.utf8";
		Charset cs = Charset.forName("utf-8");
		List<String> words = factory.findAllChineseWordFromFile(fileName, cs);
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

}
