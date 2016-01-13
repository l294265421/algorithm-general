package webpagecharsetrecognition;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parsewebpage.ParseWebPage;

/**
 * 尝试从webpage文档中找出文档的字符编码集。
 * @author liyuncong
 *
 */
public class WebPageCharsetRecognition extends ParseWebPage{
	/**
	 * 尝试从webpage文档中找出文档的字符编码集
	 * @param webPage
	 * @return 如果没有找到，就返回空串
	 */
	private String recognize(Document webPage) {
		String charsetName = "";
		Element head = webPage.getElementsByTag("head").first();
		if (head != null) {
			charsetName = recognize(head);
		}
		return charsetName;
	}
	
	/**
	 * 尝试从head标签中获得网页的字符编码集。
	 * 现在已经发现的包含字符编码集的meta标签类型：
	 * 1.<meta charset="gb2312" />
	 * 2.<meta http-equiv="Content-Type" 
	 * content="text/html; charset=utf-8" />
	 * @param head
	 * @return 如果没有找到，返回空串
	 */
	private String recognize(Element head) {
		String charsetName = "";
		Elements metas = head.getElementsByTag("meta");
		int size = metas.size();
		for (int i = 0; i < size; i++) {
			Element meta = metas.get(i);
			if (!(charsetName = meta.attr("charset")).equals("")) {
				break;
			}
			String contentAttr = meta.attr("content");
			int indexOfCharset = -1;
			if ((indexOfCharset = contentAttr.indexOf("charset")) != -1) {
				charsetName = contentAttr.substring(
						indexOfCharset + 8);
				break;
			}
			
		}
		return charsetName;
	}

	@Override
	protected Object innerParse(Document webpage) {
		return recognize(webpage);
	}
}
