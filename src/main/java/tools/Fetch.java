package tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class Fetch {
	public static String readLocalFile(String filePath) {
		return readLocalFile(filePath, DefaultValue.FROMCHARSET);
	}

	/**
	 * 读取一个本地文本文件中的所有文本
	 * 
	 * @param filePath
	 * @param charset
	 * @return
	 */
	public static String readLocalFile(String filePath, Charset charset) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Reader reader = new InputStreamReader(inputStream, charset);
		return readerToString(reader);
	}

	private static String inputstreamToString(InputStream inputStream) {
		return inputstreamToString(inputStream, DefaultValue.FROMCHARSET);
	}
	
	private static String inputstreamToString(InputStream inputStream, 
			Charset charset) {
		Reader reader = new InputStreamReader(inputStream, charset);
		return readerToString(reader);
	}
	
	private static String readerToString(Reader reader) {
		StringBuffer result = new StringBuffer();
		char[] buffer = new char[4096];
		int size = 0;
		try {
			while ((size = reader.read(buffer)) != -1) {
				if (size == 4096) {
					result.append(buffer);
				} else {
					for (int i = 0; i < size; i++) {
						result.append(buffer[i]);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * 下载url指定的文本文件
	 * @param url
	 * @return
	 */
	public static String readByURL(URL url) {
		String result = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setInstanceFollowRedirects(true);
			conn.connect();
			InputStream inputStream = conn.getInputStream();
			result = inputstreamToString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		URL url = null;
		try {
			url = new URL("http://baike.baidu.com/link?url=sJGNAMQY4Tjow6WwdDHmPcs1q-e9BAR3QOUCqrtoSU0Kogxkc5OESAYCxDvCH_uVkBEtsgCnK8jzQYlvvWa2J_");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println(Fetch.readByURL(url));
	}
}
