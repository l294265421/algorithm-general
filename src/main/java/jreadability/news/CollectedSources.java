package jreadability.news;

import java.util.HashSet;

/**
 * 用于管理已知的新闻来源；
 * 是一个单例类；
 * @author liyuncong
 *
 */
public class CollectedSources {
	private HashSet<String> sources = new HashSet<>();
	
	private CollectedSources() {
		// 初始化sources
	}
	
	/**
	 * 将发现的一个源添加到已收集的源里面，如果之前已经被收集过，
	 * 这个操作将不会产生任何影响
	 * @param source
	 */
	public synchronized void add(String source) {
		boolean isContain = this.sources.add(source);
		// 如果该来源之前没被收集，这里就需要将它持久化
		if (isContain) {
			// 持久source
		}
	}
	
	/**
	 * 通过查看candidate是否在我们收集的sources里面来确定这个candidate
	 * 是否是来源
	 * @param candidate
	 * @return
	 */
	public synchronized boolean isSource(String candidate) {
		boolean isContain = this.sources.contains(sources);
		return isContain;
	}
	
	private static class CollectedSourcesSingletonHolder {
		static CollectedSources instance = new CollectedSources();
	}
	
	public static CollectedSources getInstance() {
		return CollectedSourcesSingletonHolder.instance;
	}
}
