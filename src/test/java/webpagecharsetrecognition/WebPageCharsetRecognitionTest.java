package webpagecharsetrecognition;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import parsewebpage.ParseWebPage;

public class WebPageCharsetRecognitionTest {

	@Test
	public void testParse() throws MalformedURLException {
		URL url = new URL("http://news.163.com/16/0111/07/BD1HQS5T0001124J.html");
		ParseWebPage parseWebPage = new WebPageCharsetRecognition();
		assertEquals("gb2312", parseWebPage.parse(url));
	}

}
