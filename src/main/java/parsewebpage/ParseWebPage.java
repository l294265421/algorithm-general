package parsewebpage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 网页解析的基类。它提供了Document获得和Document整理的通用功能，并固定了
 * 网页解析算法模板。具体解析类只需要实现innerParse方法。
 * @author liyuncong
 *
 */
public abstract class ParseWebPage {
	
	public Object parse(String html) {
		Document webpage = Jsoup.parse(html);
		return parse(webpage);
	}
	
	/**
	 * 
	 * @param in
	 * @param charsetName
	 * @return 返回解析之后的结果对象，解析失败返回null
	 */
	public Object parse(File in, String charsetName) {
		Document webpage = null;
		try {
			webpage = Jsoup.parse(in, charsetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parse(webpage);
	}
	
	public Object parse(File in, String charsetName, String baseUri) {
		Document webpage = null;
		try {
			webpage = Jsoup.parse(in, charsetName, baseUri);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parse(webpage);
	}
	
	public Object parse(String html, String baseUri) {
		Document webpage = Jsoup.parse(html, baseUri);
		return parse(webpage);
	}
	public Object parse(URL url, int timeoutMillis) {
		Document webpage = null;
		try {
			webpage = Jsoup.parse(url, timeoutMillis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parse(webpage);
	}
	
	public Object parse(URL url) {
		return parse(url, 3000);
	}
	
	public Object parse(Document webpage) {
		prepDocument(webpage);
		Object result = innerParse(webpage);
		return result;
		
	}
	
	protected abstract Object innerParse(Document webpage);
	
	
	/**
	 * 整理文档
	 * @param document
	 */
	private void prepDocument(Document document) {
		// 移除所有脚本
		Elements elementsToRemove = document.getElementsByTag("script");
		for (Element script : elementsToRemove) {
			// Remove (delete) this node from the DOM tree. If this node has
			// children, they are also removed.
			script.remove();
		}

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

		// 移除所有样式标签
		elementsToRemove = document.getElementsByTag("style");
		for (Element styleTag : elementsToRemove) {
			styleTag.remove();
		}
	
	}
	
	/**
	 *（获得元素e下所有标签为tag的节点（不包括e节点））
	 * @param e 元素
	 * @param tag 标签名
	 * @return
	 */
	protected Elements getElementsByTag(Element e, String tag) {
		Elements es = e.getElementsByTag(tag);
		es.remove(e);
		return es;
	}
	
}
