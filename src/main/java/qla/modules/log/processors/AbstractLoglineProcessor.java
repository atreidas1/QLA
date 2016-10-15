package qla.modules.log.processors;

import java.util.regex.Pattern;

import qla.modules.log.Logline;
import qla.modules.loganalyser.models.LogModel;


public abstract class AbstractLoglineProcessor implements ILoglineProcessor {
	protected Pattern conditionPattern;
	protected Pattern extractPattern;
	
	
	@Override
	public boolean isNeedProcessing(Logline logline) {
		return conditionPattern.matcher(logline.getSource()).matches();
	}

	public Pattern getExtractPattern() {
		return extractPattern;
	}

	public void setExtractPattern(String extractPattern) {
		this.extractPattern = Pattern.compile(extractPattern, Pattern.DOTALL);
	}

	public Pattern getConditionPattern() {
		return conditionPattern;
	}

	public void setConditionPattern(String conditionPattern) {
		this.conditionPattern = Pattern.compile(conditionPattern, Pattern.DOTALL);
	}
}
