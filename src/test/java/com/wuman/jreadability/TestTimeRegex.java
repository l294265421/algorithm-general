package com.wuman.jreadability;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTimeRegex {

	public static void main(String[] args) {
		/**
		 * 1. 日期时间都有时，月日和时分是必须有的
		 * 2. 只有日期时，可以没年
		 * 3. 只有时分秒，可以没秒
		 */
		// 年
		String regex = "((\\d{1,4}[-|\\/|年|\\.])?"
				// 月
				+ "\\d{1,2}[-|\\/|月|\\.]"
				// 日
				+ "\\d{1,2}([日|号])?(\\s)*"
				// 时
				+ "(\\d{1,2}([点|时])?((:)?"
				// 分
				+ "\\d{1,2}(分)?((:)?"
				// 秒
				+ "\\d{1,2}(秒)?"
				+ ")?))(\\s)*(PM|AM)?)" + "|"
				// 只有年月日
				+ "((\\d{1,4}[-|\\/|年|\\.])?"
				// 月
				+ "\\d{1,2}[-|\\/|月|\\.]"
				// 日
				+ "\\d{1,2}([日|号])?(\\s)*)" + "|"
				// 只有时分秒
				// 时
				+ "((\\d{1,2}([点|时])?((:)?"
				// 分
				+ "\\d{1,2}(分)?((:)?"
				// 秒
				+ "\\d{1,2}(秒)?"
				+ ")?))(\\s)*(PM|AM)?)";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher("");
		if (matcher.find()) {
			System.out.println(matcher.group());
		}
	}

}
