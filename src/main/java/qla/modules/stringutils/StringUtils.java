package qla.modules.stringutils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * Check string for null or string.isEmpty()
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}

	/**
	 * Return the first part of string that matches given pattern, if no
	 * matchers found - return empty string.
	 * 
	 * @param searchPattern
	 * @param string
	 * @return
	 */
	public static String getPartOfStringByPattern(String searchPattern, String string) {
		Pattern pattern = Pattern.compile(searchPattern);
		return getPartOfStringByPattern(pattern, string);
	}

	/**
	 * Return the first part of string that matches given pattern, if no
	 * matchers found - return empty string.
	 * 
	 * @param searchPattern
	 * @param string
	 * @return
	 */
	public static String getPartOfStringByPattern(Pattern searchPattern, String string) {
		String partOfString = "";
		Matcher matcher = searchPattern.matcher(string);
		if (matcher.find()) {
			partOfString = matcher.group();
		}
		return partOfString;
	}
	
	/**
	 * Return the first part of string that matches given pattern, if no
	 * matchers found - return empty string.
	 * 
	 * @param searchPattern
	 * @param string
	 * @return
	 */
	public static String getStringBetveenChars(Pattern searchPattern, String string) {
		String partOfString = "";
		Matcher matcher = searchPattern.matcher(string);
		if (matcher.find()) {
			if(matcher.groupCount()>=1){
				partOfString = matcher.group(1);
			}
		}
		return partOfString;
	}

	/**
	 * Return the list of all parts of string that matches given pattern, if no
	 * matchers found - return empty list.
	 * 
	 * @param searchPattern
	 * @param string
	 * @return
	 */
	public static List<String> getPartsOfStringByPattern(String searchPattern, String string) {
		Pattern pattern = Pattern.compile(searchPattern);
		return getPartsOfStringByPattern(pattern, string);
	}

	/**
	 * Return the list of all parts of string that matches given pattern, if no
	 * matchers found - return empty list.
	 * 
	 * @param searchPattern
	 * @param string
	 * @return
	 */
	public static List<String> getPartsOfStringByPattern(Pattern searchPattern, String string) {
		List<String> partsOfString = new ArrayList<>();
		Matcher matcher = searchPattern.matcher(string);
		while (matcher.find()) {
			partsOfString.add(matcher.group());
		}
		return partsOfString;
	}

	/**
	 * Remove leading and tailing parts of string.
	 * 
	 * @param string
	 * @param leadingPart
	 * @param tailingPart
	 * @return
	 */
	public static String removeTailAndLeadParts(String string, String leadingPart, String tailingPart) {
		if (string.startsWith(leadingPart) && string.endsWith(tailingPart)) {
			int startIndex = leadingPart.length();
			int endIndex = string.length() - tailingPart.length();
			return string.substring(startIndex, endIndex);
		}
		return string;
	}

	public static String getPartOfSplitedString(String string, int partNumber, String separator) {
		String[] parts = string.split(separator);
		if (parts.length > 0) {
			if (partNumber == -1) {
				return parts[parts.length - 1];
			}
		}
		return parts[partNumber];
	}
}
