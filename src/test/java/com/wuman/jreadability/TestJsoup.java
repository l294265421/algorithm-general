package com.wuman.jreadability;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import jreadability.news.DOMUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;

public class TestJsoup {

	public static void main(String[] args) {
		URL url;
		try {
			url = new URL(
					"http://news.qq.com/a/20151029/008755.htm");
			Document document = Jsoup.parse(url, 10000);
			List<TextNode> textNodeList = DOMUtil.getAllTextNodes(document.body());
			System.out.println(textNodeList.size());
			for (TextNode textNode : textNodeList) {
				String text = textNode.text().trim();
				int len = text.length();
				if (len != 0) {
					System.out.println(textNode.text().trim());
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String test = null;
		String test1 = "32";
		String teString = test + test1;
		System.out.println(teString);
	}
}
