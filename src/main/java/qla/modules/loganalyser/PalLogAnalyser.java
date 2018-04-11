package qla.modules.loganalyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import qla.modules.loganalyser.models.LogExeptionModel;
import qla.modules.loganalyser.models.SignalModel;


public class PalLogAnalyser implements ILogAnalyser{
	private int lineCounter = 0;

	@Override
	public File analyseLog(String pathToLogFile, String pathToResultFile, String threadName) throws IOException {
		LogAnalisationInfo analisationInfo = new LogAnalisationInfo();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(pathToLogFile), "UTF8"));
		String line = null;
		while((line = readNextLine(br)) != null){
			boolean needProcessing = (threadName==null || threadName.isEmpty()) ?
					true : line.contains(threadName);
			if(needProcessing) {
				String action = defineAction(line);
				String nextLine = null;
				switch (action) {
					case "TDP_REQUEST":
						processTDPRequest(analisationInfo, br, line);
						break;
					case "TDP_RESPONSE":
						processTDPResponse(analisationInfo, br, line);
						break;
					case "SABRE_REQUEST":
						processSABRERequest(analisationInfo, br, line);
						break;
					case "SABRE_RESPONSE":
						processSABREResponse(analisationInfo, br, line);
						break;
					case "EXCEPTION_LINE":
						nextLine = processExceptionLine(analisationInfo, br, line);
						break;
					case "ERROR_LINE":
						processErrorLine(analisationInfo, br, line);
						break;
					default:
				}
			}

		}
		br.close();
		return LogAnalyseInfoSaver.save(analisationInfo, pathToResultFile);
	}

	private void processErrorLine(LogAnalisationInfo analisationInfo, BufferedReader br, String line) {
		// TODO Auto-generated method stub

	}


	private String processExceptionLine(LogAnalisationInfo analisationInfo, BufferedReader br, String line) throws IOException {
		String endl = "\n";
		String errorLine = line;
		int lineNumber = lineCounter;
		String nextLine = readNextLine(br);
		String exceptionName;
		if(nextLine!=null) {
			if(nextLine.matches("([a-zA-Z0-9]{1,40}[.]){1,10}[a-zA-Z0-9]{1,40}Exception.*")) {
				StringBuilder builder = new StringBuilder();
				builder.append(errorLine + endl);
				builder.append(nextLine + endl);
				exceptionName = nextLine.split("[\\s:]")[0];
				while ((nextLine = readNextLine(br))!=null) {
					if(nextLine.matches("\\tat ([a-zA-Z0-9$]{1,100}[.]){1,10}[a-zA-Z0-9]{1,40}[(].*[)]")){
						builder.append(nextLine + endl);
					} else {
						break;
					}
				}
				LogExeptionModel exeptionModel = new LogExeptionModel(lineNumber, builder.toString(), exceptionName);
				analisationInfo.addLogExeptionModel(exeptionModel);
			}
		}
		return nextLine;
	}


	private void processSABREResponse(LogAnalisationInfo analisationInfo, BufferedReader br, String line) throws IOException {
		SignalModel rqRsModel = readSABERequestOrResponse(analisationInfo, br, line);
		rqRsModel.setType("Response");
	}


	private String processTDPResponse(LogAnalisationInfo analisationInfo, BufferedReader br, String line) throws IOException {
		SignalModel rqRsModel = readTDPRequestOrResponse(analisationInfo, br, line);
		rqRsModel.setType("Response");
		return null;

	}

	void processSABRERequest(LogAnalisationInfo analisationInfo, BufferedReader br, String line) throws IOException {
		SignalModel rqRsModel = readSABERequestOrResponse(analisationInfo, br, line);
		rqRsModel.setType("Request");
	}

	private String processTDPRequest(LogAnalisationInfo analisationInfo, BufferedReader br, String line) throws IOException {
		SignalModel rqRsModel = readTDPRequestOrResponse(analisationInfo, br, line);
		rqRsModel.setType("Request");
		return null;
	}

	private SignalModel readSABERequestOrResponse(LogAnalisationInfo analisationInfo, BufferedReader br, String line) throws IOException{
		SignalModel rqRsModel = new SignalModel();
		rqRsModel.setLine(lineCounter);
		rqRsModel.setSystem("SABRE");
		String source = "";
		int startIndex = line.indexOf("<soap-env:Envelope");
		source = line.substring(startIndex, line.length());
		boolean isNeedProcessing = !line.contains("</soap-env:Envelope>");
		String nextline;
		while((nextline = readNextLine(br)) != null && isNeedProcessing){
			String rqpart = nextline.trim();
			source+= rqpart;
			isNeedProcessing = !rqpart.contains("</soap-env:Envelope>");
		}
		rqRsModel.setRqRsName(LogAnalyseUtils.getSabreActionName(source));
		rqRsModel.setSource(source);
		rqRsModel.setErrors(LogAnalyseUtils.getErrors(source));
		rqRsModel.setWarnings(LogAnalyseUtils.getSabreWarnings(source));
		rqRsModel.setId(analisationInfo.getSignalModelsSize());
		analisationInfo.addSignalModel(rqRsModel);
		return rqRsModel;
	}

	private SignalModel readTDPRequestOrResponse(LogAnalisationInfo analisationInfo, BufferedReader br, String line) throws IOException{
		SignalModel rqRsModel = new SignalModel();
		rqRsModel.setServantName(LogAnalyseUtils.getServantName(line));
		String request = readNextLine(br);
		rqRsModel.setId(analisationInfo.getSignalModelsSize());
		analisationInfo.addSignalModel(rqRsModel);
		rqRsModel.setLine(lineCounter);
		rqRsModel.setSource(request);
		rqRsModel.setErrors(LogAnalyseUtils.getErrors(request));
		rqRsModel.setWarnings(LogAnalyseUtils.getTDPWarnings(request));
		rqRsModel.setRqRsName(LogAnalyseUtils.getRequestName(request));
		rqRsModel.setSystem("TDP");
		return rqRsModel;
	}

	private String defineAction(String line) {
		if(isItTDPRequest(line)){
			return "TDP_REQUEST";
		}
		if(isItTDPResponse(line)){
			return "TDP_RESPONSE";
		}
		if(isItTSABRERequest(line)){
			return "SABRE_REQUEST";
		}
		if(isItTSABREResponse(line)){
			return "SABRE_RESPONSE";
		}
		if(isItExeptionLine(line)) {
			return "EXCEPTION_LINE";
		}
		if(isItErrorLine(line)) {
			return "ERROR_LINE";
		}
		return "ACTION_NOT_DEFINED";
	}

	private boolean isItErrorLine(String line) {
		// TO DO
		return false;
	}

	private boolean isItExeptionLine(String line) {
		return line.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3} \\[ERROR\\].*");
	}

	boolean isItTDPRequest(String line) {
		return line.endsWith("XML Request:");
	}

	boolean isItTDPResponse(String line){
		return line.endsWith("XML Response:");
	}

	boolean isItTSABREResponse(String line){
		return line.contains("Response from Sabre host :");
	}
	boolean isItTSABRERequest(String line){
		return line.contains("Sending to Sabre host :");
	}

	String readNextLine(BufferedReader br) throws IOException {
		lineCounter+=1;
		return br.readLine();
	}
}
