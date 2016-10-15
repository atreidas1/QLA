package qla.modules.loganalyser.models;

import java.io.Serializable;

public class LogExeptionModel extends LogModel implements Serializable{
	private static final long serialVersionUID = 7585084426377371752L;
	private String name;
	
	public LogExeptionModel(int line, String source, String exceptionName) {
		super();
		this.lineNumber = line;
		this.source = source;
		this.name = exceptionName;
	}
	
	public LogExeptionModel() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
