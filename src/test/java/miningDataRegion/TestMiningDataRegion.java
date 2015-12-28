package miningDataRegion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tools.Helper;

public class TestMiningDataRegion {

	public static void main(String[] args) throws MalformedURLException, IOException {
		// String urlStr = "http://politics.people.com.cn/GB/1027/index.html";
		String urlStr = "http://www.news.cn/local/index.htm";
		
		Document rootElement = Jsoup.parse(new URL(urlStr), 5000);
		prepDocument(rootElement);
		MiningDataRegionForNews miningDataRegion = new MiningDataRegionForNews();
		List<DataRegion> dataRegions = miningDataRegion.MDR(rootElement.body(), 0.75);
		
		int num = dataRegions.size();
		for(int i = 0; i < num; i++) {
			Helper.writeStringToFile(dataRegions.get(i).toString(), "D:\\test\\" + i + ".html");
		}
		System.out.println(dataRegions.size());
	}

	/**
	 * Prepare the HTML document for readability to scrape it. This includes
	 * things like stripping javascript, CSS, and handling terrible markup.
	 * (为readability准备HTML document。包括去掉javascript, CSS和处理糟糕的标记 )
	 */
	private static void prepDocument(Document document) {
		/* Remove all scripts */
		// 移除所有脚本
		Elements elementsToRemove = document.getElementsByTag("script");
		for (Element script : elementsToRemove) {
			// Remove (delete) this node from the DOM tree. If this node has
			// children, they are also removed.
			script.remove();
		}

		/* Remove all stylesheets */
		// 移除所有样式表
		elementsToRemove = getElementsByTag(document.head(), "link");
		for (Element styleSheet : elementsToRemove) {
			// <link type="text/css" rel="stylesheet"
			// href="/bundles/blog-common.css?v=TdLMZRHMQfitXmNZ7SFinI4hbzrT2-_1PvIqhhWnsbI1"
			// />
			if ("stylesheet".equalsIgnoreCase(styleSheet.attr("rel"))) {
				styleSheet.remove();
			}
		}

		/* Remove all style tags in head */
		// 移除所有样式标签
		elementsToRemove = document.getElementsByTag("style");
		for (Element styleTag : elementsToRemove) {
			styleTag.remove();
		}
	
	}
	
	/**
	 * Jsoup's Element.getElementsByTag(Element e) includes e itself, which is
	 * different from W3C standards. This utility function is exclusive of the
	 * Element e. （获得元素e下所有标签为tag的节点（不包括e节点））
	 * 
	 * @param e
	 * @param tag
	 * @return
	 */
	private static Elements getElementsByTag(Element e, String tag) {
		Elements es = e.getElementsByTag(tag);
		es.remove(e);
		return es;
	}
}
