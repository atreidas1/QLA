package qla.modules.actions.models;

public class FolderChooserActionRQ extends AbstractActionCommand{
	private String propKey;
	private String folder;
	private String subAction;
	private String folderKey;
	private String pathToFolder;
	private String file;
	private boolean includeBaseDir;
	
	public String getPropKey() {
		return propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getSubAction() {
		return subAction;
	}

	public void setSubAction(String subAction) {
		this.subAction = subAction;
	}

	public String getPathToFolder() {
		return pathToFolder;
	}

	public void setPathToFolder(String pathToFolder) {
		this.pathToFolder = pathToFolder;
	}

	public String getFolderKey() {
		return folderKey;
	}

	public void setFolderKey(String folderKey) {
		this.folderKey = folderKey;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public boolean isIncludeBaseDir() {
		return includeBaseDir;
	}

	public void setIncludeBaseDir(boolean includeBaseDir) {
		this.includeBaseDir = includeBaseDir;
	}
}
