package stm;

import java.util.List;

/**
 * 将一个节点序列对齐另一个节点序列的状态集（对应着操作集），实际上是指，
 * 在利用动态规划求节点序列最大匹配过程中产生的矩阵的坐标序列
 * @author liyuncong
 *
 */
public class EditPath {
	private List<Station> stationList;

	public EditPath(List<Station> stationList) {
		super();
		this.stationList = stationList;
	}

	public List<Station> getStationList() {
		return stationList;
	}

	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}

	@Override
	public String toString() {
		return "EditPath [stationList=" + stationList + "]";
	}

	
}
