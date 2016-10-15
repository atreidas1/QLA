package qla.modules.actions.models;

public class DeleteFilesActionRS extends AbstractActionCommand{
	private boolean logfilesDeleted;
	private boolean parsedfilesDeleted;
	
	public boolean isLogfilesDeleted() {
		return logfilesDeleted;
	}
	public void setLogfilesDeleted(boolean logfilesDeleted) {
		this.logfilesDeleted = logfilesDeleted;
	}
	public boolean isParsedfilesDeleted() {
		return parsedfilesDeleted;
	}
	public void setParsedfilesDeleted(boolean parsedfilesDeleted) {
		this.parsedfilesDeleted = parsedfilesDeleted;
	}
	
}
