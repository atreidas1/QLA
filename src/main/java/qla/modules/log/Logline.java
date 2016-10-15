package qla.modules.log;

import qla.modules.stringutils.StringUtils;

public class Logline {
	private String source;
	private int number;
	private LogConfiguration logConfig;
    
    
	public Logline(String source, LogConfiguration logConfiGuration) {
		this.source = source;
		this.setLogConfiguration(logConfiGuration);
	}

	public String getDateString() {
		return StringUtils
				.getPartOfStringByPattern(logConfig.getDatePattern(), source);
		
	}
	
	public String getTimeString() {
		return StringUtils
				.getPartOfStringByPattern(logConfig.getTimePattern(), source);
		
	}
	
	public String getLoglevel() {
		String logLevel = StringUtils
				.getPartOfStringByPattern(logConfig.getLoglevelPattern(), source);
		return StringUtils.removeTailAndLeadParts(logLevel, "[", "]");
	}
	
	public String getThread() {
		String thread = StringUtils
				.getPartOfStringByPattern(logConfig.getThreadPattern(), source);
		return StringUtils.removeTailAndLeadParts(thread, "[", "]");
	}
	
	public String getFullClassName() {
		String fullClassName = StringUtils.getPartOfStringByPattern(logConfig.getFullClassnamePattern(), source);
		return StringUtils.removeTailAndLeadParts(fullClassName, "[", "]");
	}
	
	public String getclassName() {
		String className = getFullClassName();
		return StringUtils.getPartOfSplitedString(className, -1, "[.]");
	}
	
	public String getJsessionId() {
		String JsessionId = StringUtils.getPartOfStringByPattern(logConfig.getJsessionIdPattern(), source);
		return StringUtils.removeTailAndLeadParts(JsessionId, "[", "]");
	}
	
	public String getLoglineInfoString() {
		return StringUtils.getPartOfStringByPattern(logConfig.getConversioPattern(), source);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Logline other = (Logline) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

	public void addLineToMessage(String nextLine) {
		source += "\n" + nextLine;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public LogConfiguration getLogConfiguration() {
		return logConfig;
	}

	public void setLogConfiguration(LogConfiguration logConfiguration) {
		this.logConfig = logConfiguration;
	}

	@Override
	public String toString() {
		return source;
	}
	
	
}
