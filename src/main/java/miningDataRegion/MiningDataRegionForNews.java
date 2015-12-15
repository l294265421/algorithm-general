package miningDataRegion;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import stm.SimpleTreeMatching;

public class MiningDataRegionForNews {
	private int memberMinNumLimit = 8;
	/**
	 * 新闻列表页中数据区域挖掘
	 * @param node 根节点
	 * @param threshold 广义节点的相似度阈值
	 */
	public void MDR(Element root, int threshold) {
		
	}
	
	/**
	 * 获取树的深度
	 * @param root 根节点
	 * @return 树的深度
	 */
	private int treeDepth(Element root) {
		int depth = 0;
		return depth;
	}
	
	/**
	 * 
	 * @param nodeList
	 * @param k 广义节点最多由几个节点组成;目前只支持1
	 * @param result 保存比较结果
	 */
	public void combComp(List<Element> nodeList, int k, List<ComparePair> result) {
		SimpleTreeMatching simpleTreeMatching = new SimpleTreeMatching();
		int size = nodeList.size();
		// k代表组成广义节点的标签节点个数
		for(int i = 1; i <= k; i++) {
			for(int start = 0; start < size - (2 * i - 1); start++) {
				for(int j = start; j < size - (2 * i - 1); j++) {
					int similarity = simpleTreeMatching.
							simpleTreeMatching(nodeList.get(i), nodeList.get(i+1));
					int[] first = new int[]{i};
					int[] second = new int[]{i+1};
					ComparePair comparePair = new ComparePair(first, second, similarity);
					result.add(comparePair);
				}
				// 暂时只从第一个元素开始
				break;
			}
		}
	}
	
	/**
	 * 
	 * @param nodeList 某一个节点的所有直接子节点
	 * @param k 广义节点最多由几个节点组成;目前只支持1
	 * @param threshold 广义节点的相似度阈值
	 * @param result 保存着广义节点间的相似度
	 * @param uncovered 没有被归入某一个数据区域的节点的索引的集合
	 * @return 数据区域的集合
	 */
	public List<DataRegion> idenDRs(List<Element> nodeList, int k, 
			int threshold, List<ComparePair> result, Set<Integer> uncovered) {
		List<DataRegion> dataRegionList = new LinkedList<DataRegion>();
		int size = nodeList.size();
		Set<Integer> memberIndexs = new HashSet<Integer>();
		for(int i = 0; i < size - 1; i++) {
			ComparePair comparePair = result.get(i);
			if (comparePair.getSimilarity() >= threshold) {
				int[] first = comparePair.getFirst();
				for (int j : first) {
					memberIndexs.add(j);
				}
				int[] second = comparePair.getSecond();
				for (int j : second) {
					memberIndexs.add(j);
				}
			} else {
				if (memberIndexs.size() < this.memberMinNumLimit) {
					memberIndexs.clear();
				} else {
					List<Element> elementList = new LinkedList<Element>();
					for(Integer integer : memberIndexs) {
						elementList.add(nodeList.get(integer));
					}
					DataRegion dataRegion = new DataRegion(elementList);
					dataRegionList.add(dataRegion);
					memberIndexs.clear();
				}
			}
		}
		return dataRegionList;
	}
	
}
