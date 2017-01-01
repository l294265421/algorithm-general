package md;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class MD {
	public static final String ALGORITHM_NAME_MD5 = "md5";
	public static final String ALGORITHM_NAME_MD4 = "md4";
	public static final String ALGORITHM_NAME_MD2 = "md2";
	public static final String ALGORITHM_NAME_SHA1 = "sha1";
	
	private MD() {
		
	}

	public static String md(String input, String algorithmName) {
		String result = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithmName);
			byte[] mdbytes = messageDigest.digest(input.getBytes());
			result = Hex.encodeHexString(mdbytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String name = "liyuncong";
		System.out.println(MD.md(name, ALGORITHM_NAME_SHA1));
	}
}
