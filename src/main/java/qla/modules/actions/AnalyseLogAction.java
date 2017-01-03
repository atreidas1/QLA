package qla.modules.actions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.AnalyseLogActionRQ;
import qla.modules.actions.models.AnalyseLogActionRS;
import qla.modules.log.LogFile;
import qla.modules.log.processors.ILogProcessor;
import qla.modules.log.processors.IProcessCallback;
import qla.modules.loganalyser.LogAnalisationInfo;
import qla.modules.loganalyser.LogAnalyseInfoSaver;
import qla.modules.serialiser.JsonSerialiser;
/**
 * Action for the analysis of the log files.
 * 
 * @author Mikhail_Mylitsa
 *
 */
public class AnalyseLogAction extends AbstractAction<AnalyseLogActionRQ> {
	private ILogProcessor logProcessor;
	
	
	public AnalyseLogAction(Class rqClass) {
		super(rqClass);
	}

	@Override
	public AbstractActionCommand proccess(AnalyseLogActionRQ rq) throws ActionException {
		String choosedLog = rq.getChoosedLog();
		ActionHelper.checkStringForNullOrEmpty(choosedLog, "Choose any logfile!");
		String pathToChoosedLog = ActionHelper.getPathToLogFile(choosedLog);
		String pathToParsedLog = ActionHelper.generatePathToParsedLog(choosedLog);
		boolean isNeedProccess = isNeedProcess(pathToChoosedLog, pathToParsedLog);
		AnalyseLogActionRS resp = new AnalyseLogActionRS();
		if(isNeedProccess){
			try {
				logProcessor.setCallback(createProgressCalback(rq));
				LogAnalisationInfo logAnalisationInfo = logProcessor.process(pathToChoosedLog);
				logAnalisationInfo.setLogFile(choosedLog);
				File infoFile = LogAnalyseInfoSaver.save(logAnalisationInfo, pathToParsedLog);
				File logFile = new File(pathToChoosedLog);
				infoFile.setLastModified(logFile.lastModified());
				resp.setSuccess("Log analyse completed sucessfully!");
				resp.setLogfile(choosedLog);
				resp.setParsedfile(ActionHelper.generateOutFilename(choosedLog));
			} catch (IOException exception) {
				throw new ActionException(exception.getMessage());
			}
		} else {
			resp.setSuccess("");
			resp.setLogfile(choosedLog);
			resp.setParsedfile(ActionHelper.generateOutFilename(choosedLog));
		}
		return resp;
	}

	private boolean isNeedProcess(String pathToChoosedLog, String pathToParsedLog) {
		File logFile = new File(pathToChoosedLog);
		File parsedFile = new File(pathToParsedLog);
		if(!parsedFile.exists() ||
			parsedFile.lastModified() != logFile.lastModified()){
			return true;
		} 
		return false;
	}

	public ILogProcessor getLogProcessor() {
		return logProcessor;
	}

	public void setLogProcessor(ILogProcessor logProcessor) {
		this.logProcessor = logProcessor;
	}
	
	private  IProcessCallback createProgressCalback(AnalyseLogActionRQ rq) {
		IProcessCallback callback = new IProcessCallback() {
			private WebSocketSession session  = rq.getSession();
			private Double progress = 0.0;
			private LogFile logFile;
			@Override
			public void startProcessing(Map<String, Object> data) {
				Map<String, Object> message = new HashMap<>();
				logFile = (LogFile) data.get("logFile");
				message.put(IAction.STATUS_PROP, "success");
				message.put(ACTION_PROP, "LOGFILE_PROCESSING_START");
				sendMessageToUI(message);
			}
			
			@Override
			public void progressOfProcessing(Map<String, Object> data) {
				Double progress = Double.valueOf(logFile.getProcessedBytesCount())/
						Double.valueOf(logFile.getFileSize())*100;
				if(progress - this.progress >= 1){
					Map<String, Object> message = new HashMap<>();
					message.put(IAction.STATUS_PROP, "success");
					message.put(ACTION_PROP, "LOGFILE_PROCESSING_PROGRESS");
					this.progress = progress;
					message.put("PROGRESS", progress.intValue());
					sendMessageToUI(message);
				}
			}
			
			@Override
			public void endOfProcessing(Map<String, Object> data) {
				Map<String, Object> message = new HashMap<>();
				message.put(IAction.STATUS_PROP, "success");
				message.put(ACTION_PROP, "LOGFILE_PROCESSING_END");
				sendMessageToUI(message);
				
			}
			
			private void sendMessageToUI(Map<String, Object> data) {
				String message = JsonSerialiser.serialise(data);
				try {
					session.sendMessage(new TextMessage(message));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		return callback;
	}
}
