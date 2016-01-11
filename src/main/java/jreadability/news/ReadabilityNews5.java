package jreadability.news;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.sun.xml.internal.bind.v2.TODO;

/**
 * 抽取新闻内容，以易于阅读的方式展示出来；
 * 抽取行为以新闻标题和新闻正文为核心，因为就
 * 目前掌握的技术来说，它们是最容易获得的；
 * 这个“抽取新闻内容”实现可行的前提是，文档中
 * 一定存在新闻标题和新闻正文。
 * @author liyuncong
 *
 */
public class ReadabilityNews5 {

	private static final String CONTENT_SCORE = "readabilityContentScore";

	// A HTML Document
	private final Document mDocument;
	private String newsTitle;
	private boolean success;
	private Document outDocument;

	/**
	 * 
	 * @param html 待解析的HTML
	 */
	public ReadabilityNews5(String html, String newsTitle) {
		super();
		mDocument = Jsoup.parse(html);
		this.newsTitle = newsTitle;
	}

	/**
	 * 
	 * @param html 待解析的HTML
	 * @param baseUri The URL where the HTML was retrieved from. Used to resolve
	 *            relative URLs to absolute URLs, that occur before the HTML
	 *            declares a <base href> tag.
	 */
	public ReadabilityNews5(String html, String baseUri, String newsTitle) {
		super(); 
		mDocument = Jsoup.parse(html, baseUri);
		this.newsTitle = newsTitle;
	}

	/**
	 * 
	 * @param in file to load HTML from
	 * @param charsetName 待解析的HTML的字符集
	 * @param baseUri The URL where the HTML was retrieved from. Used to resolve
	 *            relative URLs to absolute URLs, that occur before the HTML
	 *            declares a <base href> tag.
	 * @throws IOException
	 */
	public ReadabilityNews5(File in, String charsetName, String baseUri, String newsTitle)
			throws IOException {
		super();
		mDocument = Jsoup.parse(in, charsetName, baseUri);
		this.newsTitle = newsTitle;
	}

	/**
	 * 
	 * @param url URL to fetch (with a GET). The protocol must be http or https.
	 * @param timeoutMillis
	 *            Connection and read timeout, in milliseconds. If exceeded,
	 *            IOException is thrown.
	 * @throws IOException
	 */
	public ReadabilityNews5(URL url, int timeoutMillis, String newsTitle) throws IOException {
		super();
		mDocument = Jsoup.parse(url, timeoutMillis);
		this.newsTitle = newsTitle;
	}

	public ReadabilityNews5(Document doc, String newsTitle) {
		super();
		mDocument = doc;
		this.newsTitle = newsTitle;
	}

	/**
	 * 处理分为两步：
	 * 1.获得包含想要内容的节点
	 * 2.删除获得节点中不要想的内容
	 * @param preserveUnlikelyCandidates
	 */
	private void init(boolean preserveUnlikelyCandidates) {
		Helper.writeStringToFile(mDocument.outerHtml(), "D:/test/news.html", "gbk");
		// 第一部分
		
    	Element body = mDocument.body();

    	// 预处理
     	prepDocument();
        
		// 克隆文档body
        Element bodyInUse = body.clone();
		
		// 确定新闻title
        Node newsTitleNode = null;
        if (this.newsTitle == null) {
    		// 获取网页title
    		String articleTitle = getArticleTitle();

    		// 获取新闻标题节点
    		newsTitleNode = getNewsTitle(articleTitle, bodyInUse);
		} else {
			// 获取新闻标题节点
			newsTitleNode = getNewsTitle(bodyInUse, newsTitle);
		}
        // 如果没有获取新闻标题节点，抽取失败
        if (newsTitleNode == null) {
			return;
		}
		
		// 确定所有新闻正文的最近公共父节点
     	Element newsContentNode = grabArticle(bodyInUse);
     	// 如果没有获得包含正文的节点，抽取失败
     	if (newsContentNode == null) {
			return;
		}
		
		//　确定新闻title和新闻正文的最近父节点的最近父节点
		// 实现思路：通过遍历分别获取到两条链表之后再比较求出公共节点
     	List<Node> newsTitleNodeParentList = new LinkedList<>();
     	Node newsTitleNodeParent = newsTitleNode.parent();
     	while (newsTitleNodeParent != bodyInUse) {
			newsTitleNodeParentList.add(newsTitleNodeParent);
			newsTitleNodeParent = newsTitleNodeParent.parent();
		}
     	
     	List<Node> newsContentNodeParentList = new LinkedList<>();
     	Node newsContentNodeParent = newsContentNode.parent();
     	while (newsContentNodeParent != bodyInUse) {
     		newsContentNodeParentList.add(newsContentNodeParent);
     		newsContentNodeParent = newsContentNodeParent.parent();
		}
     	
     	Node commoNode = null;
     	searchEnd: for (Node node1 : newsContentNodeParentList) {
			for (Node node2 : newsTitleNodeParentList) {
				if (node1 == node2) {
					commoNode = node1;
					break searchEnd;
				}
			}
		}
     	if (commoNode == null) {
     		// 说明要么它们的公共父节点是body，
     		// 要么newsTitleNode是newsContentNode的子元素；
     		// 这里只需要判断newsTitleNode是否是newsContentNode的子元素，
     		// 如果不是，它们的公共父节点是body；
     		boolean isParent = DOMUtil.isChild(newsContentNode, newsTitleNode);
     		if (isParent) {
				commoNode = newsContentNode;
			} else {
				commoNode = bodyInUse;
			}
		}
     	
     	// 测试
     	boolean isParentTest1 = DOMUtil.isChild(bodyInUse, newsTitleNode);
     	boolean isParentTest2 = DOMUtil.isChild(bodyInUse, mDocument);
     	Document document = null;
		try {
			document = Jsoup.parse(new File("newsWebPageHead.html"), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
     	if (commoNode == bodyInUse) {
			document.body().html(commoNode.outerHtml());
		} else {
			document.body().appendChild(commoNode);
		}
     	 Helper.writeStringToFile(document.outerHtml(), "D:/test/commonNode.html");
     	
     	// 第二部分
     	
     	// 1.newsTitleNode不是newsContentNode的子元素
     	// 在dom树中，一个节点左边的兄弟节点出现该节点的前面（在文本中），
     	// 这也就意味着，从commonNode节点到newsTitleNode节点的路径上的的节点
     	// 所有的左兄弟节点都需要被删除，从commonNode节点到newsContentNode
     	// 节点的路径上的节点的所有的右兄弟节点都需要被删除；
     	// 2. newsTitleNode是newsContentNode的子元素
     	// 在dom树中，一个节点左边的兄弟节点出现该节点的前面（在文本中），
     	// 这也就意味着，从commonNode节点到newsTitleNode节点的路径上的的节点
     	// 所有的左兄弟节点都需要被删除;
     	if (commoNode != newsContentNode) {
     		// 构造从commonNode节点到newsContent节点的路径(重用了前面的对象)
     		newsContentNodeParentList.clear();
     		newsContentNodeParentList.add(newsContentNode);
         	newsContentNodeParent = newsContentNode.parent();
         	while (newsContentNodeParent != commoNode) {
         		newsContentNodeParentList.add(newsContentNodeParent);
         		newsContentNodeParent = newsContentNodeParent.parent();
			}
         	// 删除相应元素
         	DOMUtil.deleteLeftOrRight(newsContentNodeParentList, "right");
		}
     	// 构造从commonNode节点到newsTitleNode节点的路径(重用了前面的对象)
     	newsTitleNodeParentList.clear();
     	newsTitleNodeParentList.add(newsTitleNode);
     	newsTitleNodeParent = newsTitleNode.parent();
     	while (newsTitleNodeParent != commoNode) {
			newsTitleNodeParentList.add(newsTitleNodeParent);
			newsTitleNodeParent = newsTitleNodeParent.parent();
		}
     	// 删除相应元素
     	DOMUtil.deleteLeftOrRight(newsTitleNodeParentList, "left");
     	
     	// 到这个时候，文档已经比较干净了
     	// 测试；测试结果显示：该算法对组图也有效，真是surprise
     	Document document1 = null;
		try {
			document1 = Jsoup.parse(new File("newsWebPageHead.html"), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
     	if (commoNode == bodyInUse) {
			document1.body().html(commoNode.outerHtml());
		} else {
			document1.body().appendChild(commoNode);
		}
     	 Helper.writeStringToFile(document1.outerHtml(), "D:/test/commonNode1.html");
     	
     	// 寻找最长文本节点
     	Node longestTextNode = getLongestTextNode(newsContentNode);
     	List<Node> seperateList = new LinkedList<Node>();
     	seperateList.add(longestTextNode);
     	Node longestTextNodeParent = longestTextNode.parent();
     	while (longestTextNodeParent != commoNode) {
			seperateList.add(longestTextNodeParent);
			longestTextNodeParent = longestTextNodeParent.parent();
		}
     	
     	// 删除在从commonNode节点到newsTitleNode节点的路径和
 		// 从commonNode节点到newsContent节点的路径之间的区域
     	// 中会影响页面显示且不想要的节点;
     	// 这部分等于是对这个区域的遍历；
     	if (commoNode != newsContentNode) {
     		// 删除垃圾a标签；
			// 什么样的标签是垃圾a标签呢？
			// 1. 锚文本长度小于3的
			// 2. 包含非文本节点的子节点的
     		// 3. 上两条是为了这个目的，删除锚文本不是来源的所有a标签
			// 基于a标签，我是基于这样的认识得到上面的结论：
			// 1. a标签里面不会再嵌套a标签
     		DOMUtil.travers(newsTitleNodeParentList, newsContentNodeParentList, new NodeOperate() {
				
				@Override
				public void action(Node node) {
					String nodeName = node.nodeName();
					if (nodeName.equals("a")) {
						Element temp1 = (Element) node;
						String temp1Str = temp1.text().replace(" ", "");
						if (temp1Str.length() < 3 || temp1.childNodes().size() != 1) {
							temp1.remove();
						}
					}
				}
			});
     		
     	// 删除所有iframe元素
     	DOMUtil.deleteAllElementByTagName(newsTitleNodeParentList, 
     			newsContentNodeParentList, "iframe");
     	// 删除所有input元素
     	DOMUtil.deleteAllElementByTagName(newsTitleNodeParentList, 
     			newsContentNodeParentList, "input");
		} else {
     		// 删除垃圾a标签；
			// 什么样的标签是垃圾a标签呢？
			// 1. 锚文本长度小于3的
			// 2. 包含非文本节点的子节点的
     		// 3. 上两条是为了这个目的，删除锚文本不是来源的所有a标签
			// 基于a标签，我是基于这样的认识得到上面的结论：
			// 1. a标签里面不会再嵌套a标签
     		DOMUtil.travers(newsTitleNodeParentList, seperateList, new NodeOperate() {
				
				@Override
				public void action(Node node) {
					String nodeName = node.nodeName();
					if (nodeName.equals("a")) {
						Element temp1 = (Element) node;
						String temp1Str = temp1.text().replace(" ", "");
						if (temp1Str.length() < 2 || temp1.childNodes().size() != 1) {
							temp1.remove();
						}
					}
				}
			});
     		
     	// 删除所有iframe元素
     	DOMUtil.deleteAllElementByTagName(newsTitleNodeParentList, 
     			seperateList, "iframe");
     	// 删除所有input元素
     	DOMUtil.deleteAllElementByTagName(newsTitleNodeParentList, 
     			seperateList, "input");
		}
     	
     	// 测试；
     	Document document2 = null;
		try {
			document2 = Jsoup.parse(new File("newsWebPageHead.html"), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
     	if (commoNode == bodyInUse) {
			document2.body().html(commoNode.outerHtml());
		} else {
			document2.body().appendChild(commoNode);
		}
     	Helper.writeStringToFile(document2.outerHtml(), "D:/test/commonNode2.html");
     	
		// 删掉废弃标记词汇及其后面的所有文本节点；
     	// http://it.people.com.cn/n/2015/1211/c1009-27913081.html 这个
     	// 网页说明了这一步的必要性；
     	removeByNoneArticleTextNode(seperateList, newsContentNode); //调用方法，删除垃圾节点
      	
      	Document document3 = null;
      	try {
      		document3 = Jsoup.parse(new File("newsWebPageHead.html"), "utf-8");
      	} catch (IOException e) {
      		e.printStackTrace();
      	}
       	if (commoNode == bodyInUse) {
      		document3.body().html(commoNode.outerHtml());
      	} else {
      		document3.body().appendChild(commoNode);
      	}
       	Helper.writeStringToFile(document3.outerHtml(), "D:/test/commonNode3.html");
       	
       	this.success = true;
     	outDocument = document3;
	}
	
	/**
	 * 获得node所有后代文本节点中最长的那个节点;
	 * {@link TODO}可以继续优化，用最长的节点块的第一个节点代替，
	 * 来自论文——《基于行块分布函数的通用网页正文抽取》的启发
	 * @param node
	 * @return
	 */
	public Node getLongestTextNode(Node node) {
		List<TextNode> textNodeList = DOMUtil.getAllTextNodes(node);
		Node longestTextNode = null;
		int maxLen = 0;
		for (TextNode textNode : textNodeList) {
			String candidate = textNode.text().trim();
			int len = candidate.length();
			if (len > maxLen) {
				maxLen = len;
				longestTextNode = textNode;
			} 
		}
		return longestTextNode;
	}
	
	/**
	 * 去掉所有不可能是正文的内容
	 * @param html html源码
	 * @return
	 */
	private String preProcess(String html) {
		html = html.replaceAll("<.*?>", "");
		return html;
	}

	/**
	 * Runs readability.
	 */
	public final void init() {
		init(false);
	}

	/**
	 * Get the combined inner HTML of all matched elements.
	 * 
	 * @return
	 */
	public final String html() {
		return mDocument.html();
	}

	/**
	 * Get the combined outer HTML of all matched elements.
	 * 
	 * 如果解析失败，返回空字符串；否则返回解析成功后的文档的字符串表示
	 * @return
	 */
	public final String outerHtml() {
		if (this.success) {
			return this.outDocument.outerHtml();
		}
		return "";
	}
	
	

	/**
	 * Get the article title as an H1. Currently just uses document.title, we
	 * might want to be smarter in the future.
	 * （获得文章的标题作为H1元素。现在只是使用document.title，将来也许会使用更好的 方式）
	 * 
	 * @return
	 */
	protected String getArticleTitle() {
		String htmlTitle = mDocument.title();
		return htmlTitle;
	}

	/**
	 * 创建一个节点，并赋值给该节点一个文本节点
	 * 
	 * @param tagName
	 * @param text
	 * @return
	 */
	public Element createElement(String tagName, String text) {
		Element element = mDocument.createElement(tagName);
		element.html(text);
		return element;
	}

	/**
	 * 获得新闻标题节点
	 * @param title　网页标题
	 * @param body　网页body元素
	 * @return
	 */
	public Node getNewsTitle(String title, Element body) {
		List<TextNode> textNodeList = DOMUtil.getAllTextNodes(body);
		Node newsTitleNode = null;
		for (TextNode textNode : textNodeList) {
			String candidate = textNode.text().trim();
			int len = candidate.length();
			if (len != 0) {
				// 这个地方还需要改进，得到text可能也只是新闻标题的一部分；
				// 可以统计得到：
				// 网页标题以新闻标题开头，新闻标题长度不短于网页标题的一半
				if (((title.startsWith(candidate) 
						&& candidate.length() >= title.length() / 2) ||
						// 网页标题包含新闻标题，新闻标题长度不短于网页标题的三分之一
						(title.contains(candidate) && 
						candidate.length() >= title.length() / 3))
						// 新闻标题长度大于等于５
						&& (candidate.length() >= 5)) {
					newsTitleNode = textNode;
					break;
				}
			}
		}
		return newsTitleNode;
	}
	
	/**
	 * 获取新闻标题节点
	 * @param body　网页body元素
	 * @param newsTitle 新闻标题
	 * @return
	 */
	public Node getNewsTitle(Element body, String newsTitle) {
		List<TextNode> textNodeList = DOMUtil.getAllTextNodes(body);
		Node newsTitleNode = null;
		for (TextNode textNode : textNodeList) {
			String candidate = textNode.text().trim();
			int len = candidate.length();
			if (len != 0) {
				if (candidate.equals(newsTitle)) {
					newsTitleNode = textNode;
					break;
				}
			}
		}
		return newsTitleNode;
	}
	
	/**
	 * 通过“非正文内容”标记删除文本节点
	 */
	private void removeByNoneArticleTextNode(List<Node> list, Node contentNode) {
		final List<Node> noneArticleFlagNodeList = new LinkedList<Node>();//定义一个List，用于存放非文本节点
	    DOMUtil.travers(list,  new NodeOperate(){ //遍历seperateList，

		@Override
		public void action(Node node) {
			if( node instanceof TextNode){// 如果node是TextNode
				//noneArticleFlagNodeList.add(node);
				String text=((TextNode) node).text().trim();// 取出textnode里面的值
				if(NoneArticleFlag.isFlag(text)){                           //如果text是flags里面的内容
					noneArticleFlagNodeList.add(node);//将text的节点添加到分文吧列表里面去
				}
		     }
			}
		  });

		for (Node node1 : noneArticleFlagNodeList) {//对获得的list进行遍历，
		Node nodeParent= node1.parent();//           获得node节点的parent节点
		List<Node> flagNode= new LinkedList<Node>();// textNode到 newsContentNode的路径
			while(nodeParent!=contentNode){
				flagNode.add(nodeParent);    //如果不是，则继续添加节点
			    nodeParent= nodeParent.parent();	
			}
		       DOMUtil.deleteLeftOrRight(flagNode, "right");// 调用方法删除路径的右边节点。
		 }
		
		for (Node node : noneArticleFlagNodeList) {// 删除非文本节点列表里面的剩余节点
		node.remove();	
		}
		}

	/**
	 * 在node的所有后代文本节点范围内寻找发表时间
	 * 
	 * @param element jied节点树的根节点
	 * @return 新闻发表时间；如果没找到就返回null
	 */
	public String getNewsPublishTime(Node node) {
		List<TextNode> textNodeList = DOMUtil.getAllTextNodes(node);

		String publishTime = null;

		for (TextNode textNode : textNodeList) {
			String text = textNode.text().trim();
			int len = text.length();
			if (len != 0) {
				boolean isPublishTime = DateUtil.validateDateTime(text);
				if (isPublishTime) {
					publishTime = text;
					break;
				}
			}
		}

		return publishTime;
	}

	/**
	 * 在一系列文本节点范围内寻找新闻来源
	 * 
	 * @param start
	 *            起点文本
	 * @param end
	 *            终点文本
	 * @param element
	 *            包好起点文本、终点文本的节点树
	 * @return 新闻来源；如果没找到就返回null
	 */
	public String getNewsSource1(String start, String end, Document element) {
		List<TextNode> textNodeList = DOMUtil.getAllTextNodes(element.body());

		String source = null;

		boolean isStart = false; // 起点判断
		boolean isFindSource = false;
		for (TextNode textNode : textNodeList) {
			String text = textNode.text().trim();
			int len = text.length();
			if (len != 0) {
				// 如果还没开始,就仅仅验证是否这里就是起点
				if (!isStart) {
					if (text.equals(start)) {
						isStart = true;
					}
					continue;
				}
				// 如果还没有找到“来源”二字，就判断这个文本节点是否以这两个字开头的
				if (!isFindSource) {
					if (text.startsWith("来源")) {
						isFindSource = true;
					} else {
						continue;
					}

					// 说明 得到的形式是类似这样的 来源：具体来源
					if (len > 5) {
						source = text.substring(3).trim();
						break;
					} else {
						continue;
					}

				}
				// 找到了来源于二字，但是还没找到具体来源于
				source = text;
				break;
			}
		}

		return source;
	}
	
	/**
	 * 在一系列文本节点范围内寻找新闻来源
	 * 
	 * @param start
	 *            起点文本
	 * @param end
	 *            终点文本
	 * @param element
	 *            包好起点文本、终点文本的节点树
	 * @return 新闻来源；如果没找到就返回null
	 */
	public String getNewsSource2(String start, String end, Document element) {
		List<TextNode> textNodeList = DOMUtil.getAllTextNodes(element.body());
		
		CollectedSources collectedSources = CollectedSources.getInstance();
		
		String source = null;

		boolean isStart = false; // 起点判断
		for (TextNode textNode : textNodeList) {
			String text = textNode.text().trim();
			int len = text.length();
			if (len != 0) {
				// 如果还没开始,就仅仅验证是否这里就是起点
				if (!isStart) {
					if (text.equals(start)) {
						isStart = true;
					}
					continue;
				}
				// 在已经搜集到的来源里查找是否包含该文本，以确定它是否为来源
				boolean isSource = collectedSources.isSource(text);
				if (isSource) {
					source = text;
					break;
				} else {
					continue;
				}
			}
		}

		return source;
	}

	/**
	 * Prepare the HTML document for readability to scrape it. This includes
	 * things like stripping javascript, CSS, and handling terrible markup.
	 * (为readability准备HTML document。包括去掉javascript, CSS和处理糟糕的标记,
	 * 还有，
	 * 1. 把使用不当的div标签换成P标签
	 * 2. 删除所有不会显示在页面上的内容
	 * )
	 */
	protected void prepDocument() {
		/* Remove all scripts */
		// 移除所有脚本
		Elements elementsToRemove = mDocument.getElementsByTag("script");
		for (Element script : elementsToRemove) {
			// Remove (delete) this node from the DOM tree. If this node has
			// children, they are also removed.
			script.remove();
		}

		/* Remove all stylesheets */
		// 移除所有样式表
		elementsToRemove = getElementsByTag(mDocument.head(), "link");
		for (Element styleSheet : elementsToRemove) {
			// <link type="text/css" rel="stylesheet"
			// href="/bundles/blog-common.css?v=TdLMZRHMQfitXmNZ7SFinI4hbzrT2-_1PvIqhhWnsbI1"
			// />
			if ("stylesheet".equalsIgnoreCase(styleSheet.attr("rel"))) {
				styleSheet.remove();
			}
		}

		/* Remove all style tags in head */
		// 移除所有样式标签
		elementsToRemove = mDocument.getElementsByTag("style");
		for (Element styleTag : elementsToRemove) {
			styleTag.remove();
		}

		/* Turn all double br's into p's */
		// 把所有double br变为p
		/*
		 * TODO: this is pretty costly as far as processing goes. Maybe optimize
		 * later.（这是十分耗时的。也许之后会进行优化）
		 */
		mDocument.body().html(
				mDocument.body().html()
						.replaceAll(Patterns.REGEX_REPLACE_BRS, "</p><p>")
						.replaceAll(Patterns.REGEX_REPLACE_FONTS, "<$1span>"));
		
		/**
		 * turn divs into P tags where they
		 * have been used inappropriately (as in, where they contain no other
		 * block level elements.) （把使用不当的div标签
		 * 换成P标签（比如，它们没有包含其它块级元素）） Note: Assignment from index for performance.
		 * See http://www.peachpit.com/articles/article.aspx?p=31567&seqNum=5
		 * TODO: Shouldn't this be a reverse traversal?
		 **/
		for (Element node : mDocument.getAllElements()) {
			/*
			 * Turn all divs that don't have children block level elements into
			 * p's （把所有不包含块级孩子节点的div变为p）
			 */
			if ("div".equalsIgnoreCase(node.tagName())) {
				Matcher matcher = Patterns
						.get(Patterns.RegEx.DIV_TO_P_ELEMENTS).matcher(
								node.html());
				if (!matcher.find()) {
					dbg("Alternating div to p: " + node);
					try {
						// <div id="blog-news"></div> 变为 <p
						// id="blog-news"></p>
						node.tagName("p");
					} catch (Exception e) {
						dbg("Could not alter div to p, probably an IE restriction, reverting back to div.",
								e);
					}
				}
			}
		}
		
		// 删除所有不会显示在页面上的内容
		DOMUtil.traverse(mDocument, new NodeOperate() {
			
			@Override
			public void action(Node node) {
				if (node.getClass() == Element.class) {
					Element element = (Element) node;
					if (element.attr("style").equals("display:none;")) {
						element.remove();
					}
				}
			}
		});
	}

	/**
	 * Prepare the article node for display. Clean out any inline styles,
	 * iframes, forms, strip extraneous &lt;p&gt; tags, etc.
	 * (准备文本节点，用于显示。清除所有内部的样式，iframes, forms,和外部的&lt;p&gt;标签等)
	 * 
	 * @param articleContent
	 */
	private void prepArticle(Element articleContent) {
		cleanStyles(articleContent);
		killBreaks(articleContent);

		/* Clean out junk from the article content */
		clean(articleContent, "form");
		clean(articleContent, "object");
		clean(articleContent, "h1");
		/**
		 * If there is only one h2, they are probably using it as a header and
		 * not a subheader, so remove it since we already have a header.
		 */
		if (getElementsByTag(articleContent, "h2").size() == 1) {
			clean(articleContent, "h2");
		}
		clean(articleContent, "iframe");

		cleanHeaders(articleContent);

		/*
		 * Do these last as the previous stuff may have removed junk that will
		 * affect these
		 */
		cleanConditionally(articleContent, "table");
		cleanConditionally(articleContent, "ul");
		cleanConditionally(articleContent, "div");

		/* Remove extra paragraphs */
		Elements articleParagraphs = getElementsByTag(articleContent, "p");
		for (Element articleParagraph : articleParagraphs) {
			int imgCount = getElementsByTag(articleParagraph, "img").size();
			int embedCount = getElementsByTag(articleParagraph, "embed").size();
			int objectCount = getElementsByTag(articleParagraph, "object")
					.size();

			if (imgCount == 0 && embedCount == 0 && objectCount == 0
					&& isEmpty(getInnerText(articleParagraph, false))) {
				articleParagraph.remove();
			}
		}

		try {
			articleContent.html(articleContent.html().replaceAll(
					"(?i)<br[^>]*>\\s*<p", "<p"));
		} catch (Exception e) {
			dbg("Cleaning innerHTML of breaks failed. This is an IE strict-block-elements bug. Ignoring.",
					e);
		}
	}

	/**
	 * Initialize a node with the readability object. Also checks the
	 * className/id for special names to add to its score.
	 * （用一个readability对象初始化一个节点。同时也检查类名/id，对特定的名字， 给节点增加相应的分数）
	 * 
	 * @param node
	 */
	private static void initializeNode(Element node) {
		/**
		 * Set an attribute value on this element. If this element already has
		 * an attribute with the key, its value is updated; otherwise, a new
		 * attribute is added.
		 */
		node.attr(CONTENT_SCORE, Integer.toString(0));

		String tagName = node.tagName();
		if ("div".equalsIgnoreCase(tagName)) {
			incrementContentScore(node, 5);
		} else if ("pre".equalsIgnoreCase(tagName)
				|| "td".equalsIgnoreCase(tagName)
				|| "blockquote".equalsIgnoreCase(tagName)) {
			incrementContentScore(node, 3);
		} else if ("address".equalsIgnoreCase(tagName)
				|| "ol".equalsIgnoreCase(tagName)
				|| "ul".equalsIgnoreCase(tagName)
				|| "dl".equalsIgnoreCase(tagName)
				|| "dd".equalsIgnoreCase(tagName)
				|| "dt".equalsIgnoreCase(tagName)
				|| "li".equalsIgnoreCase(tagName)
				|| "form".equalsIgnoreCase(tagName)) {
			incrementContentScore(node, -3);
		} else if ("h1".equalsIgnoreCase(tagName)
				|| "h2".equalsIgnoreCase(tagName)
				|| "h3".equalsIgnoreCase(tagName)
				|| "h4".equalsIgnoreCase(tagName)
				|| "h5".equalsIgnoreCase(tagName)
				|| "h6".equalsIgnoreCase(tagName)
				|| "th".equalsIgnoreCase(tagName)) {
			incrementContentScore(node, -5);
		}

		incrementContentScore(node, getClassWeight(node));
	}

	protected Element grabArticle(Element body) {
		/**
		 * Loop through all paragraphs, and assign a score to them based on how
		 * content-y they look. Then add their score to their parent node.
		 * (遍历所有段落，基于内容给它们打分。然后把它们的分数加到它们的父节点上,也
		 * 给一部分分数给祖父节点)
		 * 
		 * A score is determined by things like number of commas, class names,
		 * etc. Maybe eventually link density. （分数有逗号，类名，链接密度等因素决定。）
		 **/
		Elements allParagraphs = body.getElementsByTag("p");
		ArrayList<Element> candidates = new ArrayList<Element>();

		for (Element node : allParagraphs) {
			Element parentNode = node.parent();
			Element grandParentNode = parentNode.parent();
			String innerText = getInnerText(node, true);

			/*
			 * If this paragraph is less than 15 characters, don't even count
			 * it. （如果段落内的文本少于15个字符，就不考虑它）
			 * 
			 * todo: 15这个数字或许可以改得更加合理。
			 */
			if (innerText.length() < 15) {
				continue;
			}

			/* Initialize readability data for the parent. */
			// 给父节点初始化readability数据
			if (!parentNode.hasAttr("readabilityContentScore")) {
				initializeNode(parentNode);
				candidates.add(parentNode);
			}

			/* Initialize readability data for the grandparent. */
			// 给祖父节点初始化readability数据
			if (!grandParentNode.hasAttr("readabilityContentScore")) {
				initializeNode(grandParentNode);
				candidates.add(grandParentNode);
			}

			int contentScore = 0;

			/* Add a point for the paragraph itself as a base. */
			// 这个段落因为本身而获得一分
			contentScore++;

			/* Add points for any commas within this paragraph */
			// 段落内有多少逗号（英文逗号或者中文逗号），该节点就加多少分
			contentScore += innerText.split(",|，").length;

			/*
			 * For every 100 characters in this paragraph, add another point. Up
			 * to 3 points. （段落中每一百个字符给该节点增加1分，最多增加3分）
			 */
			contentScore += Math.min(Math.floor(innerText.length() / 100), 3);

			/* Add the score to the parent. The grandparent gets half. */
			// 把分数加给该节点的父节点，祖父节点获得一半
			incrementContentScore(parentNode, contentScore);
			incrementContentScore(grandParentNode, contentScore / 2);
		}

		/**
		 * After we've calculated scores, loop through all of the possible
		 * candidate nodes we found and find the one with the highest score.
		 * （在我们计算了分数之后，就可以遍历我们找到的所有可能的候选节点，找到得分最高的那一个）
		 */
		// 获得分数最高的元素
		Element topCandidate = null;
		for (Element candidate : candidates) {
			/**
			 * Scale the final candidates score based on link density. Good
			 * content should have a relatively small link density (5% or less)
			 * and be mostly unaffected by this operation.
			 * （基于链接密度增加候选节点分数。好的节点应该拥有相对小的链接密度（5%或者更少） 而且大部分不会被这个操作影响）
			 */
			scaleContentScore(candidate, 1 - getLinkDensity(candidate));

			dbg("Candidate: (" + candidate.className() + ":" + candidate.id()
					+ ") with score " + getContentScore(candidate));

			if (topCandidate == null
					|| getContentScore(candidate) > getContentScore(topCandidate)) {
				topCandidate = candidate;
			}
		}

		return topCandidate;
	}

	/**
	 * 从新闻正文中获得第一个文本长度大于3的节点
	 * 
	 * @param content
	 *            新闻正文
	 * @return
	 */
	public String getFirstTextNodeFromContent(Element content) {
		String firstTextNode = null;
		List<TextNode> textNodeList = DOMUtil.getAllTextNodes(content);
		for (TextNode textNode : textNodeList) {
			String text = textNode.text().trim();
			int len = text.length();
			if (len > 3) {
				firstTextNode = text;
				break;
			}
		}
		return firstTextNode;
	}

	/**
	 * 从新闻正文中获得最后一个文本长度大于3的节点
	 * 
	 * @param content 新闻正文
	 * @return
	 */
	public String getLastTextNodeFromContent(Element content) {
		String lastTextNode = null;
		List<TextNode> textNodeList = DOMUtil.getAllTextNodes(content);
		int size = textNodeList.size();
		for (int i = size - 1; i >= 0; i--) {
			TextNode textNode = textNodeList.get(i);
			String text = textNode.text().trim();
			int len = text.length();
			if (len > 3) {
				lastTextNode = text;
				break;
			}
		}
		return lastTextNode;
	}
	
	/**
	 * Get the inner text of a node - cross browser compatibly. This also strips
	 * out any excess whitespace to be found. （获得一个节点内部的文本。
	 * 并且根据参数，判断是否移除额外的空白）
	 * 
	 * @param e
	 * @param normalizeSpaces
	 * @return
	 */
	private static String getInnerText(Element e, boolean normalizeSpaces) {
		String textContent = e.text().trim();

		if (normalizeSpaces) {
			textContent = textContent.replaceAll(Patterns.REGEX_NORMALIZE, " ");
		}

		return textContent;
	}

	/**
	 * Get the number of times a string s appears in the node e.
	 * 
	 * @param e
	 * @param s
	 * @return
	 */
	private static int getCharCount(Element e, String s) {
		if (s == null || s.length() == 0) {
			s = ",";
		}
		return getInnerText(e, true).split(s).length;
	}

	/**
	 * Remove the style attribute on every e and under.
	 * (层次遍历树)
	 * @param e
	 */
	private static void cleanStyles(Element e) {
		if (e == null) {
			return;
		}

		// Remove any root styles, if we're able.
		if (!"readability-styled".equals(e.className())) {
			e.removeAttr("style");
		}
		
		Element cur = e.children().first();

		// Go until there are no more child nodes
		while (cur != null) {
			cleanStyles(cur);
			cur = cur.nextElementSibling();
		}
	}

	/**
	 * Get the density of links as a percentage of the content. This is the
	 * amount of text that is inside a link divided by the total text in the
	 * node. （获得链接密度=链接中的文本数量处理整个节点的文本数量）
	 * 
	 * @param e
	 * @return
	 */
	private static float getLinkDensity(Element e) {
		Elements links = getElementsByTag(e, "a");
		int textLength = getInnerText(e, true).length();
		float linkLength = 0.0F;
		for (Element link : links) {
			linkLength += getInnerText(link, true).length();
		}
		return linkLength / textLength;
	}

	/**
	 * Get an elements class/id weight. Uses regular expressions to tell if this
	 * element looks good or bad. （获得一个元素 类／id的权重。使用正则表达式来检查这个元素看起来 是好的还是坏的）
	 * 
	 * @param e
	 * @return
	 */
	private static int getClassWeight(Element e) {
		int weight = 0;

		/* Look for a special classname */
		String className = e.className();
		if (!isEmpty(className)) {
			Matcher negativeMatcher = Patterns.get(Patterns.RegEx.NEGATIVE)
					.matcher(className);
			Matcher positiveMatcher = Patterns.get(Patterns.RegEx.POSITIVE)
					.matcher(className);
			if (negativeMatcher.find()) {
				weight -= 25;
			}
			if (positiveMatcher.find()) {
				weight += 25;
			}
		}

		/* Look for a special ID */
		String id = e.id();
		if (!isEmpty(id)) {
			Matcher negativeMatcher = Patterns.get(Patterns.RegEx.NEGATIVE)
					.matcher(id);
			Matcher positiveMatcher = Patterns.get(Patterns.RegEx.POSITIVE)
					.matcher(id);
			if (negativeMatcher.find()) {
				weight -= 25;
			}
			if (positiveMatcher.find()) {
				weight += 25;
			}
		}

		return weight;
	}

	/**
	 * Remove extraneous break tags from a node. （移除节点中所有的break标签）
	 * 
	 * @param e
	 */
	private static void killBreaks(Element e) {
		e.html(e.html().replaceAll(Patterns.REGEX_KILL_BREAKS, "<br/>"));
	}

	/**
	 * Clean a node of all elements of type "tag". (Unless it's a youtube/vimeo
	 * video. People love movies.)
	 * 
	 * @param e
	 * @param tag
	 */
	private static void clean(Element e, String tag) {
		Elements targetList = getElementsByTag(e, tag);
		boolean isEmbed = "object".equalsIgnoreCase(tag)
				|| "embed".equalsIgnoreCase(tag)
				|| "iframe".equalsIgnoreCase(tag);

		for (Element target : targetList) {
			Matcher matcher = Patterns.get(Patterns.RegEx.VIDEO).matcher(
					target.outerHtml());
			if (isEmbed && matcher.find()) {
				continue;
			}
			target.remove();
		}
	}

	/**
	 * Clean an element of all tags of type "tag" if they look fishy. "Fishy" is
	 * an algorithm based on content length, classnames, link density, number of
	 * images & embeds, etc.
	 * 
	 * @param e
	 * @param tag
	 */
	private void cleanConditionally(Element e, String tag) {
		Elements tagsList = getElementsByTag(e, tag);

		/**
		 * Gather counts for other typical elements embedded within. Traverse
		 * backwards so we can remove nodes at the same time without effecting
		 * the traversal.
		 * 
		 * TODO: Consider taking into account original contentScore here.
		 */
		for (Element node : tagsList) {
			int weight = getClassWeight(node);

			dbg("Cleaning Conditionally (" + node.className() + ":" + node.id()
					+ ")" + getContentScore(node));

			if (weight < 0) {
				node.remove();
			} else if (getCharCount(node, ",") < 10) {
				/**
				 * If there are not very many commas, and the number of
				 * non-paragraph elements is more than paragraphs or other
				 * ominous signs, remove the element.
				 */
				int p = getElementsByTag(node, "p").size();
				int img = getElementsByTag(node, "img").size();
				int li = getElementsByTag(node, "li").size() - 100;
				int input = getElementsByTag(node, "input").size();

				int embedCount = 0;
				Elements embeds = getElementsByTag(node, "embed");
				for (Element embed : embeds) {
					if (!Patterns.get(Patterns.RegEx.VIDEO)
							.matcher(embed.absUrl("src")).find()) {
						embedCount++;
					}
				}

				float linkDensity = getLinkDensity(node);
				int contentLength = getInnerText(node, true).length();
				boolean toRemove = false;

				if (img > p) {
					toRemove = true;
				} else if (li > p && !"ul".equalsIgnoreCase(tag)
						&& !"ol".equalsIgnoreCase(tag)) {
					toRemove = true;
				} else if (input > Math.floor(p / 3)) {
					toRemove = true;
				} else if (contentLength < 25 && (img == 0 || img > 2)) {
					toRemove = true;
				} else if (weight < 25 && linkDensity > 0.2f) {
					toRemove = true;
				} else if (weight > 25 && linkDensity > 0.5f) {
					toRemove = true;
				} else if ((embedCount == 1 && contentLength < 75)
						|| embedCount > 1) {
					toRemove = true;
				}

				if (toRemove) {
					node.remove();
				}
			}
		}
	}

	/**
	 * Clean out spurious headers from an Element. Checks things like classnames
	 * and link density.
	 * 
	 * @param e
	 */
	private static void cleanHeaders(Element e) {
		for (int headerIndex = 1; headerIndex < 7; headerIndex++) {
			Elements headers = getElementsByTag(e, "h" + headerIndex);
			for (Element header : headers) {
				if (getClassWeight(header) < 0
						|| getLinkDensity(header) > 0.33f) {
					header.remove();
				}
			}
		}
	}

	/**
	 * Print debug logs
	 * 
	 * @param msg
	 */
	protected void dbg(String msg) {
		dbg(msg, null);
	}

	/**
	 * Print debug logs with stack trace
	 * 
	 * @param msg
	 * @param t
	 */
	protected void dbg(String msg, Throwable t) {
		System.out.println(msg + (t != null ? ("\n" + t.getMessage()) : "")
				+ (t != null ? ("\n" + t.getStackTrace()) : ""));
	}

	private static class Patterns {
		private static Pattern sUnlikelyCandidatesRe;
		private static Pattern sOkMaybeItsACandidateRe;
		private static Pattern sPositiveRe;
		private static Pattern sNegativeRe;
		private static Pattern sDivToPElementsRe;
		private static Pattern sVideoRe;
		// (?i)正则表达式的匹配模式：即不区分大小写
		private static final String REGEX_REPLACE_BRS = "(?i)(<br[^>]*>[ \n\r\t]*){2,}";
		private static final String REGEX_REPLACE_FONTS = "(?i)<(\\/?)font[^>]*>";
		/* Java has String.trim() */
		// private static final String REGEX_TRIM = "^\\s+|\\s+$";
		private static final String REGEX_NORMALIZE = "\\s{2,}";
		private static final String REGEX_KILL_BREAKS = "(<br\\s*\\/?>(\\s|&nbsp;?)*){1,}";

		public enum RegEx {
			UNLIKELY_CANDIDATES, OK_MAYBE_ITS_A_CANDIDATE, POSITIVE, NEGATIVE, DIV_TO_P_ELEMENTS, VIDEO;
		}

		public static Pattern get(RegEx re) {
			switch (re) {
			case UNLIKELY_CANDIDATES: {
				if (sUnlikelyCandidatesRe == null) {
					sUnlikelyCandidatesRe = Pattern
							.compile(
									"combx|comment|disqus|foot|header|menu|meta|nav|rss|shoutbox|sidebar|sponsor",
									Pattern.CASE_INSENSITIVE);
				}
				return sUnlikelyCandidatesRe;
			}
			case OK_MAYBE_ITS_A_CANDIDATE: {
				if (sOkMaybeItsACandidateRe == null) {
					sOkMaybeItsACandidateRe = Pattern.compile(
							"and|article|body|column|main",
							Pattern.CASE_INSENSITIVE);
				}
				return sOkMaybeItsACandidateRe;
			}
			case POSITIVE: {
				if (sPositiveRe == null) {
					sPositiveRe = Pattern
							.compile(
									"article|body|content|entry|hentry|page|pagination|post|text",
									Pattern.CASE_INSENSITIVE);
				}
				return sPositiveRe;
			}
			case NEGATIVE: {
				if (sNegativeRe == null) {
					sNegativeRe = Pattern
							.compile(
									"combx|comment|contact|foot|footer|footnote|link|media|meta|promo|related|scroll|shoutbox|sponsor|tags|widget",
									Pattern.CASE_INSENSITIVE);
				}
				return sNegativeRe;
			}
			case DIV_TO_P_ELEMENTS: {
				if (sDivToPElementsRe == null) {
					sDivToPElementsRe = Pattern.compile(
							"<(a|blockquote|dl|div|img|ol|p|pre|table|ul)",
							Pattern.CASE_INSENSITIVE);
				}
				return sDivToPElementsRe;
			}
			case VIDEO: {
				if (sVideoRe == null) {
					sVideoRe = Pattern.compile(
							"http:\\/\\/(www\\.)?(youtube|vimeo)\\.com",
							Pattern.CASE_INSENSITIVE);
				}
				return sVideoRe;
			}
			}
			return null;
		}
	}

	/**
	 * Reads the content score. （读取一个节点的内容分）
	 * 
	 * @param node
	 * @return
	 */
	private static int getContentScore(Element node) {
		try {
			return Integer.parseInt(node.attr(CONTENT_SCORE));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Increase or decrease the content score for an Element by an
	 * increment/decrement. (增加或减少一个元素的内容分)
	 * 
	 * @param node
	 * @param increment
	 * @return
	 */
	private static Element incrementContentScore(Element node, int increment) {
		int contentScore = getContentScore(node);
		contentScore += increment;
		node.attr(CONTENT_SCORE, Integer.toString(contentScore));
		return node;
	}

	/**
	 * Scales the content score for an Element with a factor of scale.
	 * （用一个伸缩因子伸缩内容分数）
	 * 
	 * @param node
	 * @param scale
	 * @return
	 */
	private static Element scaleContentScore(Element node, float scale) {
		int contentScore = getContentScore(node);
		contentScore *= scale;
		node.attr(CONTENT_SCORE, Integer.toString(contentScore));
		return node;
	}

	/**
	 * Jsoup's Element.getElementsByTag(Element e) includes e itself, which is
	 * different from W3C standards. This utility function is exclusive of the
	 * Element e. （获得元素e下所有标签为tag的节点（不包括e节点））
	 * 
	 * @param e
	 * @param tag
	 * @return
	 */
	private static Elements getElementsByTag(Element e, String tag) {
		Elements es = e.getElementsByTag(tag);
		es.remove(e);
		return es;
	}
	
	/**
	 * 获得元素e下所有标签为tag的节点（不包括e节点）
	 * @param e
	 * @param tag
	 */
	private static void removeElementsByTag(Element e, String tag) {
		Elements es = getElementsByTag(e, tag);
		for (Element element : es) {
			element.remove();
		}
	}

	/**
	 * Helper utility to determine whether a given String is empty.
	 * 
	 * @param s
	 * @return
	 */
	private static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

}
