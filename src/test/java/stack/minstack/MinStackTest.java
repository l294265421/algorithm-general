package stack.minstack;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MinStackTest {
	private MinStack minStack;
	
	@Before
	public void init() {
		minStack = new MinStack(5);
	}

	@Test
	public void testMinStack() {
		minStack.push("5");
		minStack.push("3");
		minStack.push("4");
		minStack.push("1");
		minStack.push("9");
		System.out.println("栈顶元素:" + minStack.pop());
		System.out.println("栈中最小值:" + minStack.getMin());
		minStack.pop();
		System.out.println("栈中最小值:" + minStack.getMin());
	}
}
