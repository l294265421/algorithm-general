package stm;

/**
 * 将一个节点序列对齐另一个节点序列经过的站点，实际上是指，在利用动态规划
 * 求节点序列最大匹配过程中产生的的矩阵的坐标，比如ij,(i-1)j,i(j-1)
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
