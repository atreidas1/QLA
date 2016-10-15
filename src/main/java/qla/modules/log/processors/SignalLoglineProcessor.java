package qla.modules.log.processors;

import java.util.regex.Pattern;

import qla.modules.log.LogFile;
import qla.modules.log.Logline;
import qla.modules.loganalyser.LogAnalisationInfo;
import qla.modules.loganalyser.models.SignalModel;
import qla.modules.stringutils.StringUtils;


public class SignalLoglineProcessor extends AbstractLoglineProcessor {
	protected Pattern requestPattern;
	protected Pattern responsePattern;
	protected Pattern warningsPattern;
	protected Pattern errorsPattern;
	protected Pattern signalNamePattern;
	protected Pattern actorPattern;
	protected String system;
	//Can be XML,JSON,TEXT
	protected String contentType;
	protected String incomingSignalName;
	protected String outgoingSignalName;
	protected String defaultSignalName;

	@Override
	public void proccess(Logline logline, LogAnalisationInfo info, LogFile logFile) {
		SignalModel signalModel = new SignalModel();
		signalModel.setId(info.getSignalModelsSize());
		signalModel.setLine(logline.getNumber());
		signalModel.setSystem(system);
		signalModel.setErrors(getErrors(logline, logFile));
		signalModel.setWarnings(getWarnings(logline, logFile));
		signalModel.setRqRsName(getSignalName(logline, logFile));
		signalModel.setContentType(contentType);
		signalModel.setType(getSignalType(logline, logFile));
		signalModel.setServantName(getActorName(logline, logFile));
		signalModel.setThread(logline.getThread());
		signalModel.setSource(getSignalSource(logline, logFile));
		info.setSignalModel(signalModel);
	}
	
	protected String getSignalType(Logline logline, LogFile logFile) {
		if(requestPattern!=null && responsePattern != null) {
			if(requestPattern.matcher(logline.getSource()).matches()){
				return incomingSignalName;
			}
			if(responsePattern.matcher(logline.getSource()).matches()){
				return outgoingSignalName;
			}
		}
		return "";
	}
	
	protected String getActorName(Logline logline, LogFile logFile) {
		if(actorPattern != null) {
			return StringUtils.getStringBetveenChars(actorPattern, logline.getSource());
		}
		return "";
	}

	protected String getWarnings(Logline logline, LogFile logFile) {
		if(warningsPattern != null) {
			return StringUtils.getPartOfStringByPattern(warningsPattern, logline.getSource());
		}
		return "";
	}

	protected String getSignalName(Logline logline, LogFile logFile) {
		String signalName = null;
		if(signalNamePattern != null) {
			signalName = StringUtils.getStringBetveenChars(signalNamePattern, logline.getSource());
		}
		signalName = StringUtils.isEmpty(signalName) ? defaultSignalName : signalName;
		return signalName == null ? "" : signalName ;
	}

	protected String getErrors(Logline logline, LogFile logFile) {
		if(errorsPattern != null) {
			return StringUtils.getPartOfStringByPattern(errorsPattern, logline.getSource());
		}
		return "";
	}

	protected String getSignalSource(Logline logline, LogFile logFile) {
		return StringUtils.getPartOfStringByPattern(extractPattern, logline.getSource());
	}
	
	public Pattern getRequestPattern() {
		return requestPattern;
	}
	public void setRequestPattern(String requestPattern) {
		this.requestPattern = Pattern.compile(requestPattern, Pattern.DOTALL);
	}
	public Pattern getResponsePattern() {
		return responsePattern;
	}
	public void setResponsePattern(String responsePattern) {
		this.responsePattern = Pattern.compile(responsePattern, Pattern.DOTALL);
	}
	public Pattern getWarningsPattern() {
		return warningsPattern;
	}
	public void setWarningsPattern(String warningsPattern) {
		this.warningsPattern = Pattern.compile(warningsPattern, Pattern.DOTALL);
	}
	public Pattern getErrorsPattern() {
		return errorsPattern;
	}
	public void setErrorsPattern(String errorsPattern) {
		this.errorsPattern = Pattern.compile(errorsPattern, Pattern.DOTALL);
	}
	public Pattern getSignalNamePattern() {
		return signalNamePattern;
	}
	public void setSignalNamePattern(String signalNamePattern) {
		this.signalNamePattern = Pattern.compile(signalNamePattern, Pattern.DOTALL);
	}
	
	public Pattern getActorPattern() {
		return actorPattern;
	}

	public void setActorPattern(String actorPattern) {
		this.actorPattern = Pattern.compile(actorPattern, Pattern.DOTALL);
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getIncomingSignalName() {
		return incomingSignalName;
	}

	public void setIncomingSignalName(String incomingSignalName) {
		this.incomingSignalName = incomingSignalName;
	}

	public String getOutgoingSignalName() {
		return outgoingSignalName;
	}

	public void setOutgoingSignalName(String outgoingSignalName) {
		this.outgoingSignalName = outgoingSignalName;
	}
	
	public String getDefaultSignalName() {
		return defaultSignalName;
	}

	public void setDefaultSignalName(String defaultSignalName) {
		this.defaultSignalName = defaultSignalName;
	}

}
