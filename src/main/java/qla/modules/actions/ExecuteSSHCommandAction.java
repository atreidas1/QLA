package qla.modules.actions;

import java.io.IOException;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.ExecuteSSHCommandActionRQ;
import qla.modules.actions.models.ExecuteSSHCommandActionRS;
import qla.modules.servers.ServersUtils;
import qla.modules.servers.models.ServerModel;
import qla.modules.ssh.SSHCommand;
import qla.modules.ssh.SSHCommandResult;
import qla.modules.ssh.SSHCommandsExecuter;

public class ExecuteSSHCommandAction extends AbstractAction<ExecuteSSHCommandActionRQ>{

	public ExecuteSSHCommandAction(Class rqClass) {
		super(rqClass);
	}

	@Override
	protected AbstractActionCommand proccess(ExecuteSSHCommandActionRQ rq) throws ActionException {
		String ip = rq.getIp();
		String commandKey = rq.getCommandKey();
		ActionHelper.checkStringForNullOrEmpty(commandKey, "Command key null or empty!");
		ActionHelper.checkStringForNullOrEmpty(ip, "IP null or empty.");
		ServerModel server = getServer(ip);
		ActionHelper.checkForNull(server, "Server by ip=" + ip + " not found!");
		String command = defineCommandByKey(commandKey, server);
		ActionHelper.checkStringForNullOrEmpty(command, 
				String.format("Command by key=%s not defined for server %s", commandKey, server.getIp()));
		SSHCommandsExecuter executer = new SSHCommandsExecuter();
		SSHCommandResult result;
		try {
			result = executer.executeCommand(new SSHCommand(server.getIp(), server.getLogin(), server.getPassword(), command));
		} catch (Exception e) {
			throw new ActionException(e.getMessage());
		} 
		ExecuteSSHCommandActionRS rs = new ExecuteSSHCommandActionRS();
		if(result.getStatus() == 0){
			String commandOut = result.getCommandOutput();
			if(commandOut.isEmpty()) {
				rs.setSuccess("Command executed succesfully!");
			} else {
				rs.setSuccess(formatAsConsoleOut(result.getCommandOutput()));
			}
		} else {
			String error = result.getCommandOutput();
			if(!error.isEmpty()) {
				rs.setError(String.format("Error when executing command %s, returned status=%d, command output: %s",
						command, result.getStatus(), result.getCommandOutput()));
			} else {
				rs.setError(String.format("Error when executing command %s, returned status=%d",
						command, result.getStatus()));
			}
		}
		return rs;
	}

	private String formatAsConsoleOut(String string) {
		return "<pre>" + string + "</pre>";
	}
	
	private String defineCommandByKey(String commandKey, ServerModel serverModel) throws ActionException{
		String command = "";
		switch (commandKey) {
		case "restartTomcatCmd":
			command = serverModel.getRestartTomcatCmd();
			break;
		case "restartJbossCmd":
			command = serverModel.getRestartJbossCmd();
			break;
		case "accessToJbossLogsCmd":
			command = serverModel.getAccessToJbossLogsCmd();
			break;
		default:
			throw new ActionException(String.format("Command by key=%s not found for server %s", commandKey, serverModel.getIp()));
		}
		return command;
	}
	
	private ServerModel getServer(String ip) throws ActionException{
		ServerModel server;
		try {
			server = ServersUtils.getServer(ip);
		} catch (IOException e) {
			throw new ActionException(e.getMessage());
		}
		return server;
	}
}
