package levenshteinDistance;

/**
 * 从一个字符串编辑为另一个字符串经历过的站点，比如ij,(i-1)j,i(j-1)
 * @author liyuncong
 *
 */
public class Station {
	private int i;
	private int j;
	
	public Station(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	@Override
	public String toString() {
		return "Station [i=" + i + ", j=" + j + "]";
	}
	
}
