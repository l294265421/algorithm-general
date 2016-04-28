package elevatoralgorithm;

/**
 * 乘电梯的人发出的一条指令，告诉电梯，他在哪里，要去哪里。
 * @author yuncong
 *
 */
public class Instruction {
	private int origion;
	private int destination;
	private Direction direction;
	public Instruction(int origion, int destination, Direction direction) {
		super();
		this.origion = origion;
		this.destination = destination;
		this.direction = direction;
	}
	public int getOrigion() {
		return origion;
	}
	public void setOrigion(int origion) {
		this.origion = origion;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destination;
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + origion;
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
		Instruction other = (Instruction) obj;
		if (destination != other.destination)
			return false;
		if (direction != other.direction)
			return false;
		if (origion != other.origion)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Instruction [origion=" + origion + ", destination="
				+ destination + ", direction=" + direction + "]";
	}
	
}
