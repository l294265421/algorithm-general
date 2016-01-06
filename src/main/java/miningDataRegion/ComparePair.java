package miningDataRegion;

import org.jsoup.nodes.Node;

public class ComparePair {
	private Node parent;
	private int memberNumOfGeneralizedNode;
	private int startPoint;
	public ComparePair(Node parent, int memberNumOfGeneralizedNode,
			int startPoint) {
		super();
		this.parent = parent;
		this.memberNumOfGeneralizedNode = memberNumOfGeneralizedNode;
		this.startPoint = startPoint;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public int getMemberNumOfGeneralizedNode() {
		return memberNumOfGeneralizedNode;
	}
	public void setMemberNumOfGeneralizedNode(int memberNumOfGeneralizedNode) {
		this.memberNumOfGeneralizedNode = memberNumOfGeneralizedNode;
	}
	public int getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
	}
	
	public double getDistance() {
		return 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + memberNumOfGeneralizedNode;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + startPoint;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComparePair other = (ComparePair) obj;
		if (memberNumOfGeneralizedNode != other.memberNumOfGeneralizedNode)
			return false;
		if (parent != other.parent) {
			return false;
		}
		if (startPoint != other.startPoint)
			return false;
		return true;
	}
}
