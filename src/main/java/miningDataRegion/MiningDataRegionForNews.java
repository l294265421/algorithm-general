package miningDataRegion;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import stm.SimpleTreeMatching;

/**
 * 从新闻列表页中挖掘出数据区域;
 * 经过观察，新闻列表页中包含详情页url的数据区域具有下列特点：
 * 1. 深度较浅
 * 2. 其中的广义节点只含一个标签节点
 * 3. 广义节点数目较多
 * @author liyuncong
 *
 */
public class MiningDataRegionForNews {
	// 数据区域中包含的最小广义节点数
	private int memberMinNumLimit = 8;
	// 数据区域的最小深度
	private int dataRegionMinDepth = 2;
	// 数据区域的最大深度
	private int dataRegionMaxDepth = 8;
	
	public MiningDataRegionForNews() {
	}
	
	public MiningDataRegionForNews(int memberMinNumLimit,
			int dataRegionMinDepth, int dataRegionMaxDepth) {
		super();
		this.memberMinNumLimit = memberMinNumLimit;
		this.dataRegionMinDepth = dataRegionMinDepth;
		this.dataRegionMaxDepth = dataRegionMaxDepth;
	}

	/**
	 * 从新闻列表页中挖掘数据区域
	 * @param node 根节点
	 * @param threshold 广义节点的相似度阈值
	 */
	public List<DataRegion> MDR(Element root, double threshold) {
		if (root.tagName().equals("ul")) {
			System.out.println(root);
			System.out.println("--------------------------------------");
		}
		
		// 挖掘得到的数据区域
		List<DataRegion> dataRegions = new LinkedList<DataRegion>();
		int depth = treeDepth(root);
		// 没有包含在数据区域里元素
		List<Element> uncovered = new LinkedList<Element>();
		// 新闻列表区域的深度必须大于等于2，小于等于5
		int whichCase = -1;
		if (depth < this.dataRegionMinDepth) {
			whichCase = 1;
		} else if(depth <= this.dataRegionMaxDepth){
			whichCase = 2;
		} else {
			whichCase = 3;
		}
		outSwith: switch (whichCase) {
		case 1:
			
			break;
			
		case 2:
			
			Elements children = root.children();
			int childNum = children.size();
			
			if (childNum < this.memberMinNumLimit) {
				for(int i = 0; i < childNum; i++) {
					uncovered.add(children.get(i));
				}
				
				break;
			}
			
			List<Element> candidateElements = new LinkedList<Element>();
			candidateElements.add(children.get(0));
			SimpleTreeMatching simpleTreeMatching = new SimpleTreeMatching();
			Element lastElement = children.get(0);
			for(int i = 1; i < childNum; i++) {
				Element nowElement = children.get(i);
				int matchNum = simpleTreeMatching.simpleTreeMatching(
							lastElement, nowElement);
			
				double normalizedScore = simpleTreeMatching.normalizedSimpleTreeMatching(
						lastElement, nowElement, matchNum);
				if (normalizedScore < threshold) {
					int candidateNum = candidateElements.size();
					if (candidateNum >= this.memberMinNumLimit) {
						DataRegion dataRegion = new DataRegion(candidateElements);
						dataRegions.add(dataRegion);
						candidateElements = new LinkedList<Element>();
					} else {
						uncovered.addAll(candidateElements);
						candidateElements = new LinkedList<Element>();
					}
					// 新起点
					if (childNum - i < this.memberMinNumLimit) {
						for(int j = i; j < childNum; j++) {
							uncovered.add(children.get(j));
						}
						
						break outSwith;
					} else {
						lastElement = nowElement;
					}
					
				} else {
					lastElement = children.get(i);
					candidateElements.add(lastElement);
				}
			}
			
			int candidateNum = candidateElements.size();
			if (candidateNum >= this.memberMinNumLimit) {
				DataRegion dataRegion = new DataRegion(candidateElements);
				dataRegions.add(dataRegion);
				candidateElements = new LinkedList<Element>();
			} else {
				uncovered.addAll(candidateElements);
				candidateElements = new LinkedList<Element>();
			}
			
			break;

		case 3:
			uncovered.addAll(root.children());
			break;
		}
		
		// 递归处理没有被包含在数据区域里的节点
		for (Element element : uncovered) {
			dataRegions.addAll(MDR(element, threshold));
		}
		
		return dataRegions;
	}
	
	/**
	 * 获取树的深度；对于这个应用，这里可以做优化：直接一次性获得所有节点的深度；
	 * @param root 根节点
	 * @return 树的深度
	 */
	private int treeDepth(Element root) {
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
	
	/**
	 * 判断root及后代节点中是否有节点名为tagName的元素；
	 * @param root 根节点
	 * @param tagName 标签名
	 * @return
	 */
	private boolean isContainElement(Element root, String tagName) {
		Elements elements = root.getElementsByTag(tagName);
		return !(elements.size() == 0);
	}

}
