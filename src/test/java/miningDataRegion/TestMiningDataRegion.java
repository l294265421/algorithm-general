package miningDataRegion;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parse.news.listpage.MiningDataRegion;
import tools.Helper;

public class TestMiningDataRegion {

	public static void main(String[] args) throws MalformedURLException, IOException {
		// String urlStr = "http://politics.people.com.cn/GB/1027/index.html";
		String urlStr = "http://military.people.com.cn/GB/52936/index.html";
		URL url = new URL(urlStr);
		
		MiningDataRegion miningDataRegion = new MiningDataRegion();
		@SuppressWarnings("unchecked")
		List<DataRegion> dataRegions = (List<DataRegion>) miningDataRegion.
			parse(new File("D:\\test\\本网原创--军事--人民网.htm"), 
					"utf-8", urlStr);
		
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
