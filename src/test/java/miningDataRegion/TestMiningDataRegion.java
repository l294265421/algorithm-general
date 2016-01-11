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
		
		Document rootElement = Jsoup.parse(new URL(urlStr), 5000);
		Tools.prepDocument(rootElement);
		MiningDataRegionForNews miningDataRegion = new MiningDataRegionForNews();
		List<DataRegion> dataRegions = miningDataRegion.MDR(rootElement.body(), 0.75);
//		miningDataRegion.filterDataRegion(dataRegions);
		
		int num = dataRegions.size();
		for(int i = 0; i < num; i++) {
//			Helper.writeStringToFile(dataRegions.get(i).toString(),
//					"D:\\test\\" + i + ".html");
			for(Element e : dataRegions.get(i).getElements()) {
				System.out.println(e);
			}
			System.out.println("--------------------------------------");

		}
		System.out.println(dataRegions.size());
	}

}
