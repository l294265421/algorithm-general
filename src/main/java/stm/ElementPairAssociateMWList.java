package stm;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;

public class ElementPairAssociateMWList {
	private List<ElementPairAssociateMW> elementPairAssociateMWList;

	public ElementPairAssociateMWList() {
		this.elementPairAssociateMWList = new LinkedList<ElementPairAssociateMW>();
	}
	
	/**
	 * 返回包含元素A和B的ElementPairAssociateM元素
	 * @param A
	 * @param B
	 * @return
	 */
	public ElementPairAssociateMW getElementPairAssociateMW(Element A, Element B) {
		for (ElementPairAssociateMW elementPairAssociateMW : elementPairAssociateMWList) {
			if (elementPairAssociateMW.getElementA() == A && 
					elementPairAssociateMW.getElementB() == B) {
				return elementPairAssociateMW;
			}
		}
		return null;
	}
	
	public void addElementPairAssociateMW(ElementPairAssociateMW elementPairAssociateMW) {
		this.elementPairAssociateMWList.add(elementPairAssociateMW);
	}
}
