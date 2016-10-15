package qla.modules.ssh;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHCommandsExecuter {
	
	public SSHCommandResult executeCommand(SSHCommand sshCommand) throws JSchException, IOException{
			StringBuilder stringBuilder = new StringBuilder();
			SSHCommandResult commandResult = new SSHCommandResult();
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			Session session = jsch.getSession(sshCommand.getUser(), sshCommand.getHost(), 22);
			session.setPassword(sshCommand.getPassword());
			session.setConfig(config);
			session.connect();
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(sshCommand.getCommandSource());
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					stringBuilder.append(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					commandResult.setStatus(channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			commandResult.setCommandOutput(stringBuilder.toString());
			channel.disconnect();
			session.disconnect();
			return commandResult;
	}
}
