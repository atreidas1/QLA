package qla.modules.actions;

import java.io.IOException;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.RunPuttyActionRQ;
import qla.modules.actions.models.RunPuttyActionRS;
import qla.modules.putty.PuttyUtils;
import qla.modules.servers.ServersUtils;
import qla.modules.servers.models.ServerModel;

public class RunPuttyAction extends AbstractAction<RunPuttyActionRQ>{

	public RunPuttyAction(Class rqClass) {
		super(rqClass);
	}

	@Override
	protected AbstractActionCommand proccess(RunPuttyActionRQ rq) throws ActionException {
		String ip = rq.getIp();
		RunPuttyActionRS rs = new RunPuttyActionRS();
		ActionHelper.checkStringForNullOrEmpty(ip, "Ip addres null or empty!");
		try {
			ServerModel server = ServersUtils.getServer(ip);
			String login = server.getLogin();
			String password = server.getPassword();
			if(login != null && password !=null) {
				PuttyUtils.runPuttyOnServer(ip, login, password);
			} else {
				PuttyUtils.runPutty("");
			}
		} catch (IOException e) {
			throw new ActionException(e.getMessage());
		}
		rs.setSuccess("");
		return rs;
	}

}
