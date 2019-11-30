package velocity.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 字符串集合类.主要针对字符串常用的一些方法。
 * 
 * @see ArrayList
 * @see LinkedList
 * @see List
 */
public class StringUtils {

	/**
	 * 定义缺省的64位编码常量值
	 */
	public static final String DEFAULT_64_CODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	/**
	 * 私有构造函数，不允许外部类对此类的new创建
	 */
	private StringUtils() {
	}

	public static String trim(String str, String defult) {
		if (str == null) {
			return trim(defult);
		} else {
			return str.trim();
		}
	}

	public static String trim(Object str) {
		if (str == null)
			return "";
		else
			return str.toString().trim();
	}

	public static String trim(Object str, String defult) {
		if (str == null)
			return trim(defult);
		else
			return str.toString().trim();
	}

	public static String nonNull(String str) {
		if (str == null)
			return "";
		else
			return str;
	}

	public static int getAsInt(String str) {
		return getAsInt(str, -1);
	}

	public static int getAsInt(String str, int defaultv) {
		if (str == null || "".equals(str)) {
			return defaultv;
		}
		try {
			return Integer.parseInt(str, 10);
		} catch (Exception e) {
			return defaultv;
		}

	}

	public static long getAsLong(String str) {
		return getAsLong(str, -1L);
	}

	public static long getAsLong(String str, long defaultv) {
		if (str == null || "".equals(str)) {
			return defaultv;
		}
		try {
			return Long.parseLong(str, 10);
		} catch (Exception e) {
			return defaultv;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String[] split(String str, String seperator) {
		if (str == null || str.equals("") || trim(seperator).equals("")) {
			return new String[0];
		}
		if (str.endsWith(seperator)) {
			str = str.substring(0, str.length() - seperator.length());
		}
		ArrayList temp = new ArrayList();
		int oldPos = 0;
		int newPos = str.indexOf(seperator);
		int parentLength = str.length();
		int subStrLength = seperator.length();
		if (newPos != -1) {
			newPos += subStrLength;
		}
		while (newPos <= parentLength && newPos != -1) {
			temp.add(str.substring(oldPos, newPos - subStrLength));
			oldPos = newPos;
			newPos = str.indexOf(seperator, oldPos);
			if (newPos != -1) {
				newPos += seperator.length();
			}
		}
		if (oldPos <= parentLength) {
			temp.add(str.substring(oldPos));
		}
		return (String[]) temp.toArray(new String[temp.size()]);
	}

	public static String encodeBase64(byte data[]) {
		int len = data.length;
		StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);
		for (int i = 0; i < len; i++) {
			int c = data[i] >> 2 & 0x3f;
			ret.append(DEFAULT_64_CODE.charAt(c));
			c = data[i] << 4 & 0x3f;
			if (++i < len)
				c |= data[i] >> 4 & 0xf;
			ret.append(DEFAULT_64_CODE.charAt(c));
			if (i < len) {
				c = data[i] << 2 & 0x3f;
				if (++i < len)
					c |= data[i] >> 6 & 0x3;
				ret.append(DEFAULT_64_CODE.charAt(c));
			} else {
				i++;
				ret.append('=');
			}
			if (i < len) {
				c = data[i] & 0x3f;
				ret.append(DEFAULT_64_CODE.charAt(c));
			} else {
				ret.append('=');
			}
		}

		return ret.toString();
	}

	public static String decodeBase64(String data) {
		return decodeBase64(data.getBytes());
	}

	public static String decodeBase64(byte data[]) {
		int len = data.length;
		StringBuffer ret = new StringBuffer((len * 3) / 4);
		for (int i = 0; i < len; i++) {
			int c = DEFAULT_64_CODE.indexOf(data[i]);
			i++;
			int c1 = DEFAULT_64_CODE.indexOf(data[i]);
			c = c << 2 | c1 >> 4 & 0x3;
			ret.append((char) c);
			if (++i < len) {
				c = data[i];
				if (61 == c)
					break;
				c = DEFAULT_64_CODE.indexOf((char) c);
				c1 = c1 << 4 & 0xf0 | c >> 2 & 0xf;
				ret.append((char) c1);
			}
			if (++i >= len)
				continue;
			c1 = data[i];
			if (61 == c1)
				break;
			c1 = DEFAULT_64_CODE.indexOf((char) c1);
			c = c << 6 & 0xc0 | c1;
			ret.append((char) c);
		}

		return ret.toString();
	}

	public static String join(String list[], String seperator) {
		StringBuffer result = new StringBuffer();
		if (list == null || list.length <= 0)
			return "";
		for (int i = 0; i < list.length; i++) {
			if (list[i] != null)
				result.append(list[i].toString());
			if (i != list.length - 1)
				result.append(seperator);
		}

		return result.toString();
	}

	public static String join(long[] values, String seperator) {
		StringBuffer result = new StringBuffer();
		if (values == null || values.length <= 0)
			return "";
		for (int i = 0; i < values.length; i++) {
			result.append(trim(values[i]));
			if (i != values.length - 1) {
				result.append(seperator);
			}
		}

		return result.toString();
	}

	public static boolean in(String str, String array[]) {
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				if (str == null && array[i] == null) {
					return true;
				}
				if (str != null && str.equals(array[i])) {
					return true;
				}
			}
		} else {
			return str == null;
		}
		return false;
	}

	public static int indexof(String strs[], String str) {
		if (str == null || strs == null)
			return -1;
		for (int i = 0; i < strs.length; i++)
			if (str.equals(strs[i]))
				return i;

		return -1;
	}

	public static boolean hasText(String str) {
		return !"".equals(nonNull(str));
	}

	/**
	 * 过滤数组中重复的数值只保留其中的一个
	 * 
	 * @param arrays
	 * @return String[]
	 */
	public static String[] filterDuplicate(String[] arrays) {
		if (arrays == null || arrays.length == 0) {
			return null;
		}
		List<String> list = new LinkedList<String>();
		for (int i = 0; i < arrays.length; i++) {
			if (!list.contains(arrays[i])) {
				list.add(arrays[i]);
			}
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean isEmptyArray(String[] arr) {
		if (arr == null || arr.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取两个数组之间的交集
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String[] getArraysIntersection(String[] arr1, String[] arr2) {
		if (isEmptyArray(arr1)) {
			if (isEmptyArray(arr2)) {
				return null;
			} else {
				return arr2;
			}
		} else {
			if (isEmptyArray(arr2)) {
				return arr1;
			} else {
				List vallist = new ArrayList();
				for (int i = 0; i < arr1.length; i++) {
					String val = arr1[i];
					if (in(val, arr2) && !vallist.contains(val)) {
						vallist.add(val);
					}
				}
				return (String[]) vallist.toArray(new String[0]);
			}
		}
	}

}
