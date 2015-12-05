package levenshteinDistance;

import java.util.List;

/**
 * 从一个字符串编辑为另一个字符串的整个路径，实际上是指，在利用动态规划
 * 求字符串最短编辑距离的过程中产生的矩阵的坐标序列
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
