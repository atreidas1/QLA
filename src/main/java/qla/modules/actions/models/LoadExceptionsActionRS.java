package qla.modules.actions.models;

import java.util.List;

import qla.modules.loganalyser.models.LogExeptionModel;

public class LoadExceptionsActionRS extends AbstractActionCommand{
	private List<LogExeptionModel> exceptions;
	private LogExeptionModel exception;
	
	public List<LogExeptionModel> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<LogExeptionModel> exceptions) {
		this.exceptions = exceptions;
	}

	public LogExeptionModel getException() {
		return exception;
	}

	public void setException(LogExeptionModel exception) {
		this.exception = exception;
	} 
}
