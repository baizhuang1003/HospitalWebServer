package com.tianyuan.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	public static String getLongDateTime() {
		return getLongDateTime(new Date());
	}
	public static String getLongDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}
