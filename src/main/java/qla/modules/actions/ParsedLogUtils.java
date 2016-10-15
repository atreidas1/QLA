package qla.modules.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import qla.modules.loganalyser.LogAnalisationInfo;
import qla.modules.loganalyser.LogAnalyseInfoSaver;
import qla.modules.loganalyser.models.LogExeptionModel;
import qla.modules.loganalyser.models.SignalModel;

public class ParsedLogUtils {
	/**
	 * 
	 * @param file
	 * @param fromId
	 * @param toId
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static List<SignalModel> readBasicInfoOfSignals(LogAnalisationInfo analisationInfo, int fromId, int toId){

		List<SignalModel> signals = new ArrayList<>();
		boolean isNeedAccountId = fromId > 0 && toId > 0;
		if(isNeedAccountId){
			for (int i = fromId; i < analisationInfo.getSignalModelsSize(); i++) {
				SignalModel result = getSignalWithoutSource(analisationInfo.getSignalModel(i));
				signals.add(result);
			}
		}else {
			for (Iterator<SignalModel> iterator = analisationInfo.getSignalModels().iterator();
					iterator.hasNext();) {
				SignalModel signalModel = iterator.next();
				SignalModel result = getSignalWithoutSource(signalModel);
				signals.add(result);
			}
		}
		return signals;
	}

	public static SignalModel getSignalWithoutSource(SignalModel signalModel) {
		SignalModel result = new SignalModel();
		((SignalModel) result.setId(signalModel.getId())
		.setLine(signalModel.getLine()))
		.setSystem(signalModel.getSystem())
		.setRqRsName(signalModel.getRqRsName())
		.setServantName(signalModel.getServantName())
		.setErrors(signalModel.getErrors())
		.setWarnings(signalModel.getWarnings())
		.setContentType(signalModel.getContentType())
		.setThread(signalModel.getThread());
		return result;
	}
	
	public static List<SignalModel> readSignals(LogAnalisationInfo analisationInfo, int fromId, int toId) {
		List<SignalModel> signals = new ArrayList<>();
		for (int i = fromId; i < analisationInfo.getSignalModelsSize(); i++) {
			signals.add(analisationInfo.getSignalModel(i));
		}
		return signals;
	}
	
	public static List<SignalModel> getSignalsByThread(LogAnalisationInfo analisationInfo, String thread) {
		List<SignalModel> signals = new ArrayList<>();
		Iterator<SignalModel> iterator = analisationInfo.getSignalModels().iterator();
		while (iterator.hasNext()) {
			SignalModel signalModel = iterator.next();
			if(signalModel.getThread().equals(thread)){
				signals.add(getSignalWithoutSource(signalModel));
			}
		}
		return signals;
		
	}
	
	public static List<LogExeptionModel> getExceptionsByThread(LogAnalisationInfo analisationInfo, String thread) {
		List<LogExeptionModel> exeptions = new ArrayList<>();
		Iterator<LogExeptionModel> iterator = analisationInfo.getExeptionModels().iterator();
		while (iterator.hasNext()) {
			LogExeptionModel exception = iterator.next();
			if(exception.getThread().equals(thread)){
				exeptions.add(getExseptionWithoutSource(exception));
			}
		}
		return exeptions;
	}
	
	public static String FindThreadByJsessionId(String jsessionId, LogAnalisationInfo analisationInfo){
		Iterator<SignalModel> iterator = analisationInfo.getSignalModels().iterator();
		while (iterator.hasNext()) {
			SignalModel signalModel = iterator.next();
			if(signalModel.getSource().contains(jsessionId)){
				return signalModel.getThread();
			}
		}
		return "";
	}
	
	public static SignalModel readSignalById(int id, LogAnalisationInfo analisationInfo) {
		return analisationInfo.getSignalModel(id);
	}

	public static List<SignalModel> getSignalsByContent(String searchContent, LogAnalisationInfo analisationInfo) {
		List<SignalModel> signals = new ArrayList<>();
		Iterator<SignalModel> iterator = analisationInfo.getSignalModels().iterator();
		while (iterator.hasNext()) {
			SignalModel signalModel = iterator.next();
			if(signalModel.getSource().contains(searchContent)){
				signals.add(getSignalWithoutSource(signalModel));
			}
		}
		return signals;
	}

	public static List<LogExeptionModel> getExseptionsWithoutSource(LogAnalisationInfo analisationInfo) {
		List<LogExeptionModel> exeptions = new ArrayList<>();
		Iterator<LogExeptionModel> iterator = analisationInfo.getExeptionModels().iterator();
		while (iterator.hasNext()) {
			LogExeptionModel exception = iterator.next();
			exeptions.add(getExseptionWithoutSource(exception));
		}
		return exeptions;
	}
	
	public static LogExeptionModel getExseptionWithoutSource(LogExeptionModel exeptionModel) {
		LogExeptionModel logExeptionModel = new LogExeptionModel();
		logExeptionModel.setId(exeptionModel.getId());
		logExeptionModel.setLine(exeptionModel.getLine());
		logExeptionModel.setThread(exeptionModel.getThread());
		logExeptionModel.setName(exeptionModel.getName());
		return logExeptionModel;
	}
}
