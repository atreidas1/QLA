package qla.modules.log;

import java.util.regex.Pattern;

public class LogConfiguration {
	private String encoding;
	private Pattern datePattern;
    private Pattern timePattern;
    private Pattern loglevelPattern;
    private Pattern fullClassnamePattern;
    private Pattern jsessionIdPattern;
    private Pattern threadPattern;
    private Pattern conversioPattern;
	private Pattern newLogLinePattern;;
    
    public Pattern getDatePattern() {
		return datePattern;
	}

	public LogConfiguration setDatePattern(String datePattern) {
		this.datePattern = Pattern.compile(datePattern);
		return this;
	}

	public Pattern getTimePattern() {
		return timePattern;
	}

	public LogConfiguration setTimePattern(String timePattern) {
		this.timePattern = Pattern.compile(timePattern);
		return this;
	}

	public Pattern getLoglevelPattern() {
		return loglevelPattern;
	}

	public LogConfiguration setLoglevelPattern(String loglevelPattern) {
		this.loglevelPattern = Pattern.compile(loglevelPattern);
		return this;
	}

	public Pattern getFullClassnamePattern() {
		return fullClassnamePattern;
	}

	public LogConfiguration setFullClassnamePattern(String fullClassnamePattern) {
		this.fullClassnamePattern = Pattern.compile(fullClassnamePattern);
		return this;
	}

	public Pattern getJsessionIdPattern() {
		return jsessionIdPattern;
	}

	public LogConfiguration setJsessionIdPattern(String jsessionIdPattern) {
		this.jsessionIdPattern = Pattern.compile(jsessionIdPattern);
		return this;
	}

	public Pattern getThreadPattern() {
		return threadPattern;
	}

	public LogConfiguration setThreadPattern(String threadRegexp) {
		threadPattern = Pattern.compile(threadRegexp);
		return this;
	}

	public Pattern getConversioPattern() {
		return conversioPattern;
	}

	public LogConfiguration setConversationPattern(String conversioPattern) {
		this.conversioPattern = Pattern.compile(conversioPattern);
		return this;
	}

	public String getEncoding() {
		return encoding;
	}

	public LogConfiguration setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	public Pattern getNewLoglinePatern() {
		return newLogLinePattern;
	}
	
	public LogConfiguration setNewLoglinePatern(String newLoglinePatern) {
		this.newLogLinePattern = Pattern.compile(newLoglinePatern);
		return this;
	}
	
}
