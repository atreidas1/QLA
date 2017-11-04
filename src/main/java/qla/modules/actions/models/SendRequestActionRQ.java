package qla.modules.actions.models;

public class SendRequestActionRQ extends AbstractActionCommand{
	private String url;
	private String servant;
	private String system;
	private String requestSource;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServant() {
		return servant;
	}
	public void setServant(String servant) {
		this.servant = servant;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getRequestSource() {
		return requestSource;
	}
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}

	
}
