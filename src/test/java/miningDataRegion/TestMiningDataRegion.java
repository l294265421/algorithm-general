package miningDataRegion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestMiningDataRegion {

	public static void main(String[] args) throws MalformedURLException, IOException {
		Document rootElement = Jsoup.parse(new URL("http://politics.people.com.cn/GB/1027/index.html"), 5000);
		MiningDataRegionForNews miningDataRegion = new MiningDataRegionForNews();
		List<DataRegion> dataRegions = miningDataRegion.MDR(rootElement.body(), 0.75);
		System.out.println(dataRegions.get(0));
	}

}
