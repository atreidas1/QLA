package qla.modules.actions.models;

public class GetLogInfoActionRQ extends AbstractActionCommand{
	private String searchContent;
	private String infoFile;
	private String thread;
	private String jSessionId;
	private int fromId;
	private int toId;
	
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getjSessionId() {
		return jSessionId;
	}
	public void setjSessionId(String jSessionId) {
		this.jSessionId = jSessionId;
	}
	public int getFromId() {
		return fromId;
	}
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}
	public int getToId() {
		return toId;
	}
	public void setToId(int toId) {
		this.toId = toId;
	}
	public String getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(String infoFile) {
		this.infoFile = infoFile;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	
	
}
