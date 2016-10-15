package qla.modules.actions.models;

import java.util.List;

public class DeleteFilesActionRQ extends AbstractActionCommand{
	private List<String> logfiles;
	private List<String> parsedFiles;
	
	public List<String> getLogfiles() {
		return logfiles;
	}
	public void setLogfiles(List<String> lofiles) {
		this.logfiles = lofiles;
	}
	public List<String> getParsedFiles() {
		return parsedFiles;
	}
	public void setParsedFiles(List<String> parsedFiles) {
		this.parsedFiles = parsedFiles;
	}
}
