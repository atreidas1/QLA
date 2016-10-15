package qla.modules.servers.models;

public class ServerModel implements Comparable<ServerModel> {
	private String ip;
	private String branch;
	private String url;
	private String login;
	private String password;
	private String repoLink;
	private String restartTomcatCmd;
	private String restartJbossCmd;
	private String pathToJbossLogs;
	private String pathToTomcatLogs;
	private String accessToJbossLogsCmd;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepoLink() {
		return repoLink;
	}
	public void setRepoLink(String repoLink) {
		this.repoLink = repoLink;
	}
	public String getRestartTomcatCmd() {
		return restartTomcatCmd;
	}
	public void setRestartTomcatCmd(String restartTomcatCmd) {
		this.restartTomcatCmd = restartTomcatCmd;
	}
	public String getRestartJbossCmd() {
		return restartJbossCmd;
	}
	public void setRestartJbossCmd(String restartJbossCmd) {
		this.restartJbossCmd = restartJbossCmd;
	}
	public String getPathToJbossLogs() {
		return pathToJbossLogs;
	}
	public void setPathToJbossLogs(String pathToJbossLogs) {
		this.pathToJbossLogs = pathToJbossLogs;
	}
	public String getPathToTomcatLogs() {
		return pathToTomcatLogs;
	}
	public void setPathToTomcatLogs(String pathToTomcatLogs) {
		this.pathToTomcatLogs = pathToTomcatLogs;
	}
	public String getAccessToJbossLogsCmd() {
		return accessToJbossLogsCmd;
	}
	public void setAccessToJbossLogsCmd(String accessToJbossLogsCmd) {
		this.accessToJbossLogsCmd = accessToJbossLogsCmd;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public int compareTo(ServerModel o) {
		return 0;
	}
}
