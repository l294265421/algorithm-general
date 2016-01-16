package parse.news.detailpage;

import static org.junit.Assert.*;

import java.io.File;

import org.jsoup.nodes.Document;
import org.junit.Test;

import com.liyuncong.application.commontools.FileTools;

import parsewebpage.ParseWebPage;

public class CleanNewsDetailPageTest {

	@Test
	public void testParseFileString() {
		ParseWebPage parseWebPage = new CleanNewsDetailPage();
		File file = new File("D:\\program\\bigdata\\people.com.cn\\finance.people.com.cn\\0a5e49e68fb6ec94a8659e76a52be4e1.html");
		Document document = (Document) parseWebPage.parse(file, "utf-8");
		FileTools.writeStringToFile(document.outerHtml(), "d:\\test\\test.html");
	}

}
