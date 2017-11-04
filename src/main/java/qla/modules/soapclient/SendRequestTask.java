package qla.modules.soapclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import qla.modules.serialiser.JsonSerialiser;

public class SendRequestTask implements Runnable {
	private String Url;
	private String source;
	private String servant;
	private WebSocketSession session;
	private boolean isCanceled = false;
	private SystemRequestParameters parameters;

	
	public SendRequestTask(String url, String source, String servant, WebSocketSession session,
			SystemRequestParameters parameters) {
		super();
		Url = url;
		this.source = source;
		this.servant = servant;
		this.session = session;
		this.parameters = parameters;
	}


	@Override
	public void run() {
		Map<String, Object> rs = new HashMap<>();
		try {
			String soapRs = SOAPClient.sendRequest(Url, source, servant, parameters);
			soapRs = qla.modules.prettyprinter.XmlPrettyPrinter.prettyFormat(soapRs);
			rs.put("response", soapRs);
		} catch (Exception e) {
			rs.put("response", e.getMessage());
		}
		rs.put("status", "success");
		rs.put("action", "SOAPResponseRecieved");
		String message = JsonSerialiser.serialise(rs);
		try {
			if (!isCanceled) {
				session.sendMessage(new TextMessage(message));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getUrl() {
		return Url;
	}


	public void setUrl(String url) {
		Url = url;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getServant() {
		return servant;
	}


	public void setServant(String servant) {
		this.servant = servant;
	}

	public WebSocketSession getSession() {
		return session;
	}


	public void setSession(WebSocketSession session) {
		this.session = session;
	}


	public boolean isCanceled() {
		return isCanceled;
	}


	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}
}
