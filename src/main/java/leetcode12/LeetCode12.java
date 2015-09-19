package leetcode12;

public class LeetCode12 {
    public String intToRoman(int num) {
    	StringBuffer roman = new StringBuffer();
    	
    	String numStr = String.valueOf(num);
    	int len = numStr.length();
    	for(int i = 0; i < len; i++) {
    		roman.append(intToRoman(Integer.valueOf(
    				numStr.substring(i, i + 1)), len - 1 - i));
    	}
    	
        return roman.toString();
    }
    
    public String intToRoman(int num, int position) {
    	StringBuffer roman = new StringBuffer();
    	String one = "";
    	String five = "";
    	String ten = "";
        if (position == 0) {
        	one = "I";
        	five = "V";
        	ten = "X";
		} else if (position == 1){
			one = "X";
        	five = "L";
        	ten = "C";
		}
		else if (position == 2){
			one = "C";
        	five = "D";
        	ten = "M";
		} else {
			one = "M";
		}
        
        if (num <= 3) {
			for(int i = 0; i < num; i++) {
				roman.append(one);
			}
		} else if(num == 4) {
			roman.append(one).append(five);
		} else if(num == 5) {
			roman.append(five);
		} else if(num < 9) {
			roman.append(five);
			for(int i = 6; i <= num; i++) {
				roman.append(one);
			}
		} else {
			roman.append(one).append(ten);
		}
        return roman.toString();
    }
    
    public static void main(String[] args) {
		LeetCode12 leetCode12 = new LeetCode12();
		System.out.println(leetCode12.intToRoman(1984));
	}
}
