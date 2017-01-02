package encrypt;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSA {
	private static Logger logger = LoggerFactory.getLogger("consoleLogger");
	private static final String ALGORITHM_NAME = "RSA";
	private static final Charset CHARSET = Charset.forName("utf-8");
	private RSA() {
	}
	
	/**
	 * 
	 * @param input 需要加密的字符串
	 * @param encodedPublicKey 被编码后的公钥
	 * @return 加密后以base64编码的字符串
	 */
	public static String publicKeyEncrypt(String input, byte[] encodedPublicKey) {
		if (input == null || encodedPublicKey == null) {
			throw new NullPointerException();
		}
		
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] result = cipher.doFinal(input.getBytes());
			return Base64.encodeBase64String(result);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("{}", e);
		}
		return "";
	}
	
	public static String privateKeyDecrypt(String base64EncodeEncryptString, byte[] encodePrivateKey) {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodePrivateKey);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] result = cipher.doFinal(base64EncodeEncryptString.getBytes());
			return new String(result, CHARSET);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("{}", e);
		}
		return "";
	}
	
	public static String privateKeyEncrypt(String input, byte[] encodedPrivateKey) {
		try {
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] result = cipher.doFinal(input.getBytes());
			return Base64.encodeBase64String(result);
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return "";
	}
	
	public static String publicKeyDecrypt(String base64EncodeEncryptString, byte[] encodedPuplicKey) {
		try{
			// 3.私钥加密、公钥解密——解密
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(encodedPuplicKey);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] result = cipher.doFinal(base64EncodeEncryptString.getBytes());
			return new String(result, CHARSET);
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return "";
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_NAME);
		keyPairGenerator.initialize(512);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) keyPair.getPrivate();
		
		String input = "liyuncong";
		// 公钥加密，私钥揭秘
		String base64EncodeEncryptString = publicKeyEncrypt(input, rsaPublicKey.getEncoded());
		logger.info("{}被加密后以base64编码的结果", input, base64EncodeEncryptString);
		logger.info("{}被加密后以base64编码的字符串解密后的结果{}", input, privateKeyDecrypt(base64EncodeEncryptString, rsaPrivateCrtKey.getEncoded()));
	}
}
