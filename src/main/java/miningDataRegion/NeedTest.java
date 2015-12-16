package miningDataRegion;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NeedTest {
	/**
	 * 获取树的深度；对于这个应用，这里可以做优化：直接一次性获得所有节点的深度；
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
			for(int i = 0; i < childrenNum; i++) {
				int thisDepth = treeDepth(children.get(i));
				if (thisDepth > maxDepth) {
					maxDepth = thisDepth;
				}
			}
			return maxDepth + 1;
		}
	}
}
