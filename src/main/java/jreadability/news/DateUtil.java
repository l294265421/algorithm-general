package jreadability.news;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	/**
	 * 1. 日期时间都有时，月日和时分是必须有的 2. 只有日期时，可以没年 3. 只有时分秒，可以没秒
	 */
	// 年
	private final static String dateTimeRegex = "((\\d{1,4}[-|\\/|年|\\.])?"
	// 月
			+ "\\d{1,2}[-|\\/|月|\\.]"
			// 日
			+ "\\d{1,2}([日|号])?(\\s)*"
			// 时
			+ "(\\d{1,2}([点|时])?((:)?"
			// 分
			+ "\\d{1,2}(分)?((:)?"
			// 秒
			+ "\\d{1,2}(秒)?" + ")?))(\\s)*(PM|AM)?)" + "|"
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
			+ "\\d{1,2}(秒)?" + ")?))(\\s)*(PM|AM)?)";

	/**
	 * 验证candidate是否是日期或者时间
	 * @param candidate
	 * @return
	 */
	public static boolean validateDateTime(String candidate) {

		String dateTime = null;
		Pattern pattern = Pattern.compile(dateTimeRegex);
		Matcher matcher = pattern.matcher(candidate);
		if (matcher.find()) {
			dateTime = matcher.group();
		}
		if (candidate.equals(dateTime)) {
			return true;
		}
		
		return false;
	}
	
}
