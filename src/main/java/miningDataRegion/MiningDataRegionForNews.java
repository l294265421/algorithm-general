package miningDataRegion;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parsewebpage.ParseWebPage;
import stm.SimpleTreeMatching;

/**
 * 从新闻列表页中挖掘出数据区域;
 * 经过观察，新闻列表页中包含详情页url的数据区域具有下列特点：
 * 1. 深度较浅
 * 2. 其中的广义节点只含一个标签节点
 * 3. 广义节点数目较多
 * 
 * 可优化项：
 * 1.发现其中的“下一页”
 * @author liyuncong
 *
 */
public class MiningDataRegionForNews extends ParseWebPage{
	// 数据区域的最小深度
	private int dataRegionMinDepth = 2;
	// 数据区域的最大深度
	private int dataRegionMaxDepth = 8;
	
	// 数据区域中包含的最小广义节点数
	private int memberMinNumLimit = 8;
	// 广义节点的相似度阈值
	private double threshold = 0.75;
	
	// 一个记录中的a标签数
	private int aTagNumInRecord = 1;
	// a标签中最小锚文本长度
	private int minAnchorTextLen = 8;
	
	public MiningDataRegionForNews() {
	}

	/**
	 * 从新闻列表页中挖掘数据区域
	 * @param node 根节点
	 */
	private List<DataRegion> MDR(Element root) {
		// 挖掘得到的数据区域
		List<DataRegion> dataRegions = new LinkedList<DataRegion>();
		int depth = Tools.treeDepth(root);
		// 没有包含在数据区域里元素
		List<Element> uncovered = new LinkedList<Element>();
		
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
						// 得到的相似元素个数够了，就整体当作一个数据区域对待
						DataRegion dataRegion = new DataRegion(candidateElements);
						dataRegions.add(dataRegion);
						candidateElements = new LinkedList<Element>();
					} else {
						uncovered.addAll(candidateElements);
						candidateElements = new LinkedList<Element>();
					}
					// 新起点
					if (childNum - i < this.memberMinNumLimit) {
						// 剩余元素个数总和都不足构成一个数据区域，就全部当作uncovered
						for(int j = i; j < childNum; j++) {
							uncovered.add(children.get(j));
						}
						
						break outSwith;
					} else {
						lastElement = nowElement;
						candidateElements.add(lastElement);
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
			dataRegions.addAll(MDR(element));
		}
		
		return dataRegions;
	}
	
	/**
	 * 用一些观察的结果过滤掉一些数据区域
	 */
	private void filterDataRegion(List<DataRegion> dataRegions) {
		Iterator<DataRegion> it = dataRegions.iterator();
		outerLoop: while (it.hasNext()) {
			DataRegion dataRegion = it.next();
			List<Element> elements = dataRegion.getElements();
			for (Element element : elements) {
				Elements aTags = element.getElementsByTag("a");
				// 一个数据区域里的每个记录都应该只包含一个超链接
				if (aTags.size() != aTagNumInRecord) {
					it.remove();
					continue outerLoop;
				}
				
				// 一个数据区域的每一个a标签的锚文本长度应该不小于指定的最小长度
				Element aTag = aTags.first();
				if (aTag.text().length() < minAnchorTextLen) {
					it.remove();
					continue outerLoop;
				}
			}
		}
	}

	@Override
	protected Object innerParse(Document webpage) {
		List<DataRegion> dataRegions = MDR(webpage.body());
		filterDataRegion(dataRegions);
		return dataRegions;
	}
	
	public int getDataRegionMinDepth() {
		return dataRegionMinDepth;
	}


	public void setDataRegionMinDepth(int dataRegionMinDepth) {
		this.dataRegionMinDepth = dataRegionMinDepth;
	}


	public int getDataRegionMaxDepth() {
		return dataRegionMaxDepth;
	}


	public void setDataRegionMaxDepth(int dataRegionMaxDepth) {
		this.dataRegionMaxDepth = dataRegionMaxDepth;
	}


	public int getMemberMinNumLimit() {
		return memberMinNumLimit;
	}


	public void setMemberMinNumLimit(int memberMinNumLimit) {
		this.memberMinNumLimit = memberMinNumLimit;
	}


	public double getThreshold() {
		return threshold;
	}


	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}


	public int getaTagNumInRecord() {
		return aTagNumInRecord;
	}


	public void setaTagNumInRecord(int aTagNumInRecord) {
		this.aTagNumInRecord = aTagNumInRecord;
	}


	public int getMinAnchorTextLen() {
		return minAnchorTextLen;
	}


	public void setMinAnchorTextLen(int minAnchorTextLen) {
		this.minAnchorTextLen = minAnchorTextLen;
	}
}
