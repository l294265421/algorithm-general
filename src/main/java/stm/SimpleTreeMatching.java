package stm;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import levenshteinDistance.EditPath;
import levenshteinDistance.Station;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 简单树匹配算法实现;
 * 树对齐；
 * 
 * @author liyuncong
 *
 */
public class SimpleTreeMatching {
	private ElementPairAssociateMWList elementPairAssociateMWList = new ElementPairAssociateMWList();

	/**
	 * 简单树匹配算法实现;
	 * @param A
	 * @param B
	 * @return
	 */
	public int simpleTreeMatching(Element A, Element B) {
		String aTag = A.tagName();
		String bTag = B.tagName();
		if (!aTag.equals(bTag)) {
			return 0;
		}
		// 只有标签节点，不包含文本节点
		Elements aChild = A.children();
		Elements bChild = B.children();
		int aChildNum = aChild.size();
		int bChildNum = bChild.size();
		if (aChildNum == 0 || bChildNum == 0) {
			return 1;
		}
		
		int[][] m = new int[aChildNum + 1][bChildNum + 1];
		int[][] w = new int[aChildNum + 1][bChildNum + 1];
		// 当A没有子树时，只有０个匹配
		for(int i = 0; i < aChildNum + 1; i++) {
			m[i][0] = 0;
		}
		//　当B没有子树时，只有０个匹配
		for(int j = 0; j < bChildNum + 1; j++) {
			m[0][j] = 0;
		}
		for(int i = 1; i < aChildNum + 1; i++) {
			for(int j = 1; j < bChildNum + 1; j++) {
				w[i][j] = simpleTreeMatching(aChild.get(i - 1), bChild.get(j - 1));
				m[i][j] = Math.max(Math.max(m[i][j-1], m[i - 1][j]), 
						m[i-1][j-1] + w[i][j]);
			}
		}
		ElementPairAssociateMW elementPairAssociateMW = new ElementPairAssociateMW(A, B, m, w);
		elementPairAssociateMWList.addElementPairAssociateMW(elementPairAssociateMW);
		return m[aChildNum][bChildNum] + 1;
	}
	
	/**
	 * 对两颗树的匹配分数做归一化处理；
	 * @param A
	 * @param B
	 * @param matchNum
	 * @return
	 */
	public double normalizedSimpleTreeMatching(Element A, Element B, int matchNum) {
		return (matchNum * 2)/(getElementNum(A) + getElementNum(B));
	}
	
	/**
	 * 获得root的后代节点的个数（包含root）
	 * @param root 根节点
	 * @return
	 */
	private int getElementNum(Element root) {
		if (root == null) {
			return 0;
		} else {
			int num = 1;
			Elements children = root.children();
			int childNum = children.size();
			num += childNum;
			for (Element element : children) {
				num += getElementNum(element);
			}
			return num;
		}
	}
	
	/**
	 * 对齐树，没有对齐的节点将被删除
	 * @param A
	 * @param B
	 */
	public void align(Element A, Element B) {
		String aTag = A.tagName();
		String bTag = B.tagName();
		if (!aTag.equals(bTag)) {
			A.remove();
			B.remove();
			return;
		}
		// 只有标签节点，不包含文本节点
		Elements aChild = A.children();
		Elements bChild = B.children();
		ElementPairAssociateMW elementPairAssociateMW = this.elementPairAssociateMWList.getElementPairAssociateMW(A, B);
		int aChildNum = aChild.size();
		int bChildNum = bChild.size();
		int[][] m = elementPairAssociateMW.getM();
		int[][] w = elementPairAssociateMW.getW();
		int maxMatchNum = m[aChildNum][bChildNum];
		if (maxMatchNum == 0) {
			for(int i = 0; i < aChildNum; i++) {
				Element element = aChild.get(i);
				element.remove();
			}
			for(int j = 0; j < bChildNum; j++) {
				Element element = bChild.get(j);
				element.remove();
			}
		} else {
			// 在m中寻找状态转移路径
			EditPath editPath = getOnePath(m, w);
			List<Station> stationList = editPath.getStationList();
	    	int size = stationList.size();
	    	Station now = stationList.get(0);
	    	for(int i = 1; i <= size - 1; i++) {
	    		Station next = stationList.get(i);
	    		int iMinus = now.getI() - next.getI();
	    		int jMinus = now.getJ() - next.getJ();
	    		
	    		// 插入
	    		if (iMinus == 0 && jMinus == 1) {
					bChild.get(now.getJ() - 1).remove();
				// 删除
				} else if (iMinus == 1 && jMinus == 0) {
					aChild.get(now.getI() - 1).remove();
				// 相等
				} else {
					align(aChild.get(now.getI() - 1), bChild.get(now.getJ() - 1));
				}
	    		now = next;
	    	}
		}
	}
	
	/**
	 * 获得产生最大匹配的状态序列
	 * @param aChild　第一个节点序列
	 * @param bChild　第二个节点序列
	 * @param m　匹配矩阵
	 * @param w　各个节点间的匹配数矩阵ｗ
	 * @return
	 */
	public EditPath getOnePath(int[][] m, int[][] w) {
		List<Station> stationList = new LinkedList<Station>();
		int i = m.length;
		int j = m[0].length;
		stationList.add(new Station(i, j));
		while(i > 0 || j > 0) {
			// 第一个节点序列存在元素才能删除
			if (i > 0) {
				int up = m[i - 1][j];
				if (up == m[i][j]) {
					i = i - 1;
					stationList.add(new Station(i, j));
					continue;
				}
			}
			// 第二个节点序列存在元素才需要插入
			if (j > 0) {
				int left = m[i][j - 1];
				if (left == m[i][j]) {
					j = j - 1;
					stationList.add(new Station(i, j));
					continue;
				}
			}
			//　相等
			if (i > 0 && j >0) {
				int upLeft = m[i - 1][j - 1];
				if (upLeft + w[i][j] == m[i][j]) {
					i = i - 1;
					j = j - 1;
					stationList.add(new Station(i, j));
				}
			}
		}
		EditPath editPath = new EditPath(stationList);
		return editPath;
	}
	
	public static void main(String[] args) throws IOException {
		Document document = Jsoup.parse(new File("index.html"), "utf-8");
		System.out.println(document.children().size());;
	}
}
