package stm;

import org.jsoup.nodes.Element;

/**
 * 树１中的节点A和树2中的节点B，以及它们的第一层子树的匹配矩阵m和
 * 第一层各个节点间的匹配数矩阵ｗ
 * 
 * @author liyuncong
 *
 */
public class ElementPairAssociateMW {
	private Element elementA;
	private Element elementB;
	private int[][] m;
	private int[][] w;

	public ElementPairAssociateMW(Element elementA, Element elementB,
			int[][] m, int[][] w) {
		super();
		this.elementA = elementA;
		this.elementB = elementB;
		this.m = m;
		this.w = w;
	}

	public Element getElementA() {
		return elementA;
	}

	public void setElementA(Element elementA) {
		this.elementA = elementA;
	}

	public Element getElementB() {
		return elementB;
	}

	public void setElementB(Element elementB) {
		this.elementB = elementB;
	}

	public int[][] getM() {
		return m;
	}

	public void setM(int[][] m) {
		this.m = m;
	}

	public int[][] getW() {
		return w;
	}

	public void setW(int[][] w) {
		this.w = w;
	}

}
