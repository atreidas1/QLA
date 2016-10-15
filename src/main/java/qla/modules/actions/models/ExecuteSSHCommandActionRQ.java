package qla.modules.actions.models;

public class ExecuteSSHCommandActionRQ extends AbstractActionCommand{
	private String commandKey;
	private String ip;
	
	public String getCommandKey() {
		return commandKey;
	}
	public void setCommandKey(String commandKey) {
		this.commandKey = commandKey;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
