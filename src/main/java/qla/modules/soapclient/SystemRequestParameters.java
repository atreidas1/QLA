package qla.modules.soapclient;

import java.util.Map;

public class SystemRequestParameters {
	private int readTimeout;
	private int connectTimeout;
	private boolean wrapInEnvelope;
	private Map<String, String> headers;
	private boolean wrapInAction;
	
	public int getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public boolean isWrapInEnvelope() {
		return wrapInEnvelope;
	}
	public void setWrapInEnvelope(boolean wrapInEnvelope) {
		this.wrapInEnvelope = wrapInEnvelope;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public boolean isWrapInAction() {
		return wrapInAction;
	}
	public void setWrapInAction(boolean wrapInAction) {
		this.wrapInAction = wrapInAction;
	}
	
}
