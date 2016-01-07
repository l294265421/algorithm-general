package jreadability.news;

import org.jsoup.nodes.Node;

public class RemoveNode implements NodeOperate {

	@Override
	public void action(Node node) {
		// 没有父节点的节点不能删除
		if (node.parent() == null) {
			return;
		}
		node.remove();
	}

}
