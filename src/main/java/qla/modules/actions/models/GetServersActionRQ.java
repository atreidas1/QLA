package qla.modules.actions.models;

public class GetServersActionRQ extends AbstractActionCommand{
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
