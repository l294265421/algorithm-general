package md;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class MD {
	private MD() {
		
	}

	public static String MD5(String input) {
		String md5 = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] md5bytes = messageDigest.digest(input.getBytes());
			md5 = Hex.encodeHexString(md5bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}

	public static String MD4(String input) {
		String md4 = "";
		try {
			Security.addProvider(new BouncyCastleProvider());
			MessageDigest messageDigest = MessageDigest.getInstance("MD4");
			byte[] md4bytes = messageDigest.digest(input.getBytes());
			md4 = Hex.encodeHexString(md4bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md4;
	}

	public static String MD2(String input) {
		String md2 = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD2");
			byte[] md2bytes = messageDigest.digest(input.getBytes());
			md2 = Hex.encodeHexString(md2bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md2;
	}
}
