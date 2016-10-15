package qla.modules.actions.models;

public class AnalyseLogActionRQ extends AbstractActionCommand{
	private String jsessionId;
	private String threadName;
	private String choosedLog;
	
	public String getJsessionId() {
		return jsessionId;
	}
	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public String getChoosedLog() {
		return choosedLog;
	}
	public void setChoosedLog(String choosedLog) {
		this.choosedLog = choosedLog;
	}
}
