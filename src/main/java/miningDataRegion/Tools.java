package miningDataRegion;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Tools {
	/**
	 * 获取树的深度；对于这个应用，这里可以做优化：直接一次性获得所有节点的深度；
	 * 
	 * @param root 根节点
	 * @return 树的深度
	 */
	public static int treeDepth(Element root) {
		if (root == null) {
			return 0;
		} else {
			int maxDepth = 0;
			Elements children = root.children();
			int childrenNum = children.size();
			for (int i = 0; i < childrenNum; i++) {
				int thisDepth = treeDepth(children.get(i));
				if (thisDepth > maxDepth) {
					maxDepth = thisDepth;
				}
			}
			return maxDepth + 1;
		}
	}
	
	/**
	 * 判断root及后代节点中是否有节点名为tagName的元素；
	 * @param root 根节点
	 * @param tagName 标签名
	 * @return 包含返回true，不包含返回false
	 */
	public static boolean isContainElement(Element root, String tagName) {
		Elements elements = root.getElementsByTag(tagName);
		return !(elements.size() == 0);
	}
	
	/**
	 * Prepare the HTML document for readability to scrape it. This includes
	 * things like stripping javascript, CSS, and handling terrible markup.
	 * (为readability准备HTML document。包括去掉javascript, CSS和处理糟糕的标记 )
	 */
	public static void prepDocument(Document document) {
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
	public static Elements getElementsByTag(Element e, String tag) {
		Elements es = e.getElementsByTag(tag);
		es.remove(e);
		return es;
	}
}
