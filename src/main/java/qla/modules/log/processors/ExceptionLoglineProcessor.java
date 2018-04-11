package qla.modules.log.processors;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import qla.modules.log.LogFile;
import qla.modules.log.Logline;
import qla.modules.loganalyser.models.LogExeptionModel;
import qla.modules.loganalyser.models.LogModel;
import qla.modules.stringutils.StringUtils;

public class ExceptionLoglineProcessor extends AbstractLoglineProcessor{
	protected Pattern exceptionNamePattern;

	@Override
	public LogModel proccess(Logline logline, LogFile logFile) {
		LogExeptionModel exeptionModel = new LogExeptionModel();
		exeptionModel.setLine(logline.getNumber());
		exeptionModel.setName(getExceptionName(logline, logFile));
		exeptionModel.setSource(logline.getSource());
		exeptionModel.setThread(logline.getThread());
		return exeptionModel;
	}

	private String getExceptionName(Logline logline, LogFile logFile) {
		if(exceptionNamePattern == null) {
			return "";
		}
		return StringUtils.getStringBetveenChars(exceptionNamePattern, logline.getSource());
	}

	public Pattern getExceptionNamePattern() {
		return exceptionNamePattern;
	}

	public void setExceptionNamePattern(String exceptionNamePattern) {
		this.exceptionNamePattern = Pattern.compile(exceptionNamePattern, Pattern.DOTALL);
	}

}
