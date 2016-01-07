package jreadability.news;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class ReadabilityNews {

	private static final String CONTENT_SCORE = "readabilityContentScore";

	// A HTML Document
	private final Document mDocument;
	private String mBodyCache;

	/**
	 * 
	 * @param html
	 *            待解析的HTML
	 */
	public ReadabilityNews(String html) {
		super();
		mDocument = Jsoup.parse(html);
	}

	/**
	 * 
	 * @param html
	 *            待解析的HTML
	 * @param baseUri
	 *            The URL where the HTML was retrieved from. Used to resolve
	 *            relative URLs to absolute URLs, that occur before the HTML
	 *            declares a <base href> tag.
	 */
	public ReadabilityNews(String html, String baseUri) {
		super();
		mDocument = Jsoup.parse(html, baseUri);
	}

	/**
	 * 
	 * @param in
	 *            file to load HTML from
	 * @param charsetName
	 *            待解析的HTML
	 * @param baseUri
	 *            The URL where the HTML was retrieved from. Used to resolve
	 *            relative URLs to absolute URLs, that occur before the HTML
	 *            declares a <base href> tag.
	 * @throws IOException
	 */
	public ReadabilityNews(File in, String charsetName, String baseUri)
			throws IOException {
		super();
		mDocument = Jsoup.parse(in, charsetName, baseUri);
	}

	/**
	 * 
	 * @param url
	 *            URL to fetch (with a GET). The protocol must be http or https.
	 * @param timeoutMillis
	 *            Connection and read timeout, in milliseconds. If exceeded,
	 *            IOException is thrown.
	 * @throws IOException
	 */
	public ReadabilityNews(URL url, int timeoutMillis) throws IOException {
		super();
		mDocument = Jsoup.parse(url, timeoutMillis);
	}

	public ReadabilityNews(Document doc) {
		super();
		mDocument = doc;
	}

	// @formatter:off
	/**
	 * Runs readability.
	 * 
	 * Workflow: 1. Prep the document by removing script tags, css, etc.
	 * (通过移除script tags, css等准备document) 2. Build readability's DOM tree.
	 * （构建readability树） 3. Grab the article content from the current dom tree.
	 * （从dom树中抓取文章内容） 4. Replace the current DOM tree with the new one.
	 * （替换用新的DOM树代替旧的） 5. Read peacefully.
	 * 
	 * @param preserveUnlikelyCandidates
	 */
	// @formatter:on
	private void init(boolean preserveUnlikelyCandidates) {
		Element body = mDocument.body();
		if (body != null && mBodyCache == null) {
			// mBodyCache保存这body里面的内容
			mBodyCache = body.html();
		}

		prepDocument();

		/* Build readability's DOM tree */
		// 创建readability的DOM树
		Element overlay = mDocument.createElement("div");
		Element innerDiv = mDocument.createElement("div");
		// 获取网页title
		Element articleTitle = getArticleTitle();

		// 删除body中新闻标题之前的文本节点，并获得标题
		String newsTitle = removeTextNodeInBodyForeTitle(articleTitle.text());
		Element newsTitleElement = createElement("h1", newsTitle);

		// 备份mDocument
		Document mDocumentBackup = mDocument.clone();

		// 获取正文
		Element articleContent = grabArticle(preserveUnlikelyCandidates);

		// 获得正文的第一个文本节点
		String firstTextNode = getFirstTextNodeFromContent(articleContent);

		// 获取新闻时间
		String publishTime = getNewsPublishTime(newsTitle, firstTextNode,
				mDocumentBackup);
		if (publishTime != null &&publishTime.length() == 0) {
			publishTime = null;
		}

		// 获取新闻来源
		String source = getNewsSource1(newsTitle, firstTextNode,
				mDocumentBackup);
		if (source != null && source.length() == 0) {
			source = null;
		}

		// 创建节点保存新闻发表时间和来源
		Element publishTimeSource = null;
		if (publishTime != null && source == null) {
			publishTimeSource = createElement("p", publishTime);
		} else if (publishTime == null && source != null) {
			publishTimeSource = createElement("p", source);
		} else if (publishTime != null || source != null) {
			publishTimeSource = createElement("p", publishTime + " 来源:"
					+ source);
		}

		/**
		 * If we attempted to strip unlikely candidates on the first run
		 * through, and we ended up with no content, that may mean we stripped
		 * out the actual content so we couldn't parse it. So re-run init while
		 * preserving unlikely candidates to have a better shot at getting our
		 * content out properly. （如果我们在第一次运行时尝试去去除看上去不太可能是候选节点的节点，而且我们最终没有
		 * 得到内容，这也许意味着我们移除了实际的内容，因此我们不能解析它。因此，再次运行，这次
		 * 我们保留那些看上去不太可能是候选节点的节点来再次尝试获得我们想要的内容）
		 */
		if (isEmpty(getInnerText(articleContent, false))) {
			if (!preserveUnlikelyCandidates) {
				mDocument.body().html(mBodyCache);
				init(true);
				return;
			} else {
				articleContent
						.html("<p>Sorry, readability was unable to parse this page for content.</p>");
			}
		}

		/* Glue the structure of our document together. */
		// 把我们的文档结构粘在一起
		innerDiv.appendChild(newsTitleElement);

		if (publishTimeSource != null) {
			innerDiv.appendChild(publishTimeSource);
		}

		innerDiv.appendChild(articleContent);
		overlay.appendChild(innerDiv);

		/* Clear the old HTML, insert the new content. */
		// 清除老的HTML,插入新的内容
		mDocument.body().html("");
		mDocument.body().prependChild(overlay);
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
	 * @return
	 */
	public final String outerHtml() {
		return mDocument.outerHtml();
	}

	/**
	 * Get the article title as an H1. Currently just uses document.title, we
	 * might want to be smarter in the future.
	 * （获得文章的标题作为H1元素。现在只是使用document.title，将来也许会使用更好的 方式）
	 * 
	 * @return
	 */
	protected Element getArticleTitle() {
		Element articleTitle = mDocument.createElement("h1");
		String htmlTitle = mDocument.title();
		articleTitle.html(htmlTitle);
		return articleTitle;
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
	 * 删除body节点中，在新闻标题之前的所有文本节点
	 * 
	 * @param title 网页title，新闻标题包含其中
	 * @return 返回新闻标题
	 */
	public String removeTextNodeInBodyForeTitle(String title) {
		String newsTitle = "";
		List<TextNode> textNodeList = DOMUtil.getAllTextNodes(mDocument.body());

		for (TextNode textNode : textNodeList) {
			String text = textNode.text().trim();
			int len = text.length();
			if (len != 0) {
				System.out.println(textNode.text().trim());
			}
		}

		for (TextNode textNode : textNodeList) {
			String text = textNode.text().trim();
			int len = text.length();
			if (len != 0) {
				// 这个地方还需要改进，得到text可能也只是新闻标题的一部分；
				// 第二个条件用于解决html文档不标准的问题，比如不能取出body节点
				if (title.startsWith(text)
						&& (text.length() < title.length() 
						&& text.length() >= title.length() / 2)) {
					newsTitle = text;
					break;
				} else {
					 textNode.remove();
				}
			}
		}
		return newsTitle;
	}

	/**
	 * 在一系列文本节点范围内寻找发表时间
	 * 
	 * @param start
	 *            起点文本
	 * @param end
	 *            终点文本
	 * @param element
	 *            包好起点文本、终点文本的节点树
	 * @return 新闻发表时间；如果没找到就返回null
	 */
	public String getNewsPublishTime(String start, String end, Document element) {
		List<TextNode> textNodeList = DOMUtil.getAllTextNodes(element.body());

		String publishTime = null;

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

				// 如果已经开始，就寻找时间
				boolean isPublishTime = DateUtil.validateDateTime(text);
				if (isPublishTime) {
					publishTime = text;
					break;
				}
				if (text.equals(end)) {
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

		for (TextNode textNode : textNodeList) {
			String text = textNode.text().trim();
			int len = text.length();
			if (len != 0) {
				System.out.println(textNode.text().trim());
			}
		}

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
	 * Prepare the HTML document for readability to scrape it. This includes
	 * things like stripping javascript, CSS, and handling terrible markup.
	 * (为readability准备HTML document。包括去掉javascript, CSS和处理糟糕的标记)
	 */
	protected void prepDocument() {
		/**
		 * In some cases a body element can't be found (if the HTML is totally
		 * hosed for example) so we create a new body node and append it to the
		 * document. （在某些情况下，body元素不能被找到，因此我们创建一个新的body节点，并把这个节点 附到document上）
		 */
		if (mDocument.body() == null) {
			mDocument.appendElement("body");
		}

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

	/**
	 * Using a variety of metrics (content score, classname, element types),
	 * find the content that is most likely to be the stuff a user wants to
	 * read. Then return it wrapped up in a div.
	 * （用不同的测量标准（内容分数，类名，元素类型）来寻找用户最想读的内容。然后包装 在一个div中返回）
	 * 
	 * @param preserveUnlikelyCandidates
	 *            是否保存看上去不像候选节点的元素
	 * @return
	 */
	protected Element grabArticle(boolean preserveUnlikelyCandidates) {
		/**
		 * First, node prepping. Trash nodes that look cruddy (like ones with
		 * the class name "comment", etc), and turn divs into P tags where they
		 * have been used inappropriately (as in, where they contain no other
		 * block level elements.) （首先，节点准备。废弃一些节点（例如 类名为“comment”等），把使用不当的div标签
		 * 换成P标签（比如，它们没有包含其它块级元素）） Note: Assignment from index for performance.
		 * See http://www.peachpit.com/articles/article.aspx?p=31567&seqNum=5
		 * TODO: Shouldn't this be a reverse traversal?
		 **/
		for (Element node : mDocument.getAllElements()) {
			/* Remove unlikely candidates */
			// 移除看上去不像候选节点的元素
			if (!preserveUnlikelyCandidates) {
				String unlikelyMatchString = node.className() + node.id();
				Matcher unlikelyCandidatesMatcher = Patterns.get(
						Patterns.RegEx.UNLIKELY_CANDIDATES).matcher(
						unlikelyMatchString);
				Matcher maybeCandidateMatcher = Patterns.get(
						Patterns.RegEx.OK_MAYBE_ITS_A_CANDIDATE).matcher(
						unlikelyMatchString);
				if (unlikelyCandidatesMatcher.find()
						&& maybeCandidateMatcher.find()
						&& !"body".equalsIgnoreCase(node.tagName())) {
					node.remove();
					dbg("Removing unlikely candidate - " + unlikelyMatchString);
					continue;
				}
			}

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
						// <div id="blog-news"></div> 变为 <div
						// id="blog-news"></div>
						node.tagName("p");
					} catch (Exception e) {
						dbg("Could not alter div to p, probably an IE restriction, reverting back to div.",
								e);
					}
				}
			}
		}

		/**
		 * Loop through all paragraphs, and assign a score to them based on how
		 * content-y they look. Then add their score to their parent node.
		 * (遍历所有段落，基于内容给它们打分。然后把它们的分数加到它们的父节点上)
		 * 
		 * A score is determined by things like number of commas, class names,
		 * etc. Maybe eventually link density. （分数有逗号，类名，链接密度等因素决定。）
		 **/
		Elements allParagraphs = mDocument.getElementsByTag("p");
		ArrayList<Element> candidates = new ArrayList<Element>();

		for (Element node : allParagraphs) {
			Element parentNode = node.parent();
			Element grandParentNode = parentNode.parent();
			String innerText = getInnerText(node, true);

			/*
			 * If this paragraph is less than 25 characters, don't even count
			 * it. （如果段落内的文本少于25个字符，就不考虑它）
			 */
			if (innerText.length() < 25) {
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
			// 段落内有多少逗号，该节点就加多少分
			contentScore += innerText.split(",").length;

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
		 * （在我们计算了分数之后，就可以遍历我们找到的所有可能的候选节点，找到得分最高的 那一个）
		 */
		// 分数最高的元素
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

		/**
		 * If we still have no top candidate, just use the body as a last
		 * resort. We also have to copy the body node so it is something we can
		 * modify. （如果我们还没有一个顶级候选元素，我们可以使用body。我们也必须复制body节点， 因此我们可以修改它。）
		 */
		if (topCandidate == null
				|| "body".equalsIgnoreCase(topCandidate.tagName())) {
			topCandidate = mDocument.createElement("div");
			topCandidate.html(mDocument.body().html());
			mDocument.body().html("");
			mDocument.body().appendChild(topCandidate);
			initializeNode(topCandidate);
		}

		/**
		 * Now that we have the top candidate, look through its siblings for
		 * content that might also be related. Things like preambles, content
		 * split by ads that we removed, etc.
		 * （既然我们已经有了顶级候选元素，我们就遍历它的所有兄弟节点的内容，这些内容
		 * 可能也是相关的。像前言这样的东西，内容被广告分开了，我们已经把它删除了）
		 */
		Element articleContent = mDocument.createElement("div");
		articleContent.attr("id", "readability-content");
		// 设置一个分数门槛，看哪一些兄弟也是正文
		int siblingScoreThreshold = Math.max(10,
				(int) (getContentScore(topCandidate) * 0.2f));
		Elements siblingNodes = topCandidate.parent().children();
		for (Element siblingNode : siblingNodes) {
			// 是否被添加到articleContent节点中
			boolean append = false;

			dbg("Looking at sibling node: (" + siblingNode.className() + ":"
					+ siblingNode.id() + ")" + " with score "
					+ getContentScore(siblingNode));

			if (siblingNode == topCandidate) {
				append = true;
			}

			if (getContentScore(siblingNode) >= siblingScoreThreshold) {
				append = true;
			}

			// 什么样的p节点才能是正文
			if ("p".equalsIgnoreCase(siblingNode.tagName())) {
				float linkDensity = getLinkDensity(siblingNode);
				String nodeContent = getInnerText(siblingNode, true);
				int nodeLength = nodeContent.length();

				if (nodeLength > 80 && linkDensity < 0.25f) {
					append = true;
				} else if (nodeLength < 80 && linkDensity == 0.0f
						&& nodeContent.matches(".*\\.( |$).*")) {
					append = true;
				}
			}

			if (append) {
				dbg("Appending node: " + siblingNode);

				/*
				 * Append sibling and subtract from our list because it removes
				 * the node when you append to another node
				 */
				articleContent.appendChild(siblingNode);
				continue;
			}
		}

		/**
		 * So we have all of the content that we need. Now we clean it up for
		 * presentation. （因此我们拥有我们需要的所有内容了。现在我们来清洗它，为了很好的呈现）
		 */
		prepArticle(articleContent);

		return articleContent;
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
	 * Get the inner text of a node - cross browser compatibly. This also strips
	 * out any excess whitespace to be found. （获得一个节点内部的文本。并且根据参数，判断是否移除额外的空白）
	 * 
	 * @param e
	 * @param normalizeSpaces
	 * @return
	 */
	private static String getInnerText(Element e, boolean normalizeSpaces) {
		String textContent = e.text().trim();

		if (normalizeSpaces) {
			textContent = textContent.replaceAll(Patterns.REGEX_NORMALIZE, "");
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
	 * 
	 * @param e
	 */
	private static void cleanStyles(Element e) {
		if (e == null) {
			return;
		}

		Element cur = e.children().first();

		// Remove any root styles, if we're able.
		if (!"readability-styled".equals(e.className())) {
			e.removeAttr("style");
		}

		// Go until there are no more child nodes
		while (cur != null) {
			// Remove style attributes
			if (!"readability-styled".equals(cur.className())) {
				cur.removeAttr("style");
			}
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
		e.html(e.html().replaceAll(Patterns.REGEX_KILL_BREAKS, "<br />"));
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
	 * Helper utility to determine whether a given String is empty.
	 * 
	 * @param s
	 * @return
	 */
	private static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

}
