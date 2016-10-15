package qla.modules.ssh;

import java.io.IOException;

import org.junit.Test;

import com.jcraft.jsch.JSchException;

public class SSHCommandsExecuterTest {

	@Test
	public void test() {
		String host = "";
		String user = "";
		String password = "";
		String command1 = "";
		SSHCommand command = new SSHCommand(host, user, password, command1);
		SSHCommandsExecuter executer = new SSHCommandsExecuter();
		try {
			SSHCommandResult commandResult = executer.executeCommand(command);
			System.out.println(commandResult.getCommandOutput());
			System.out.println(commandResult.getStatus());
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
