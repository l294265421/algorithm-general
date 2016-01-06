package miningDataRegion;

import java.util.List;

import org.jsoup.nodes.Element;

/**
 * html列表页中的数据区域
 * @author liyuncong
 *
 */
public class DataRegion {
	private List<Element> elements;

	public DataRegion(List<Element> elements) {
		super();
		this.elements = elements;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		return "DataRegion [elements=" + elements + "]";
	}
	
}
