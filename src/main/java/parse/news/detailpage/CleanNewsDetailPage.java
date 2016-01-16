package parse.news.detailpage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jreadability.news.DOMUtil;
import jreadability.news.NodeOperate;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import parsewebpage.ParseWebPage;

/**
 * 清洗新闻详情页内容，以易于阅读的方式展示出来；
 * 清洗行为以新闻标题和新闻正文为核心，因为就
 * 目前掌握的技术来说，它们是最容易获得的；
 * 这个“清洗新闻详情页内容”实现可行的前提是，文档中
 * 一定存在新闻标题和新闻正文。
 * @author liyuncong
 *
 */
public class CleanNewsDetailPage extends ParseWebPage {
	private static final String CONTENT_SCORE = "readabilityContentScore";
	
	@Override
	protected Object innerParse(Document webpage) {
		// 1.获得包含想要内容的节点
		
		if (webpage == null) {
			return null;
		}
		
		Element body = webpage.body();
		if (body == null) {
			return null;
		}
		
		Node newsTitle = getNewsTitle(webpage);
		if (newsTitle == null) {
			return null;
		}
		
		Element newsText = getNewsText(body);
		if (newsText == null) {
			return null;
		}
		
	
		Node closestCommonAncestor = getClosestCommonAncestor(
				newsTitle, newsText);
		if (closestCommonAncestor == null) {
			return null;
		}
		
		// 2.删除获得节点中不要想的内容
		// 构造从newsTitle节点到closestCommonAncestor节点的路径
		List<Node> ancestorsOfNewsTitle = new LinkedList<Node>();
     	Node ancestorOfTitle = newsTitle;
     	while (ancestorOfTitle != closestCommonAncestor) {
     		ancestorsOfNewsTitle.add(ancestorOfTitle);
     		ancestorOfTitle = ancestorOfTitle.parent();
		}
     	
     	// 删除树中ancestorsOfNewsTitle这条路左面的所有节点
     	DOMUtil.deleteLeftOrRight(ancestorsOfNewsTitle, "left");
     	
     	if (newsText != closestCommonAncestor) {
     		// 构造从newsText节点到closestCommonAncestor节点的路径
        	List<Node> ancestorsOfNewsText = new LinkedList<Node>();
         	Node ancestorOfText = newsText;
         	while (ancestorOfText != closestCommonAncestor) {
         		ancestorsOfNewsText.add(ancestorOfText);
         		ancestorOfText = ancestorOfText.parent();
    		}
         	
     		// 删除树中ancestorsOfNewsText这条路右面的所有节点
     		DOMUtil.deleteLeftOrRight(ancestorsOfNewsText, 
     				"right");
     		
     		// 删除在路径ancestorsOfNewsTitle和
          	// 路径ancestorsOfNewsText之间的区域
            // 中会影响页面显示且不想要的节点;
     		// 删除垃圾a标签；
			// 什么样的标签是垃圾a标签呢？
			// 1. 锚文本长度小于3的
			// 2. 包含非文本节点的子节点的
     		// 3. 上两条是为了这个目的，删除锚文本不是来源的所有a标签
			// 基于a标签，我是基于这样的认识得到上面的结论：
			// 1. a标签里面不会再嵌套a标签
     		DOMUtil.travers(ancestorsOfNewsTitle, ancestorsOfNewsText, new NodeOperate() {
				
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
     	DOMUtil.deleteAllElementByTagName(ancestorsOfNewsTitle, 
     			ancestorsOfNewsText, "iframe");
     	// 删除所有input元素
     	DOMUtil.deleteAllElementByTagName(ancestorsOfNewsTitle, 
     			ancestorsOfNewsText, "input");
		}
     	
     	Document document = wrapNodeToDocument(
     			closestCommonAncestor);
     	
		return document;
	}
	
	/**
	 * 把一个节点包装成一个html文档
	 * @param node
	 * @return
	 */
	private Document wrapNodeToDocument(Node node) {
		Document document = null;
      	try {
      		document = Jsoup.parse(new File("newsWebPageHead.html"), "utf-8");
      	} catch (IOException e) {
      		e.printStackTrace();
      	}
       	if (node.nodeName().equals("body")) {
      		document.body().html(node.outerHtml());
      	} else {
      		document.body().appendChild(node);
      	}
     	return document;
	}
	
	/**
	 * 获得node1和node2的最近公共父节点。
	 * @param node1 DOM树中的标签节点
	 * @param node2 DOM树中的标签节点
	 * @return node1和node2的最近公共父节点, 没有找到返回null
	 */
	private Node getClosestCommonAncestor(Node node1, 
			Node node2) {
		if (node1 == null || node2 == null) {
			return null;
		}
		
		// 实现思路：通过遍历分别获取到两条链表，之后再比较，最后求出最近公共父节点
		LinkedList<Node> ancestorsOfNode1 = ancestors(node1);
     	
     	LinkedList<Node> ancestorsOfNode2 = ancestors(node2);
     	
     	Node closestCommonAncestor = getClosestCommonAncestor(
     			ancestorsOfNode1, ancestorsOfNode2);
     	
     	return closestCommonAncestor;
     	
	}
	
	/**
	 * 获得node1和node2的最近公共父节点。
	 * @param ancestorsOfNode1 node1的所有祖先，包括它自己，
	 * <br/>并且越远的祖先在链表中的索引越小
	 * @param ancestorsOfNode2 node2的所有祖先，包括它自己，
	 * <br/>并且越远的祖先在链表中的索引越小
	 * @return node1和node2的最近公共父节点, 没有找到返回null
	 */
	private Node getClosestCommonAncestor(LinkedList<Node> 
		ancestorsOfNode1, LinkedList<Node> ancestorsOfNode2) {
		int size1 = ancestorsOfNode1.size();
		int size2 = ancestorsOfNode2.size();
		int size = Math.min(size1, size2);
		Node closestCommonAncestor = null;
		for(int i = size - 1; i >= 0; i--) {
			if (ancestorsOfNode1.get(i) == ancestorsOfNode2.get(i)) {
				closestCommonAncestor = ancestorsOfNode1.get(i);
				break;
			}
		}
		return closestCommonAncestor;
	}
	
	/**
	 * 获得node的所有祖先，包括它自己，并且越远的祖先在链表中的索引越小。
	 * @param node
	 * @return node的所有祖先
	 */
	private LinkedList<Node> ancestors(Node node) {
		LinkedList<Node> ancestors = new LinkedList<>();
		Node cursor = node;
     	while (cursor != null) {
     		ancestors.addFirst(cursor);
     		cursor = cursor.parent();
		}
     	return ancestors;
	}
	
	/**
	 * 
	 * @param webpage 新闻详情页的DOM对象
	 * @return 新闻标题节点，如果没有找到就返回null
	 */
	private Node getNewsTitle(Document webpage) {
		String webPageTitle = getWebPageTitle(webpage);
		Node newsTitle = getNewsTitle(webPageTitle, 
				webpage.body());
		return newsTitle;
	}
	
	/**
	 * 
	 * @param webpage 新闻详情页的DOM对象
	 * @return 网页标题，如果没有找到，就返回空串
	 */
	private String getWebPageTitle(Document webpage) {
		return webpage.title();
	}
	
	/**
	 * 获得新闻标题节点。
	 * @param title　网页标题
	 * @param body　网页body元素
	 * @return 新闻标题节点，如果没有找到就返回null
	 */
	private Node getNewsTitle(String title, Element body) {
		if (StringUtils.isBlank(title) || body == null) {
			return null;
		}
		
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
	 * 获得所有新闻正文的最近公共父节点
	 * @param body 新闻详情页的body节点
	 * @return 所有新闻正文的最近公共父节点, 没有找到返回null
	 */
	protected Element getNewsText(Element body) {
		/**
		 * Loop through all paragraphs, and assign a score to them based on how
		 * content-y they look. Then add their score to their parent node.
		 * (遍历所有段落，基于内容给它们打分。然后把它们的分数加到它们的父节点上,也
		 * 给一部分分数给祖父节点)
		 * 
		 * A score is determined by things like number of commas, class names,
		 * etc. Maybe eventually link density. （分数由逗号，类名，链接密度等因素决定。）
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

			if (topCandidate == null
					|| getContentScore(candidate) > getContentScore(topCandidate)) {
				topCandidate = candidate;
			}
		}

		return topCandidate;
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
	 * Get the density of links as a percentage of the content. This is the
	 * amount of text that is inside a link divided by the total text in the
	 * node. （获得链接密度=链接中的文本数量处理整个节点的文本数量）
	 * 
	 * @param e
	 * @return
	 */
	private float getLinkDensity(Element e) {
		Elements links = getElementsByTag(e, "a");
		int textLength = getInnerText(e, true).length();
		float linkLength = 0.0F;
		for (Element link : links) {
			linkLength += getInnerText(link, true).length();
		}
		return linkLength / textLength;
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
		if (StringUtils.isNotBlank(className)) {
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
		if (!StringUtils.isBlank(id)) {
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
}
