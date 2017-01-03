package qla.modules.actions.models;

public class FolderChooserActionRQ extends AbstractActionCommand{
	private String propKey;
	private String folder;
	private String subAction;
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
	
}
