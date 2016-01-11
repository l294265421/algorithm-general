package jreadability.news;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import jreadability.news.DOMUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.junit.Test;

public class TestJsoup {

	@Test
	public void testCreateElement() {
		Document document = new Document("");
		Element element = document.createElement("div");
		element.append("<div style=\"\" class=\"position\">温州网</div>");
		System.out.println(element.child(0));
	}
	
	@Test
	public void testElementHasAttr() {
		Document document = new Document("");
		Element element = document.createElement("div");
		element.append("<div style=\"\" class=\"position\">温州网</div>");
		System.out.println(element.child(0).hasAttr("id"));
		System.out.println(element.child(0).attr("id"));
	}
}
