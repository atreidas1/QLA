package qla.modules.putty;

import java.io.IOException;

import qla.modules.confuguration.AppConfiguration;

public class PuttyUtils {
	
	public static void runPutty(String argsString) throws IOException {
		String pathToPutty = AppConfiguration.getProperty("putty.path");
		Runtime.getRuntime().exec(pathToPutty + argsString);
	}
	
	public static void runPuttyOnServer(String ip) throws IOException {
		String argsString = String.format(" -ssh %s", ip);
		runPutty(argsString);
	}
	
	public static void runPuttyOnServer(String ip, String login, String password) throws IOException {
		String argsString = String.format(" -ssh %s -l %s -pw %s", ip, login, password);
		runPutty(argsString);
	}
}
