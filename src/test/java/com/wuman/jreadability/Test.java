package com.wuman.jreadability;

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
import java.net.URL;

import jreadability.news.Helper;
import jreadability.news.ReadabilityNews;
import jreadability.news.ReadabilityNews5;

public class Test {

	public static void main(String[] args) throws IOException {
		URL url = new URL("http://intl.ce.cn/qqss/201512/15/t20151215_7559627.shtml");
//		URL url = new URL("http://pic.people.com.cn/n/2015/1203/c1016-27884040.html");
		ReadabilityNews5 readability = new ReadabilityNews5(new File("D:\\test\\40index.html"), "utf-8", "http://www.cnblogs.com/ybwang/archive/2011/10/04/lastOrderTraverse.html", null);
//		ReadabilityNews5 readability = new ReadabilityNews5(url, 5000, null);
		readability.init();
		String cleanHtml = readability.outerHtml();
		Helper.writeStringToFile(cleanHtml, "D:/test/commonNodeEndVersion.html");
	}
	
}
