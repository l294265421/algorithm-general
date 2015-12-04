package levenshteinDistance;

import java.util.List;

/**
 * 从一个字符串编辑为另一个字符串的整个路径
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
