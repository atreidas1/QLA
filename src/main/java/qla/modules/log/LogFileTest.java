package qla.modules.log;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import qla.modules.confuguration.AppConfiguration;
import qla.modules.confuguration.ConfKeys;

public class LogFileTest {

	@Test
	public void test() throws IOException {
		LogConfiguration logConfiGuration = new LogConfiguration();
		logConfiGuration.setDatePattern(AppConfiguration.getProperty(ConfKeys.DATE_PATTERN))
		.setEncoding("UTF-8")
		.setFullClassnamePattern(AppConfiguration.getProperty(ConfKeys.CLASS_NAME_PATTERN))
		.setLoglevelPattern(AppConfiguration.getProperty(ConfKeys.LOG_LEVEL_PATTERN))
		.setThreadPattern(AppConfiguration.getProperty(ConfKeys.THREAD_PATTERN))
		.setTimePattern(AppConfiguration.getProperty(ConfKeys.TIME_PATTERN))
		.setNewLoglinePatern(AppConfiguration.getProperty(ConfKeys.START_OF_LOGLINE_PATTERN));
		LogFile logFile = new LogFile("d:\\testlog.txt", logConfiGuration);
		for (int i = 0; i < 10; i++) {
			Logline logline = logFile.nextLogline();
			System.out.println(logline);
		}
	}

}
