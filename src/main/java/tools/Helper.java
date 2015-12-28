package tools;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Helper {
	/**
	 * 把字符串写进文件里
	 * @param str 字符串
	 * @param fileName 文件路径
	 * @param charsetName 字符集
	 */
	public static void writeStringToFile(String str, String fileName, String charsetName) {
		try (OutputStream outputStream = new FileOutputStream(fileName);
				Writer writer = new OutputStreamWriter(outputStream, charsetName);
				BufferedWriter bufferedWriter = new BufferedWriter(writer);) {
			bufferedWriter.write(str);
			bufferedWriter.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用默认的字符集（utf-8）把字符串写进文件里
	 * @param str 字符串
	 * @param fileName 文件路径
	 */
	public static void writeStringToFile(String str, String fileName) {
		writeStringToFile(str, fileName, "utf-8");
	}
}
