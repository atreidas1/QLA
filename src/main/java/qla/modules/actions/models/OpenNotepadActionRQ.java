package qla.modules.actions.models;

public class OpenNotepadActionRQ extends AbstractActionCommand{
	private String parsedfileName;
	private String logFilename;
	private String lineNumber;
	private String pathToFile;
	
	public String getParsedfileName() {
		return parsedfileName;
	}
	public void setParsedfileName(String parsedfileName) {
		this.parsedfileName = parsedfileName;
	}
	public String getLogFilename() {
		return logFilename;
	}
	public void setLogFilename(String logFilename) {
		this.logFilename = logFilename;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getPathToFile() {
		return pathToFile;
	}
	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}
	
}
