package miningDataRegion;

public class TestOutSwit {
	public static void main(String[] args) {
		int num = 20;
		outSwith: switch (num) {
		case 10:

			break;

		case 20:
			
			for(int i = 0; i < num; i++) {
				if (i == 10) {
					break outSwith;
				}
				System.out.println(i);
			}
			System.out.println(num);
			break;
		}
	}
}
