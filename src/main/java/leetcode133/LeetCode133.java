package leetcode133;

import global.UndirectedGraphNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
/**
 * 核心思想：宽度优先遍历图，图中的结点第一次被访问时一个map中，以结点的label为键，结点为值，下一次
 * 再需要这个结点时，直接取出即可；最后利用根节点的label取回根节点返回；有一点是非常巧妙的，每一次
 * 遍历一个结点的邻居节点时，如果是新结点，我们只是构造了这个节点的label，但是没有考虑这个节点的
 * 邻居节点，幸运的是，这个节点的内容会在后面补全。
 * @author yuncong
 *
 */
public class LeetCode133 {
	public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
		if (node == null) {
			return null;
		}

		// 存储图中所有结点
		Map<Integer, UndirectedGraphNode> nodes = new HashMap<Integer, UndirectedGraphNode>();
		int rootIndex = node.label;

		// 广度优先遍历图
		Queue<UndirectedGraphNode> helper = new LinkedList<UndirectedGraphNode>();
		helper.add(node);
		Set<UndirectedGraphNode> visited = new HashSet<>();
		while (!helper.isEmpty()) {
			UndirectedGraphNode root = helper.poll();
			// 访问过的元素不再处理
			if (!visited.add(root)) {
				continue;
			}
			List<UndirectedGraphNode> neighbors = root.neighbors;
			
			int rootLabel = root.label;
			UndirectedGraphNode rootClone = nodes.get(rootLabel);
			// 只有新元素才需要放进nodes中
			if (rootClone == null) {
				rootClone = new UndirectedGraphNode(rootLabel);
				nodes.put(rootLabel, rootClone);
			}
			if (neighbors.size() == 0) {
				continue;
			} else {
				for (UndirectedGraphNode undirectedGraphNode : neighbors) {
					helper.add(undirectedGraphNode);

					int label = undirectedGraphNode.label;
					UndirectedGraphNode newElement = nodes.get(label);
					// 只要是新元素就需要放进nodes中
					if (newElement == null) {
						newElement = new UndirectedGraphNode(
								label);
						nodes.put(newElement.label, newElement);
						rootClone.neighbors.add(newElement);
					} else {
						rootClone.neighbors.add(newElement);
					}
				}
			}
		}
		return nodes.get(rootIndex);
	}
	
	public static void main(String[] args) {
		LeetCode133 leetCode133 = new LeetCode133();
	}
}
