package qla.modules.log.processors;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import qla.modules.log.ILogConfigurator;
import qla.modules.log.LogConfiguration;
import qla.modules.log.LogFile;
import qla.modules.log.Logline;
import qla.modules.log.PropertiesLogConfigurator;
import qla.modules.loganalyser.LogAnalisationInfo;

public class TDPLogProcessor implements ILogProcessor {
	private LogConfiguration logConfiguration;
	private List<ILoglineProcessor> LineProcessors = new ArrayList<>();
	private IProcessCallback callback;
	
	@Override
	public LogAnalisationInfo process(String pathToLogfile) throws IOException {
		LogAnalisationInfo analisationInfo = new LogAnalisationInfo();
		LogFile logFile = new LogFile(pathToLogfile, logConfiguration);
		Logline logline;
		startProcessing(logFile);
		while((logline = logFile.nextLogline())!= null) {
			for (Iterator iterator = LineProcessors.iterator(); iterator.hasNext();) {
				ILoglineProcessor iLoglineProcessor = (ILoglineProcessor) iterator.next();
				if(iLoglineProcessor.isNeedProcessing(logline)){
					iLoglineProcessor.proccess(logline, analisationInfo, logFile);
					break;
				}
			}
			progressOfProcessing(logFile);
		}
		endOfProcessing(logFile);
		logFile.close();
		return analisationInfo;
	}
	
	private void endOfProcessing(LogFile logFile) {
		Map<String, Object> data = new HashMap<>();
		data.put("logFile", logFile);
		if(callback != null) {
			callback.endOfProcessing(data);
		}
	}

	private void progressOfProcessing(LogFile logFile) {
		Map<String, Object> data = new HashMap<>();
		data.put("logFile", logFile);
		if(callback != null) {
			callback.progressOfProcessing(data);
		}
	}

	private void startProcessing(LogFile logFile) {
		Map<String, Object> data = new HashMap<>();
		data.put("logFile", logFile);
		if(callback != null) {
			callback.startProcessing(data);
		}
	}

	public void setProcessor(ILoglineProcessor processor) {
		LineProcessors.add(processor);
	}

	public LogConfiguration getLogConfiguration() {
		return logConfiguration;
	}

	public void setLogConfiguration(LogConfiguration logConfiguration) {
		this.logConfiguration = logConfiguration;
	}

	public List<ILoglineProcessor> getLineProcessors() {
		return LineProcessors;
	}

	public void setLineProcessors(List<ILoglineProcessor> lineProcessors) {
		LineProcessors = lineProcessors;
	}

	public IProcessCallback getCallback() {
		return callback;
	}
	
	@Override
	public void setCallback(IProcessCallback callback) {
		this.callback = callback;
	}

}
