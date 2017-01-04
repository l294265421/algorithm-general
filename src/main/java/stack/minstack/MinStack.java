package stack.minstack;

import java.util.ArrayList;
import java.util.List;

/**
 * 最小栈:有三个方法，出栈(pop)、入栈(push)和取得最小值(getMin)，三个方法
 * <br/>的时间复杂度都是O(1)。
 * 实现思路:用一个辅助栈，如果当前入栈元素是最小值，就将当前元素在栈中的位置
 * <br/>入栈到辅助栈，如果当前出栈元素是当前最小值，就将辅助栈栈顶元素出栈。
 * @author liyuncong
 *
 */
public class MinStack {
	private String[] stack;
	private int stackTop;
	private int[] helper;
	private int helperTop;
	private int capacity;
	
	/**
	 * 
	 * @param capacity 栈的容量
	 * @throws IllegalArgumentException if capacity is less than 1
	 */
	public MinStack(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("capacity is less than 1");
		}
		stack = new String[capacity];
		helper = new int[capacity];
		stackTop = -1;
		helperTop = -1;
		this.capacity = capacity;
	}
	
	/**
	 * 
	 * @return 栈顶元素
	 * @throws IllegalAccessError if stack is empty
	 */
	public String pop() {
		if (stackTop == -1) {
			throw new IllegalAccessError("stack is empty");
		}
		if (helper[helperTop] == stackTop) {
			helperTop--;
		}
		return stack[stackTop--];
	}
	
	/**
	 * 
	 * @param element
	 * @throws IllegalAccessError stack is full
	 */
	public void push(String element) {
		if (element == null) {
			throw new NullPointerException("element is null");
		}
		if (stackTop == capacity - 1) {
			throw new IllegalAccessError("stack is full");
		}
		stack[++stackTop] = element;
		if (stackTop == 0 || element.compareTo(stack[helper[helperTop]]) < 0) {
			helper[++helperTop] = stackTop;
		}
	}
	
	public String getMin() {
		if (helperTop == -1) {
			throw new IllegalAccessError("stack is empty");
		}
		return stack[helper[helperTop]];
	}
}
