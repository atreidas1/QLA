package qla.modules.actions.models;

public class LoadExceptionsActionRQ extends AbstractActionCommand{
	private String parsedfile;
	private String exceptionId;
	private String thread;
	
	public String getParsedfile() {
		return parsedfile;
	}

	public void setParsedfile(String parsedfile) {
		this.parsedfile = parsedfile;
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	
}
