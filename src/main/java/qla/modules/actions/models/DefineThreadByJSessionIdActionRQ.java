package qla.modules.actions.models;

public class DefineThreadByJSessionIdActionRQ extends AbstractActionCommand{
	private String jsessionId;
	private String logFile;
	
	public String getJsessionId() {
		return jsessionId;
	}
	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}
	public String getLogFile() {
		return logFile;
	}
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}
	
}
