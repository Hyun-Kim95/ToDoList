package com.toy.kh.ToDoList.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Util {

	public static String getNowDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();
		
		return format1.format(time);
	}
	
	public static String getTomorrowDateStr() {
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return dtf.format(cal.getTime());
    }
	
	// homeController에서 사용함
	public static String msgAndBack(String msg) {
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('" + msg + "');");
		sb.append("history.back();");
		sb.append("</script>");

		return sb.toString();
	}

	public static String msgAndReplace(String msg, String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('" + msg + "');");
		sb.append("location.replace('" + url + "');");
		sb.append("</script>");

		return sb.toString();
	}
	// 해당 월의 마지막 일자를 리턴
	public static int lastday(int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.set(year,month,1);

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	// 받은 인자들을 map으로 리턴해줌
	public static Map<String, Object> mapOf(Object... args) {
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException("인자를 짝수개 입력해주세요.");
		}

		int size = args.length / 2;
		// 키의 순서가 지켜짐(LinkedHashMap)
		Map<String, Object> map = new LinkedHashMap<>();

		for (int i = 0; i < size; i++) {
			int keyIndex = i * 2;
			int valueIndex = keyIndex + 1;

			String key;
			Object value;

			try {
				key = (String) args[keyIndex];
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("키는 String으로 입력해야 합니다. " + e.getMessage());
			}

			value = args[valueIndex];

			map.put(key, value);
		}

		return map;
	}
	
	// 회원가입시 모든 글자가 숫자로만 되어 있는지 확인
	public static boolean allNumberString(String str) {
		if ( str == null ) {
			return false;
		}
		
		if ( str.length() == 0 ) {
			return true;
		}
		
		for ( int i = 0; i < str.length(); i++ ) {
			if ( Character.isDigit(str.charAt(i)) == false ) {
				return false;
			}
		}
		
		return true;
	}
	// 회원가입시 숫자로 시작하는지 확인
	public static boolean startsWithNumberString(String str) {
		if ( str == null ) {
			return false;
		}
		
		if ( str.length() == 0 ) {
			return false;
		}
		
		return Character.isDigit(str.charAt(0));
	}
	// 회원가입시 영문과 숫자로만 조합이 가능
	public static boolean isStandardLoginIdString(String str) {
		if ( str == null ) {
			return false;
		}
		
		if ( str.length() == 0 ) {
			return false;
		}
		
		// 조건
		// 5자 이상, 20자 이하로 구성
		// 숫자로 시작 금지
		// _, 알파벳, 숫자로만 구성
		return Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,19}$", str);
	}
	
	public static boolean isEmpty(Object data) {
		if (data == null) {
			return true;
		}

		if (data instanceof String) {
			String strData = (String) data;

			return strData.trim().length() == 0;
		} else if (data instanceof Integer) {
			Integer integerData = (Integer) data;

			return integerData != 0;
		} else if (data instanceof List) {
			List listData = (List) data;

			return listData.isEmpty();
		} else if (data instanceof Map) {
			Map mapData = (Map) data;

			return mapData.isEmpty();
		}

		return true;
	}
	
	public static <T> T ifEmpty(T data, T defaultValue) {
		if ( isEmpty(data) ) {
			return defaultValue;
		}
		
		return data;
	}
}
