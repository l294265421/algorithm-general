package miningDataRegion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tools.Helper;

public class TestMiningDataRegion {

	public static void main(String[] args) throws MalformedURLException, IOException {
		// String urlStr = "http://politics.people.com.cn/GB/1027/index.html";
		String urlStr = "http://finance.people.com.cn/GB/153179/153522/index.html";
		URL url = new URL(urlStr);
		
		MiningDataRegionForNews miningDataRegion = new MiningDataRegionForNews();
		@SuppressWarnings("unchecked")
		List<DataRegion> dataRegions = (List<DataRegion>) miningDataRegion.parse(url);
		
		int num = dataRegions.size();
		for(int i = 0; i < num; i++) {
			for(Element e : dataRegions.get(i).getElements()) {
				System.out.println(e);
				System.out.println(e.getElementsByTag("a").first().attr("abs:href"));;
			}
			System.out.println("--------------------------------------");

		}
		System.out.println(dataRegions.size());
	}

}
