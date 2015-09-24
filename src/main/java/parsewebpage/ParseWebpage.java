package parsewebpage;

import java.net.MalformedURLException;
import java.net.URL;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

import tools.Fetch;

public class ParseWebpage {

	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL("https://www.baidu.com/");
		String htmlcode = Fetch.readByURL(url);
		System.out.println(htmlcode);
		Parser parser = Parser.createParser(htmlcode, "UTF-8");
		HtmlPage page = new HtmlPage(parser);
		try {
			parser.visitAllNodesWith(page);
		} catch (ParserException e1) {
			e1 = null;
		}
		NodeList nodelist = page.getBody();
		NodeFilter filter = new TagNameFilter("A");
		nodelist = nodelist.extractAllNodesThatMatch(filter, true);
		for (int i = 0; i < nodelist.size(); i++) {
			LinkTag link = (LinkTag) nodelist.elementAt(i);
			System.out.println(link.getAttribute("href"));
		}
	}
}
