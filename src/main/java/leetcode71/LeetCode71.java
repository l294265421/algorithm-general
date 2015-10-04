package leetcode71;

public class LeetCode71 {
	/**
	 * 处理的路径以/结尾，如果路径不是以/
	 * 结尾，就在路径后面添上/再处理;
	 * 收获：被处理的数据必须跟之前假定程序
	 * 处理的数据格式一致
	 * @param path
	 * @return
	 */
	public String simplifyPath(String path) {
		if (path == null) {
			return path;
		}
		int len = path.length();
		if (len == 0) {
			return path;
		}
		path = path  + "/";
		int lastSlashIndex = path.indexOf("/");
		int thisSlashIndex = path.indexOf("/", lastSlashIndex + 1);
		while (thisSlashIndex != -1) {
			if (thisSlashIndex - lastSlashIndex == 1) {
				path = path.substring(0, lastSlashIndex) + 
						path.substring(thisSlashIndex);
				thisSlashIndex = path.indexOf("/", lastSlashIndex + 1);
			} else {
				String tempString = path.substring(lastSlashIndex + 1, 
						thisSlashIndex);
				if (tempString.equals(".")) {
					path = path.substring(0, lastSlashIndex) + 
							path.substring(thisSlashIndex);
					thisSlashIndex = path.indexOf("/", lastSlashIndex + 1);
				} else if (tempString.equals("..")) {
					if (lastSlashIndex == 0) {
						path = path.substring(thisSlashIndex);
						thisSlashIndex = path.indexOf("/", 
								lastSlashIndex + 1);
					} else {
						lastSlashIndex = path.lastIndexOf("/", 
								lastSlashIndex - 1);
						path = path.substring(0, lastSlashIndex) + 
								path.substring(thisSlashIndex);
						thisSlashIndex = path.indexOf("/", lastSlashIndex
								 + 1);
					}
				} else {
					lastSlashIndex = thisSlashIndex;
					thisSlashIndex = path.indexOf("/", lastSlashIndex + 1);
				}
			}
		}
		
		len = path.length();
		if (len > 1 && path.charAt(len - 1 ) == '/') {
			path = path.substring(0, len - 1);
		}
		return path;
	}
	
	public static void main(String[] args) {
		LeetCode71 leetCode71 = new LeetCode71();
		System.out.println(leetCode71.simplifyPath("/"));
	}
}
