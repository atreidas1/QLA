package qla.modules.actions.models;

import java.util.List;

import qla.modules.loganalyser.models.SignalModel;

public class AnalyseLogActionRS extends AbstractActionCommand{
	private String parsedfile;
	private String logfile;
	private int numberOfExceptions;
	private List<SignalModel> signals;
	
	public String getParsedfile() {
		return parsedfile;
	}
	public void setParsedfile(String parsedfile) {
		this.parsedfile = parsedfile;
	}
	public List<SignalModel> getSignals() {
		return signals;
	}
	public void setSignals(List<SignalModel> signals) {
		this.signals = signals;
	}
	public String getLogfile() {
		return logfile;
	}
	public void setLogfile(String logfile) {
		this.logfile = logfile;
	}
	public int getNumberOfExceptions() {
		return numberOfExceptions;
	}
	public void setNumberOfExceptions(int numberOfExceptions) {
		this.numberOfExceptions = numberOfExceptions;
	}
	
}
