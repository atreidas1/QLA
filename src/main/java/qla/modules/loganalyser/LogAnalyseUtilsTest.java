package qla.modules.loganalyser;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class LogAnalyseUtilsTest {

	@Test
	public void test() {
		String string = "{log} {name}{name_1}";
		String pattern = "\\{[a-zA-Z_0-9]{1,}\\}";
		List<String> strings = LogAnalyseUtils.getPartsOfStringByPattern(pattern, string);
		System.out.println(strings);
	}

}
