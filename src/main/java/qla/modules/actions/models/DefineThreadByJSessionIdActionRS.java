package qla.modules.actions.models;

public class DefineThreadByJSessionIdActionRS extends AbstractActionCommand{
	private String threadName;

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
}
