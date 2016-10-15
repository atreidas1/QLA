package qla.modules.ssh;

public class SSHCommandResult {
	private int status;
	private String commandOutput;
	
	public SSHCommandResult(int status, String commandOutput) {
		super();
		this.status = status;
		this.commandOutput = commandOutput;
	}
	
	public SSHCommandResult() {
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCommandOutput() {
		return commandOutput;
	}
	public void setCommandOutput(String commandOutput) {
		this.commandOutput = commandOutput;
	}
}
