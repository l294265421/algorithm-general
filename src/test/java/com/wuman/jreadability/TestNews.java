package com.wuman.jreadability;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

import jreadability.news.ReadabilityNews;

public class TestNews {

	public static void main(String[] args) throws IOException {
		URL url = new URL("http://news.sina.com.cn/c/nd/2015-11-11/doc-ifxkniup6302508.shtml");
		ReadabilityNews readability = new ReadabilityNews(url, 30000);
		
//		InputStream inputStream = new FileInputStream("/home/liyuncong/test/347具赴缅远征军遗骸将归国.html");
//		Reader reader = new InputStreamReader(inputStream, "UTF-8");
//		BufferedReader bufferedReader = new BufferedReader(reader);
//		String html = "";
//		String line = "";
//		while ((line = bufferedReader.readLine()) != null) {
//			html += line + System.lineSeparator();
//		}
//		bufferedReader.close();
		
//		Readability readability = new Readability(html);
		readability.init();
		String cleanHtml = readability.outerHtml();
		
		System.out.println(".........");
		System.out.println(cleanHtml);
		
		OutputStream outputStream = new FileOutputStream("/home/liyuncong/test1.html");
		Writer writer = new OutputStreamWriter(outputStream, "utf-8");
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		bufferedWriter.write(cleanHtml);
		bufferedWriter.close();
	}

}
