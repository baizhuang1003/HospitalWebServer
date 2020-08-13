package com.tianyuan.core;

public class Convertor {

	public static int toInt(Object o) {
		
		if(o==null)return 0;
		String str = o.toString();
		if(str==null||str.equals(""))return 0;
		try {
			return Integer.parseInt(str);
		}
		catch(Exception e) {
			return 0;
		}
	}
	
	
	public static boolean isNull(String s) {
		if (s == null || s.equals(""))
			return true;
		else
			return false;
	}

	public static String padLeft(String src, int len, char ch) {
		int diff = len - src.length();
		if (diff <= 0) {
			return src;
		}

		char[] charr = new char[len];
		System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
		for (int i = src.length(); i < len; i++) {
			charr[i] = ch;
		}
		return new String(charr);
	}

	public static String padRight(String src, int len, char ch) {
		int diff = len - src.length();
		if (diff <= 0) {
			return src;
		}

		char[] charr = new char[len];
		System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
		for (int i = 0; i < diff; i++) {
			charr[i] = ch;
		}
		return new String(charr);
	}
}
