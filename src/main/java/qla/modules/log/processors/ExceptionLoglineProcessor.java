package qla.modules.log.processors;

import java.util.regex.Pattern;

import qla.modules.log.LogFile;
import qla.modules.log.Logline;
import qla.modules.loganalyser.LogAnalisationInfo;
import qla.modules.loganalyser.models.LogExeptionModel;
import qla.modules.stringutils.StringUtils;

public class ExceptionLoglineProcessor extends AbstractLoglineProcessor{
	protected Pattern exceptionNamePattern;

	@Override
	public void proccess(Logline logline, LogAnalisationInfo info, LogFile logFile) {
		LogExeptionModel exeptionModel = new LogExeptionModel();
		exeptionModel.setId(info.getExeptionModelsSize());
		exeptionModel.setLine(logline.getNumber());
		exeptionModel.setName(getExceptionName(logline, logFile));
		exeptionModel.setSource(logline.getSource());
		exeptionModel.setThread(logline.getThread());
		info.setLogExeptionModel(exeptionModel);
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
