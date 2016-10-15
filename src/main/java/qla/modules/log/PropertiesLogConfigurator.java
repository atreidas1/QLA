package qla.modules.log;

import java.util.Iterator;
import java.util.List;

import qla.modules.confuguration.AppConfiguration;
import qla.modules.confuguration.ConfKeys;
import qla.modules.loganalyser.LogAnalyseUtils;

public class PropertiesLogConfigurator implements ILogConfigurator{

	@Override
	public LogConfiguration getLogConfiguration() {
		LogConfiguration logConfiguration = new LogConfiguration();
		logConfiguration.setDatePattern(AppConfiguration.getProperty(ConfKeys.DATE_PATTERN))
		.setEncoding(AppConfiguration.getProperty(ConfKeys.ENCODING))
		.setFullClassnamePattern(AppConfiguration.getProperty(ConfKeys.CLASS_NAME_PATTERN))
		.setLoglevelPattern(AppConfiguration.getProperty(ConfKeys.LOG_LEVEL_PATTERN))
		.setThreadPattern(AppConfiguration.getProperty(ConfKeys.THREAD_PATTERN))
		.setTimePattern(AppConfiguration.getProperty(ConfKeys.TIME_PATTERN))
		.setJsessionIdPattern(AppConfiguration.getProperty(ConfKeys.JSESSION_ID_PATTERN))
		.setNewLoglinePatern(AppConfiguration.getProperty(ConfKeys.START_OF_LOGLINE_PATTERN))
		.setConversationPattern(compileGlobalPattern(ConfKeys.GLOBAL_PATTERN));
		return logConfiguration;
	}

	String compileGlobalPattern(String globalPattern) {
		String compilled = globalPattern;
		List<String> relacements = LogAnalyseUtils
		.getPartsOfStringByPattern(AppConfiguration.getProperty(ConfKeys.REPLACE_PATTERN), globalPattern);
		for (Iterator<String> iterator = relacements.iterator(); iterator.hasNext();) {
			String replacement = iterator.next();
			String keyForPattern = replacement.substring(1, replacement.length()-1);
			compilled = compilled
					.replace(replacement, AppConfiguration.getProperty(keyForPattern, ""));
		}
		return compilled;
	}
}
