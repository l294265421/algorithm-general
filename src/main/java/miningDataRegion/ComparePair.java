package miningDataRegion;

import java.util.Arrays;

public class ComparePair {
	private int[] first;
	private int[] second;
	private double similarity;
	public ComparePair(int[] first, int[] second, double similarity) {
		super();
		this.first = first;
		this.second = second;
		this.similarity = similarity;
	}
	public int[] getFirst() {
		return first;
	}
	public void setFirst(int[] first) {
		this.first = first;
	}
	public int[] getSecond() {
		return second;
	}
	public void setSecond(int[] second) {
		this.second = second;
	}
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	@Override
	public String toString() {
		return "ComparePair [first=" + Arrays.toString(first) + ", second="
				+ Arrays.toString(second) + ", similarity=" + similarity + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(first);
		result = prime * result + Arrays.hashCode(second);
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
		if (!Arrays.equals(first, other.first))
			return false;
		if (!Arrays.equals(second, other.second))
			return false;
		return true;
	}
}
