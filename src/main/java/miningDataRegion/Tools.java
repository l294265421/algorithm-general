package miningDataRegion;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Tools {
	/**
	 * 获取树的深度；对于这个应用，这里可以做优化：直接一次性获得所有节点的深度；
	 * 
	 * @param root
	 *            根节点
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
}
