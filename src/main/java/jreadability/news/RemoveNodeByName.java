package jreadability.news;

import org.jsoup.nodes.Node;

/**
 * 
 * @author liyuncong
 *
 */
public class RemoveNodeByName implements NodeOperate {
	private String nodeNameToRemove;

	public RemoveNodeByName(String nodeNameToRemove) {
		super();
		this.nodeNameToRemove = nodeNameToRemove;
	}


	@Override
	public void action(Node node) {
		// 没有父节点的节点不能删除
		if (node.parent() == null || !node.nodeName().equals(nodeNameToRemove)) {
			return;
		}
		node.remove();
	}

}
