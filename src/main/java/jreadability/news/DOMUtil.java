package jreadability.news;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;
/**
 * 
 * @author liyuncong
 *
 */
public class DOMUtil {
	/**
	 * 先根遍历节点树，获得所有文本节点，所有叶子节点都是文本节点
	 * @param node
	 * @return 该节点下的所有文本节点
	 */
	public static List<TextNode> getAllTextNodes(Node node) {
		List<TextNode> textNodes = new ArrayList<>();
		
		if (node instanceof TextNode) {
			textNodes.add((TextNode) node);
			return textNodes;
		}
		
		List<Node> childNode = node.childNodes();
		int childNum = childNode.size();
		for(int i = 0; i < childNum; i++) {
			Node temp = childNode.get(i);
			textNodes.addAll(getAllTextNodes(temp));
		}
		return textNodes;
	}
	
	/**
	 * 判断candidate是否为parent的后代节点
	 * @param parent 父节点
	 * @param candidate 可能的后代节点
	 * @return
	 */
	public static boolean isChild(Node parent, Node candidate) {
		class FindCandidate implements NodeOperate{
			private boolean find;
			private Node candidate;
			
			public FindCandidate(Node candidate) {
				this.candidate = candidate;
			}
			@Override
			public void action(Node node) {
				if (node == candidate) {
					find = true;
				}
			}
			public boolean isFind() {
				return find;
			}
			
		}
		FindCandidate findCandidate = new FindCandidate(candidate);
		traverse(parent, findCandidate);
		return findCandidate.isFind();
	}
	
	/**
	 * 先根遍历节点树;如果是要删除树中节点，则要注意：没有父节点的节点不能删除
	 * @param node 根节点
	 * @param nodeOperate 根节点上的操作
	 */
	public static void traverse(Node node, NodeOperate nodeOperate) {
		if (node != null) {
			// foreSize和afterSize用于应对删除操作
			Node parent = node.parent();
			if (parent == null) {
				nodeOperate.action(node);
			} else {
				int foreSize = parent.childNodes().size();
				nodeOperate.action(node);
				int afterSize = parent.childNodes().size();
				// 如果该节点已经从节点树中删除，就没有必要遍历其子节点了
				if (afterSize < foreSize) {
					return;
				}
			}
			
			List<Node> children = node.childNodes();
			int foreSize = children.size();
			for(int i = 0; i < foreSize;) {
				traverse(children.get(i), nodeOperate);
				int afterSize = children.size();
				if (afterSize < foreSize) {
					foreSize = afterSize;
				} else {
					i++;
				}
			}
		}
	}
	
	/**
	 * 遍历树中夹在leftPath和rightPath之间的区域，对遇到的元素进行操作;
	 * 如果是要删除树中节点，则要注意：没有父节点的节点不能删除
	 * @param leftPath 树中的一条链表，后一个元素是前一个元素的父节点，最后一个节点的
	 * 父节点和rightPath中最后一个的父节点是同一节点
	 * @param rightPath 树中的一条链表，后一个元素是前一个元素的父节点，最后一个节点的
	 * 父节点和leftPath中最后一个的父节点是同一节点
	 * @param nodeOperate
	 */
	public static void travers(List<Node> leftPath, List<Node> rightPath, NodeOperate nodeOperate) {
 		int leftPathSize = leftPath.size();
 		// 没访问最后一个元素
 		for(int i = 0; i < leftPathSize - 1; i++) {
 			Node node = leftPath.get(i);
 			// 这是一个可能有孩子节点的节点
			Node nextSibling = node.nextSibling();
			while (nextSibling != null) {
				Node temp = nextSibling.nextSibling();
				
				traverse(nextSibling, nodeOperate);
				
				nextSibling = temp;
			}
 		}
 		
 		int rightPathSize = rightPath.size();
 		// 没访问最后一个元素
 		for(int i = 0; i < rightPathSize - 1; i++) {
 			Node node = rightPath.get(i);
 			// 这是一个可能有孩子节点的节点
			Node previousSibling = node.previousSibling();
			while (previousSibling != null) {
				Node temp = previousSibling.nextSibling();
				
				traverse(previousSibling, nodeOperate);
				
				previousSibling = temp;
			}
 		}
 		
		Node lastLeftPathNode = leftPath.get(leftPathSize - 1);
		Node lastRightPathNode = rightPath.get(rightPathSize - 1);
		// 这是一个可能有孩子节点的节点
		Node lastNextSibling = lastLeftPathNode.nextSibling();
		while (lastNextSibling != lastRightPathNode) {
			Node temp = lastNextSibling.nextSibling();
			
			traverse(lastNextSibling, nodeOperate);
			
			lastNextSibling = temp;
		}
		
	}
	
	/**
	 * 遍历树中leftPath右边区域，对遇到的元素进行操作；
	 * 如果是要删除树中节点，则要注意：没有父节点的节点不能删除
	 * @param leftPath 树中的一条链表，后一个元素是前一个元素的父节点
	 * @param nodeOperate
	 */
	public static void travers(List<Node> leftPath, NodeOperate nodeOperate) {
 		int leftPathSize = leftPath.size();
 		for(int i = 0; i < leftPathSize; i++) {
 			Node node = leftPath.get(i);
 			// 这是一个可能有孩子节点的节点
			Node nextSibling = node.nextSibling();
			while (nextSibling != null) {
				Node temp = nextSibling.nextSibling();
				
				traverse(nextSibling, nodeOperate);
				
				nextSibling = temp;
			}
 		}
	}
	
	/**
	 * 遍历树中rightPath左边边区域，对遇到的元素进行操作;
	 * 如果是要删除树中节点，则要注意：没有父节点的节点不能删除
	 * @param rightPath 树中的一条链表，后一个元素是前一个元素的父节点
	 * @param nodeOperate
	 */
	public static void travers(NodeOperate nodeOperate, List<Node> rightPath) {
 		int rightPathSize = rightPath.size();
 		for(int i = 0; i < rightPathSize; i++) {
 			Node node = rightPath.get(i);
 			// 这是一个可能有孩子节点的节点
			Node previousSibling = node.previousSibling();
			while (previousSibling != null) {
				Node temp = previousSibling.previousSibling();
				
				traverse(previousSibling, nodeOperate);
				
				previousSibling = temp;
			}
 		}
	}
	
	/**
	 * 
	 * 遍历树中夹在leftPath和rightPath之间的区域，对删除指定标签名的元素；
	 * @param leftPath 树中的一条链表，后一个元素是前一个元素的父节点，最后一个节点的
	 * 父节点和rightPath中最后一个的父节点是同一节点
	 * @param rightPath 树中的一条链表，后一个元素是前一个元素的父节点，最后一个节点的
	 * 父节点和leftPath中最后一个的父节点是同一节点
	 * @param tagName 要删除的节点名
	 */
	public static void deleteAllElementByTagName(List<Node> leftPath, List<Node> rightPath, String tagName) {
		DOMUtil.travers(leftPath, rightPath, new RemoveNodeByName(tagName));
	}
	
	/**
	 * 删除path左边或者右边的元素
	 * @param path 树中的一条链表，后一个元素是前一个元素的父节点
	 */
	public static void deleteLeftOrRight(List<Node> path, String leftOrRight) {
		if (leftOrRight.equals("right")) {
			travers(path, new RemoveNode());
		} else {
			travers(new RemoveNode(), path);
		}
	}
}
