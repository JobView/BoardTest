package com.wzf.boardgame.utils;


import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 算法工具类
 * 
 * @author chenlong 2015-12-17
 * 
 */
public class MathUtilAndroid {

	/**
	 * AES加密公钥
	 */
	private static final String AES_PUBLIC_KEY = "hdgZGsUpkBdm8Q5bZ4aLqw==";
	
	public static final String ENCODING = "UTF-8";
	
	/**
	 * 进行base64编码
	 * @param input
	 *            要编码的数据
	 * @return
	 * @throws Exception
	 */
	public static byte[] encodeBase64(byte[] input) throws Exception {
		return Base64.encode(input, Base64.DEFAULT);
//		Class<?> clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
//		Method mainMethod = clazz.getMethod("encode", byte[].class);
//		mainMethod.setAccessible(true);
//		Object retObj = mainMethod.invoke(null, new Object[] { input });
//		return (String) retObj;
	}
	
	/**
	 * 进行base64解码
	 * @param input
	 *            要解码的数据
	 * @return
	 * @throws Exception
	 */
	public static byte[] decodeBase64(String input) throws Exception{
		return Base64.decode(input, Base64.DEFAULT);
//		Class<?> clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
//		Method mainMethod= clazz.getMethod("decode", String.class);
//		mainMethod.setAccessible(true);
//		 Object retObj=mainMethod.invoke(null, input);
//		 return (byte[])retObj;
	}

	/**
	 * AES加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encodeAES(String data){
		String content = null;
		try {
			byte[] key = decodeBase64(AES_PUBLIC_KEY);
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");//恢复密钥
			Cipher cipher = Cipher.getInstance("AES");//Cipher完成加密或解密工作类
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);//对Cipher初始化，解密模式
			byte[] cipherByte = cipher.doFinal(data.getBytes());//加密data
			content = new String(encodeBase64(cipherByte));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}
	
	/**
	 * AES解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decodeAES(String data) {
		byte[] cipherByte = new byte[0];//解密data
		try {
			byte[] key = decodeBase64(AES_PUBLIC_KEY);

			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");//恢复密钥
			Cipher cipher = Cipher.getInstance("AES");//Cipher完成加密或解密工作类
			cipher.init(Cipher.DECRYPT_MODE, secretKey);//对Cipher初始化，解密模式
			cipherByte = cipher.doFinal(decodeBase64(data));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String(cipherByte);
	}

	/**
	 * md5加密
	 * @param plainText
	 * @return
	 */
	public static String md5Encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}

}
