package qla.modules.actions.models;

public class SignalInfoActionRQ extends AbstractActionCommand{
	private String parsedFile;
	private String signalId;
	public String getParsedFile() {
		return parsedFile;
	}
	public void setParsedFile(String parsedFile) {
		this.parsedFile = parsedFile;
	}
	public String getSignalId() {
		return signalId;
	}
	public void setSignalId(String signalId) {
		this.signalId = signalId;
	}
	
	
}
