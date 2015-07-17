package com.tiny.framework.util;

import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static boolean isEmpty(String str) {
		return str == null || ("").trim().equals(str) || ("").trim().equals(str);
	}
	public static boolean isEmpty(CharSequence str){
		return str == null || ("").trim().equals(str) || ("").trim().equals(str);
		
	}
	public static boolean isEmpty(EditText txt) {
		return txt==null?true:isEmpty(txt.getText().toString().trim());

	}

	public static boolean isEmpty(TextView txt) {
		return txt==null?true:isEmpty(txt.getText().toString().trim());

	}
	public static String trim(String str){
		if(str==null){
			return "";
		}else{
			return str.trim();
		}
	}

	public static String formatTime(String t) {
		String time = "";
		if (!StringUtil.isEmpty(t)) {
			try {
				Date d = new Date(Long.parseLong(t));
				time = sdf.format(d);
			} catch (Exception e) {
				return "";
			}
		}
		return time;
	}
	
	public static String formatTime2(String t) {
		String time = "";
		if (!StringUtil.isEmpty(t)) {
			try {
				Date d = new Date(Long.parseLong(t));
				time = sdf2.format(d);
			} catch (Exception e) {
				return "";
			}
		}
		return time;
	}
	
	public static String getCurrentTime() {
		String time = "";
		time = sdf.format(new Date());
		return time;
	}

	public static boolean EmailFormat(String email) {// 邮箱判断正则表达式
		Pattern pattern = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher mc = pattern.matcher(email);
		return mc.matches();
	}
	
	public static boolean URlFormat(String url) {// 邮箱判断正则表达式
		Pattern pattern = Pattern
				.compile("[a-zA-z]+://[^\\s]*");
		Matcher mc = pattern.matcher(url);
		return mc.matches();
	}
	
	

	public static String getTowLinesText(String line1, String line2) {
		return  line1 + "\n" + line2;

	}

	public static boolean TelFormat(String telphone) {
		String telRegex = "[1][358]\\d{9}";
		Pattern pattern = Pattern.compile(telRegex);
		Matcher matcher = pattern.matcher(telphone);
		return matcher.matches();
	}

	public static boolean PasswordFormat(String telphone) {
		String telRegex = "\\w{6,16}";
		Pattern pattern = Pattern.compile(telRegex);
		Matcher matcher = pattern.matcher(telphone);
		return matcher.matches();
	}
	
	public static boolean NickNameFormat(EditText nickname) {
		if(nickname == null)
			return false;
		return nickname.getText().toString().length() >= 1 && nickname.getText().toString().length() <= 10;
	}

	public static boolean JsonIsNull(JSONObject json, String text) {
		try {
			return !(json.has(text) && !"".equals(json.getString(text))
					&& !(json.getString(text) == null));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
