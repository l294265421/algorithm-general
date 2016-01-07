package jreadability.news;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TestIterator {
	public static void main(String[] args) {
		List<String> test = new LinkedList<String>();
		test.add("0");
		test.add("1");
		test.add("2");
		test.add("3");
		test.add("4");
		test.add("5");
		Iterator<String> iterator = test.iterator();
		while (iterator.hasNext()) {
			String string = iterator.next();
			if (string.equals("2")) {
				iterator.remove();
			} else {
				System.out.println(string);
			}
		}
//		for (String string : test) {
//			if (string.equals("2")) {
//				test.remove("2");
//			} else {
//				System.out.println(string);
//			}
//		}
	}
}
