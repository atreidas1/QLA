package qla.modules.actions.models;

public class SendRequestActionRS extends AbstractActionCommand{
	private String response;
	private String responseStatus;
	
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	

}
