package qla.modules.loganalyser.models;

import java.io.Serializable;

public class LogModel implements Serializable{
	private static final long serialVersionUID = -8027377318857535630L;
	protected int id;
	protected int lineNumber;
	protected String source;
	protected String thread;
	
	public LogModel() {
		super();
	}
	
	public LogModel(int id, int line, String source) {
		super();
		this.id = id;
		this.lineNumber = line;
		this.source = source;
	}
	
	public int getId() {
		return id;
	}
	public LogModel setId(int id) {
		this.id = id;
		return this;
	}
	public int getLine() {
		return lineNumber;
	}
	public LogModel setLine(int line) {
		this.lineNumber = line;
		return this;
	}
	public String getSource() {
		return source;
	}
	public LogModel setSource(String source) {
		this.source = source;
		return this;
	}

	public String getThread() {
		return thread;
	}

	public LogModel setThread(String thread) {
		this.thread = thread;
		return this;
	}
	
	
}
