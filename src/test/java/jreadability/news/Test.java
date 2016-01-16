package jreadability.news;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import jreadability.news.Helper;
import jreadability.news.ReadabilityNews;
import jreadability.news.ReadabilityNews5;

public class Test {

	@org.junit.Test
	public void test1() throws IOException {
		ReadabilityNews5 readability = new ReadabilityNews5(
				new File("D:\\program\\bigdata\\people.com.cn\\finance.people.com.cn\\0a5e49e68fb6ec94a8659e76a52be4e1.html"), 
				"utf-8", 
				"http://www.cnblogs.com/ybwang/archive/2011/10/04/lastOrderTraverse.html", 
				null);
		readability.init();
		String cleanHtml = readability.outerHtml();
		Helper.writeStringToFile(cleanHtml, "D:/test/commonNodeEndVersion.html");
	}
	
	@org.junit.Test
	public void test2() throws IOException {
		URL url = new URL("http://politics.people.com.cn/n1/2015/1231/c1001-28000609.html");
		ReadabilityNews5 readability = new ReadabilityNews5(url, 5000, null);
		readability.init();
		String cleanHtml = readability.outerHtml();
		Helper.writeStringToFile(cleanHtml, "D:/test/commonNodeEndVersion.html");
	}
	
	@org.junit.Test
	public void test3() throws IOException {
		String dirStr = "D:\\test\\news.66wz.com\\";
		File dir = new File(dirStr);
		File[] children = dir.listFiles();
		System.out.println(children.length);
		for (File child : children) {
			if (child.isDirectory()) {
				continue;
			}
			ReadabilityNews5 readability = new ReadabilityNews5(
					child, 
					"gbk", 
					"http://www.cnblogs.com/ybwang/archive/2011/10/04/lastOrderTraverse.html", 
					null);
			readability.init();
			String cleanHtml = readability.outerHtml();
			Helper.writeStringToFile(cleanHtml, dirStr + child.getName().substring(0, 5) + ".html");
		}
	}
}
