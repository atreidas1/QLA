package qla.modules.actions.models;

import java.util.List;

import qla.modules.loganalyser.models.LogExeptionModel;
import qla.modules.loganalyser.models.SignalModel;

public class GetLogInfoActionRS extends AbstractActionCommand{
	private String infoFile;
	private String logfile;
	private List<LogExeptionModel> exceptions;
	private List<SignalModel> signals;
	private int numberOfExceptions;
	private int numberErrorSignals;
	private int numberWarningSignals;
	
	public List<LogExeptionModel> getExceptions() {
		return exceptions;
	}
	public void setExceptions(List<LogExeptionModel> exceptions) {
		this.exceptions = exceptions;
	}
	public List<SignalModel> getSignals() {
		return signals;
	}
	public void setSignals(List<SignalModel> signals) {
		this.signals = signals;
	}
	public int getNumberOfExceptions() {
		return numberOfExceptions;
	}
	public void setNumberOfExceptions(int numberOfExceptions) {
		this.numberOfExceptions = numberOfExceptions;
	}
	public int getNumberErrorSignals() {
		return numberErrorSignals;
	}
	public void setNumberErrorSignals(int numberErrorSignals) {
		this.numberErrorSignals = numberErrorSignals;
	}
	public int getNumberWarningSignals() {
		return numberWarningSignals;
	}
	public void setNumberWarningSignals(int numberWarningSignals) {
		this.numberWarningSignals = numberWarningSignals;
	}
	public String getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(String infoFile) {
		this.infoFile = infoFile;
	}
	public String getLogfile() {
		return logfile;
	}
	public void setLogfile(String logfile) {
		this.logfile = logfile;
	}
}
