package com.shaoxia.server.common.utils;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-14 15:23
 */
public class IdUtils {
	public static String userIdToRoomKey(Long id1,Long id2){
		Long small = id1>id2?id1:id2;
		Long big = id1<id2?id1:id2;
		return String.format("%s%s",small.toString(),big.toString());
	}

	public static boolean isValidStringToInteger(String... ids){
		for (String id : ids) {
			// 使用正则表达式检查字符串是否只包含数字字符
			boolean isValidNumericString = id.matches("\\d+");
			// 检查字符串的长度是否超过Long的最大长度
			boolean isWithinMaxLongLength = id.length() <= String.valueOf(Integer.MAX_VALUE).length();
			// 字符串转换为Long时是否会抛出异常
			boolean isValidLong = true;
			try {
				Integer.parseInt(id);
			} catch (NumberFormatException e) {
				isValidLong = false;
			}
			// 如果有任何一个检查不通过，则返回false
			if (!(isValidNumericString && isWithinMaxLongLength && isValidLong)) {
				return false;
			}
		}

		// 所有ID都通过检查，返回true
		return true;
	}

	public static boolean isValidNumericString(String... ids) {
		for (String id : ids) {
			// 使用正则表达式检查字符串是否只包含数字字符
			boolean isValidNumericString = id.matches("\\d+");

			// 检查字符串的长度是否超过Long的最大长度
			boolean isWithinMaxLongLength = id.length() <= String.valueOf(Long.MAX_VALUE).length();

			// 字符串转换为Long时是否会抛出异常
			boolean isValidLong = true;
			try {
				Long.parseLong(id);
			} catch (NumberFormatException e) {
				isValidLong = false;
			}

			// 如果有任何一个检查不通过，则返回false
			if (!(isValidNumericString && isWithinMaxLongLength && isValidLong)) {
				return false;
			}
		}

		// 所有ID都通过检查，返回true
		return true;
	}

}
