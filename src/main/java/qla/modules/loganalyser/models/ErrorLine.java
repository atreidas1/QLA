package qla.modules.loganalyser.models;

import java.io.Serializable;

public class ErrorLine implements Serializable {
	private static final long serialVersionUID = -7309032098895387730L;
	private int id;
	private int line;
	private String source;
	
	public ErrorLine(int id, int line, String source) {
		super();
		this.id = id;
		this.line = line;
		this.source = source;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getLine() {
		return line;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
}
