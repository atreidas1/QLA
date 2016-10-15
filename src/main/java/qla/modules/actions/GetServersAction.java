package qla.modules.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.GetServersActionRQ;
import qla.modules.actions.models.GetServersActionRS;
import qla.modules.servers.ServersUtils;
import qla.modules.servers.models.ServerModel;

public class GetServersAction extends AbstractAction<GetServersActionRQ> {

	public GetServersAction(Class rqClass) {
		super(rqClass);
	}

	@Override
	protected AbstractActionCommand proccess(GetServersActionRQ rq) throws ActionException {
		String ip = rq.getIp();
		GetServersActionRS rs = new GetServersActionRS();
		List<ServerModel> servers = new ArrayList<>();
		try {
			if (ip == null || ip.isEmpty()) {
				servers.addAll(ServersUtils.getServers());
				if (servers.isEmpty()) {
					throw new ActionException("No servers found!");
				}
			} else {
				ServerModel server = ServersUtils.getServer(ip);
				ActionHelper.checkForNull(server, String.format("Server with ip = %s does not exists.", ip));
				servers.add(server);
			}
		} catch (IOException e) {
			throw new ActionException(e.getMessage());
		}
		rs.setServers(servers);
		rs.setSuccess("");
		return rs;
	}

}
