package qla.modules.log.processors;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import qla.modules.log.LogConfiguration;
import qla.modules.log.LogFile;
import qla.modules.log.Logline;
import qla.modules.loganalyser.LogAnalisationInfo;
import qla.modules.loganalyser.models.LogExeptionModel;
import qla.modules.loganalyser.models.LogModel;
import qla.modules.loganalyser.models.SignalModel;

public class TDPLogProcessor implements ILogProcessor {
	private LogConfiguration logConfiguration;
	private List<ILoglineProcessor> lineProcessors = Collections.synchronizedList(new ArrayList<>());
	private IProcessCallback callback;

	@Override
	public LogAnalisationInfo process(String pathToLogfile) throws IOException {
		long startTimeMillis = System.currentTimeMillis();
		LogFile logFile = new LogFile(pathToLogfile, logConfiguration);
		startProcessing(logFile);
		Map<Integer, LogModel> dataForAnalisationInfo =
				getProcessedDataForAnalysationInfo(logFile, lineProcessors, this::progressOfProcessing);
		LogAnalisationInfo analisationInfo = getAnalisationInfo(dataForAnalisationInfo);
		closeResources(logFile, dataForAnalisationInfo);
		endOfProcessing(logFile);
		printStatistics(startTimeMillis, logFile,analisationInfo);
		return analisationInfo;
	}

	private void closeResources(LogFile logFile, Map<Integer, LogModel> dataForAnalisationInfo)
	{
		logFile.close();
		System.gc();
		dataForAnalisationInfo.clear();
	}

	private static LogAnalisationInfo getAnalisationInfo(Map<Integer, LogModel> dataForAnalisationInfo)
	{
		LogAnalisationInfo analisationInfo = new LogAnalisationInfo();
		TreeMap<Integer, LogModel> treeMap = new TreeMap<>();
		treeMap.putAll(dataForAnalisationInfo);
		int logModelId = 0;
		for (Integer index : treeMap.keySet())
		{
			LogModel logModel = treeMap.get(index);
			logModel.setId(logModelId++);
			if (logModel.getLogModelType()== LogModel.LogModelType.EXCEPTION_LOG_MODEL) {
				analisationInfo.addLogExeptionModel((LogExeptionModel) logModel);
			} else if (logModel.getLogModelType()== LogModel.LogModelType.SIGNAL_LOG_MODEL)
			{
				analisationInfo.addSignalModel((SignalModel) logModel);
			}
		}
		return analisationInfo;
	}

	private static void printStatistics(long startTimeMillis, LogFile logFile, LogAnalisationInfo analisationInfo)
	{
		double elapsedTime = (System.currentTimeMillis()-startTimeMillis)/1000.;
		double fileSizeMbs = logFile.getFileSize() / 1000000.;
		double mbsPerSecond = fileSizeMbs/elapsedTime;
		System.err.println("File size: " + fileSizeMbs +
				" mb | Time elapsed " + elapsedTime +
				" | Performance: " + mbsPerSecond+" mb/s" +
				" | Completed lines: " + analisationInfo.getSignalModels().size());
	}

	private static Map<Integer, LogModel> getProcessedDataForAnalysationInfo(final LogFile logLinesFile,
																			 List<ILoglineProcessor> lineProcessors,
																			 Consumer<LogFile> progressBarChangesConsumer)
	{
		final Map<Integer,LogModel> dataForAnalisationInfo = new ConcurrentHashMap<>();
		ExecutorService service = Executors.newFixedThreadPool(4);

		for (Logline logLine : logLinesFile)
		{
			service.submit(() ->
			{
				//separate thread
				for (ILoglineProcessor iLoglineProcessor : lineProcessors)
				{
					if (iLoglineProcessor.isNeedProcessing(logLine))
					{
						LogModel logModel = iLoglineProcessor.proccess(logLine, logLinesFile);
						dataForAnalisationInfo.put(logModel.getLine(), logModel);
						break;
					}
				}
			});
			progressBarChangesConsumer.accept(logLinesFile);
		}

		service.shutdown();
		while (true) {
			if (service.isTerminated()) {
				break;
			}
		}
		return dataForAnalisationInfo;
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
		lineProcessors.add(processor);
	}

	public LogConfiguration getLogConfiguration() {
		return logConfiguration;
	}

	public void setLogConfiguration(LogConfiguration logConfiguration) {
		this.logConfiguration = logConfiguration;
	}

	public List<ILoglineProcessor> getLineProcessors() {
		return lineProcessors;
	}

	public void setLineProcessors(List<ILoglineProcessor> lineProcessors) {
		this.lineProcessors = lineProcessors;
	}

	public IProcessCallback getCallback() {
		return callback;
	}

	@Override
	public void setCallback(IProcessCallback callback) {
		this.callback = callback;
	}

}
