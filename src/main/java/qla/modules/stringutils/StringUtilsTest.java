package qla.modules.stringutils;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testRemoveTailAndLeadParts() {
		String string = "[[debug]]";
		assertEquals("debug", StringUtils.removeTailAndLeadParts(string, "[[", "]]"));
	}
	@Test
	public void testgetPartOfSplitedString() throws Exception {
		String string = "com.datalex";
		assertEquals("datalex", StringUtils.getPartOfSplitedString(string, -1, "[.]"));
	}

	@Test
	public void testgetStringBetveenChars() throws Exception {
		String string = "<UserLoginSvRQ MajorVersion=\"1\" UniqueId=\"1474885241654_0.5330170521801771\"";
		Pattern pattern = Pattern.compile("<(\\w*?) ");
		System.out.println(StringUtils.getStringBetveenChars(pattern, string));
		assertEquals("UserLoginSvRQ", StringUtils.getStringBetveenChars(pattern, string));
	}
}
