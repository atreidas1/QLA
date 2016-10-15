package qla.modules.actions.models;

import java.util.List;
import java.util.Map;

public class GetConfigurationActionRS extends AbstractActionCommand{
	private List<Map<String, String>> config;

	public List<Map<String, String>> getConfig() {
		return config;
	}

	public void setConfig(List<Map<String, String>> config) {
		this.config = config;
	}
}
