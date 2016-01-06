package miningDataRegion;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import stm.SimpleTreeMatching;

/**
 * 算法应用的对象： 即使某一个节点的直接子节点集合包含了多个数据区域，但是所有数据区域 中的广义节点的长度是相同的；
 * 
 * @author liyuncong
 *
 */
public class MiningDataRegion {
	// 数据区域的最小深度
	private int dataRegionMinDepth = 3;

	/**
	 * html中数据区域挖掘
	 * 
	 * @param node 根节点
	 * @param k 广义节点最多由几个节点组成
	 * @param threshold 广义节点的相似度阈值
	 */
	public void MDR(Element root, int k, int threshold) {
		if (Tools.treeDepth(root) >= dataRegionMinDepth) {
		}
	}

	/**
	 * 
	 * @param nodeList
	 * @param k 广义节点最多由几个节点组成
	 * @param result 保存比较结果
	 */
	public void combComp(List<Element> nodeList, int k, List<ComparePair> result) {

	}

	/**
	 * 
	 * @param nodeList
	 *            某一个节点的所有直接子节点
	 * @param k
	 *            广义节点最多由几个节点组成
	 * @param threshold
	 *            广义节点的相似度阈值
	 * @param result
	 *            保存着广义节点间的相似度
	 * @param uncovered
	 *            没有被归入某一个数据区域的节点的索引的集合
	 * @return 数据区域的集合
	 */
	public List<DataRegion> idenDRs(List<Element> nodeList, int k,
			int threshold, List<ComparePair> result, Set<Integer> uncovered) {
		List<DataRegion> dataRegionList = new LinkedList<DataRegion>();
		return dataRegionList;
	}

}
