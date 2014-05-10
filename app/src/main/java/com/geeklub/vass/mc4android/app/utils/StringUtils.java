package com.geeklub.vass.mc4android.app.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class StringUtils {
	public static String fillStringByArgs(String str, String... arr) {
		Matcher m = Pattern.compile("\\{(\\d)\\}").matcher(str);
		while (m.find()) {
			// str = str.replace(m.group(),
			// URLEncoder.encode(arr[Integer.parseInt(m.group(1))]));
			str = str.replace(m.group(), arr[Integer.parseInt(m.group(1))]);
		}
		return str;
	}

	public static boolean isBlank(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

	public static List<NameValuePair> getParamFromString(String str) {
		Matcher m = Pattern.compile("([^?&]+)=([^?&]+)").matcher(str);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		while (m.find()) {
			NameValuePair nameValuePair = new BasicNameValuePair(m.group(1),
					m.group(2));
			nameValuePairs.add(nameValuePair);
		}
		return nameValuePairs;
	}
}
