package qla.modules.ssh;

public class SSHCommand {
	private String host;
	private String user;
	private String password;
	private String commandSource;
	
	public SSHCommand(String host, String user, String password, String commandSource) {
		super();
		this.host = host;
		this.user = user;
		this.password = password;
		this.commandSource = commandSource;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCommandSource() {
		return commandSource;
	}
	public void setCommandSource(String commandSource) {
		this.commandSource = commandSource;
	}
}
