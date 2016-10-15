package qla.modules.actions.models;

import java.util.List;

import qla.modules.servers.models.ServerModel;

public class GetServersActionRS extends AbstractActionCommand{
	private List<ServerModel> servers;

	public List<ServerModel> getServers() {
		return servers;
	}

	public void setServers(List<ServerModel> servers) {
		this.servers = servers;
	}
	
}
