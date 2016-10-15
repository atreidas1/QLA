package qla.modules.loganalyser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import qla.modules.loganalyser.models.ErrorLine;
import qla.modules.loganalyser.models.LogExeptionModel;
import qla.modules.loganalyser.models.SignalModel;

public class LogAnalisationInfo implements Serializable{
	private static final long serialVersionUID = 5513690698874951557L;
	private String logFile;
	private List<SignalModel> signalModels = new ArrayList<>();
	private List<LogExeptionModel> exeptionModels =  new ArrayList<>();
	private List<ErrorLine> errorLines =  new ArrayList<>();
	
	public List<SignalModel> getSignalModels() {
		return signalModels;
	}

	public List<LogExeptionModel> getExeptionModels() {
		return exeptionModels;
	}

	public List<ErrorLine> getErrorLines() {
		return errorLines;
	}

	public SignalModel getSignalModel(int id) {
		return signalModels.get(id);
	}
	
	public LogExeptionModel getLogExeptionModel(int id) {
		return exeptionModels.get(id);
	}
	
	public ErrorLine getLogErrorLine(int id) {
		return errorLines.get(id);
	}
	
	public LogAnalisationInfo setSignalModel(SignalModel model) {
		signalModels.add(model);
		return this;
	}
	
	public LogAnalisationInfo setLogExeptionModel(LogExeptionModel model) {
		model.setId(exeptionModels.size());
		exeptionModels.add(model);
		return this;
	}
	
	public LogAnalisationInfo setErrorLine(ErrorLine model) {
		errorLines.add(model);
		return this;
	}
	
	public int getSignalModelsSize() {
		return signalModels.size();
	}
	
	public int getExeptionModelsSize() {
		return exeptionModels.size();
	}
	
	public int getErrorLinesSize() {
		return errorLines.size();
	}

	public String getLogFile() {
		return logFile;
	}

	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}
	
}
