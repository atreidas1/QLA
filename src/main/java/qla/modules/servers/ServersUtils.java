package qla.modules.servers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import qla.modules.serialiser.JsonSerialiser;
import qla.modules.servers.models.ServerModel;

public class ServersUtils {
	
	@SuppressWarnings("unchecked")
	public static List<ServerModel> getServers() throws IOException {
		List<ServerModel> servers = new ArrayList<>();
		StringBuffer json = new StringBuffer();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(System.getProperty("config.folder")+"\\servers.json"), "UTF8"));
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			json.append(line);
		}
		Type listType = new TypeToken<ArrayList<ServerModel>>() {
		}.getType();
		servers.addAll((List<ServerModel>) JsonSerialiser.deserialise(json.toString(), listType));
		return servers;
	}
	
	public static ServerModel getServer(String ip) throws IOException {
		List<ServerModel> servers = getServers();
		for (ServerModel serverModel : servers) {
			if(ip.equals(serverModel.getIp())){
				return serverModel;
			}
		}
		return null;
	}
}
