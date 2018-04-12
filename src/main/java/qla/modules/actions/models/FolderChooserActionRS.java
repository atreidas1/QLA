package qla.modules.actions.models;

import java.util.List;

public class FolderChooserActionRS extends AbstractActionCommand{
	private List<String> listOflogfiles;
	private String fileContent;

	public List<String> getListOflogfiles() {
		return listOflogfiles;
	}

	public void setListOflogfiles(List<String> filenames) {
		this.listOflogfiles = filenames;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
}
