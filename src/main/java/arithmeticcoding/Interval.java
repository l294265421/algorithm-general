package arithmeticcoding;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 区间，比如[0, 1)
 * @author liyuncong
 *
 */
public class Interval {
	private BigDecimal left;
	private boolean leftInclusive;
	private BigDecimal right;
	private boolean rightInclusive;
	
	public BigDecimal getLeft() {
		return left;
	}

	public void setLeft(BigDecimal left) {
		this.left = left;
	}

	public boolean isLeftInclusive() {
		return leftInclusive;
	}

	public void setLeftInclusive(boolean leftInclusive) {
		this.leftInclusive = leftInclusive;
	}

	public BigDecimal getRight() {
		return right;
	}

	public void setRight(BigDecimal right) {
		this.right = right;
	}

	public boolean getRightInclusive() {
		return rightInclusive;
	}

	public void setRightInclusive(boolean rightInclusive) {
		this.rightInclusive = rightInclusive;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
