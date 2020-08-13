package com.tianyuan.core;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Common {

	/***
	 * 随机数字
	 * @param count 长度
	 * @return
	 */
	public static final String randomNumber(int count) {
		Random random = new Random();
		String result="";
		for (int i=0;i<count;i++)
		{
			result+=random.nextInt(10);
		}
		return String.valueOf(result);
	}
	
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
	
	
	/***
	 * MD5 加密 有相对应的C# 加密方式保持一直
	 * @param inStr
	 * @return
	 */
	public static String md5Encode(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = inStr.getBytes("UTF-8");
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			return "";
		}
	}

	
	public static final String AES_key = "www.lzsou.cc8888";
	
	
	   /**将二进制转换成16进制 
     * @param buf 
     * @return 
     */  
    public static String parseByte2HexStr(byte buf[]) {  
            StringBuffer sb = new StringBuffer();  
            for (int i = 0; i < buf.length; i++) {  
                    String hex = Integer.toHexString(buf[i] & 0xFF);  
                    if (hex.length() == 1) {  
                            hex = '0' + hex;  
                    }  
                    sb.append(hex.toUpperCase());  
            }  
            return sb.toString();  
    } 
    
    /**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
            if (hexStr.length() < 1)  
                    return null;  
            byte[] result = new byte[hexStr.length()/2];  
            for (int i = 0;i< hexStr.length()/2; i++) {  
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                    result[i] = (byte) (high * 16 + low);  
            }  
            return result;  
    }
	/**
	 * AES加密
	 * 
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String str, String key) throws Exception {
		if (str == null || key == null)
			return null;
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
		byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
		return parseByte2HexStr(bytes);
	}

	/**
	 * AES解密
	 * 
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(String str, String key) throws Exception {
		if (str == null || key == null)
			return null;
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
		byte[] bytes = parseHexStr2Byte(str);
		bytes = cipher.doFinal(bytes);
		return new String(bytes, "utf-8");
	}
	
	/**
	 * 根据domain得到str
	 * @param domain
	 * @return
	 */
	public static String domainInfo(String domain) {
		if(domain.equals("www.akgcjx.cn")) return "s1435326080,2f8651a9b48f5f7fe49f5cae6d333283,521601";
		if(domain.equals("akgcjx.cn")) return "s1435326080,2f8651a9b48f5f7fe49f5cae6d333283,521601";
		if(domain.equals("hanzhong.cxgcjx.net")) return "s1435326080,12345678910,521601";
		if(domain.equals("shangluo.cxgcjx.net")) return "s1435326080,12345678910,521601";
		if(domain.equals("QRCode")) return "https://gcjxcx.cn/manage/query/emmsys/wxinfo";
		return "";
	}
	
	/**
	 * 是否开启必须短信验证
	 * @param domain
	 * @return
	 */
	public static boolean domainInfoLockItem(String domain) {
		if(domain.equals("www.akgcjx.cn")) return true;
		if(domain.equals("hanzhong.cxgcjx.net")) return false;
		if(domain.equals("shangluo.cxgcjx.net")) return false;
		return false;
	}
	
}
