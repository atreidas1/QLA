package qla.modules.log.processors;

import java.io.IOException;
import java.util.regex.Pattern;

import qla.modules.log.LogFile;
import qla.modules.log.Logline;
import qla.modules.stringutils.StringUtils;

public class MultiLoglineProcessor extends SignalLoglineProcessor{
	protected Pattern endPattern;
	
	@Override
	protected String getSignalSource(Logline logline, LogFile logFile) {
		StringBuilder signalSource = new StringBuilder();
		Logline nextLogline = logline;
		try {
			boolean isEnd;
			do{
				isEnd = isEnd(nextLogline);
				String partOfSignal = getPartOfSignal(nextLogline);
				signalSource.append(partOfSignal + "\n");
				if(!isEnd){
					nextLogline = logFile.nextLogline();
				} 
			} while(!isEnd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return signalSource.toString();
	}
	
	private String getPartOfSignal(Logline logline) {
		return StringUtils.getStringBetveenChars(extractPattern, logline.getSource());
	}

	public Pattern getEndPattern() {
		return endPattern;
	}
	public void setEndPattern(String endPattern) {
		this.endPattern = Pattern.compile(endPattern, Pattern.DOTALL);
	}

	protected boolean isEnd(Logline logline) {
		if(logline == null) {
			return true;
		}
		return endPattern.matcher(logline.getSource()).matches();
	}
}
