package parse.news.detailpage;

import java.util.HashSet;

/**
 * 如果一个字符串是“非正文内容”标记，则它及它之后的文本节点都不是正文
 * @author liyuncong
 *
 */
public class NoneArticleFlag {
	private static final HashSet<String> flags = new HashSet<>();
	
	static {
		flags.add("相关新闻");
		flags.add("延伸阅读");
		flags.add("更多猛料");
		flags.add("相关阅读");
		flags.add("相关");
	}
	
	private NoneArticleFlag() {
	}
	
	/**
	 * 判断一个字符串是否是“非正文内容”标记
	 * @param candidate
	 * @return
	 */
	public static boolean isFlag(String candidate) {
		boolean result = flags.contains(candidate);
		return result;
	}
}
