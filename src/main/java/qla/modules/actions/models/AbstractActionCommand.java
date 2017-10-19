package qla.modules.actions.models;

import org.springframework.web.socket.WebSocketSession;

public abstract class AbstractActionCommand {
	private transient WebSocketSession session;
	private String successAction;
	private String action;
	private String message;
	private String subaction;
	
	public String getSuccessAction() {
		return successAction;
	}
	public void setSuccessAction(String successAction) {
		this.successAction = successAction;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setSuccess(String message) {
		this.message = message;
		this.action = "success";
	}
	
	public void setError(String message) {
		this.message = message;
		this.action = "error";
	}
	
	public WebSocketSession getSession() {
		return session;
	}
	
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	public String getSubaction() {
		return subaction;
	}
	public void setSubaction(String subaction) {
		this.subaction = subaction;
	}
}
