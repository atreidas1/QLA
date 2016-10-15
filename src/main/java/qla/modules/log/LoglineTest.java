package qla.modules.log;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import qla.modules.confuguration.AppConfiguration;
import qla.modules.confuguration.ConfKeys;

public class LoglineTest {
	static LogConfiguration logConfiGuration;
	String Logline = "2016-09-16 20:22:43,089 [DEBUG] [http-0.0.0.0-8080-3] [346B12870F6E880A3ECCF2E7CC847005] [com.datalex.matrix.servants.mastertable.manage.impl.oracle.OracleManageAncillaryAirComponentSearchImpl] processAncillaryAirComponentDescr:";
	@BeforeClass
	public static void setUp() {
		logConfiGuration = new LogConfiguration();
		logConfiGuration.setDatePattern(AppConfiguration.getProperty(ConfKeys.DATE_PATTERN))
		.setEncoding("UTF-8")
		.setFullClassnamePattern(AppConfiguration.getProperty(ConfKeys.CLASS_NAME_PATTERN))
		.setLoglevelPattern(AppConfiguration.getProperty(ConfKeys.LOG_LEVEL_PATTERN))
		.setThreadPattern(AppConfiguration.getProperty(ConfKeys.THREAD_PATTERN))
		.setTimePattern(AppConfiguration.getProperty(ConfKeys.TIME_PATTERN))
		.setJsessionIdPattern(AppConfiguration.getProperty(ConfKeys.JSESSION_ID_PATTERN))
		.setNewLoglinePatern(AppConfiguration.getProperty(ConfKeys.START_OF_LOGLINE_PATTERN));
	}
	@Test
	public void test() {
		Logline logline = new Logline(Logline, logConfiGuration);
		assertEquals("2016-09-16", logline.getDateString());
		assertEquals("20:22:43,089", logline.getTimeString());
		assertEquals("DEBUG", logline.getLoglevel());
		assertEquals("http-0.0.0.0-8080-3", logline.getThread());
		assertEquals("346B12870F6E880A3ECCF2E7CC847005", logline.getJsessionId());
		assertEquals("com.datalex.matrix.servants.mastertable.manage.impl.oracle.OracleManageAncillaryAirComponentSearchImpl",
				logline.getFullClassName());
		assertEquals("OracleManageAncillaryAirComponentSearchImpl", logline.getclassName());
	}

}
