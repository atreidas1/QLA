package qla.modules.actions;

import java.io.IOException;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.GetLogInfoActionRQ;
import qla.modules.actions.models.GetLogInfoActionRS;
import qla.modules.loganalyser.LogAnalisationInfo;
import qla.modules.loganalyser.LogAnalyseInfoSaver;
import qla.modules.stringutils.StringUtils;

public class GetLogInfoAction extends AbstractAction<GetLogInfoActionRQ>{

	public GetLogInfoAction(Class rqClass) {
		super(rqClass);
	}

	@Override
	protected AbstractActionCommand proccess(GetLogInfoActionRQ rq) throws ActionException {
		String infoFile = rq.getInfoFile();
		ActionHelper.checkStringForNullOrEmpty(infoFile, "Select any info file!");
		String pathToInfoFile = ActionHelper.getPathToParsedLogFile(infoFile);
		LogAnalisationInfo info;
		int numberOfExceptions = 0;
		try {
			info = LogAnalyseInfoSaver.restore(pathToInfoFile);
		} catch (IOException | ClassNotFoundException e){
			throw new ActionException(e.getMessage(), e);
		}
		String thread = rq.getThread();
		String jsessionId = rq.getjSessionId();
		String searchContent = rq.getSearchContent();
		GetLogInfoActionRS rs = new GetLogInfoActionRS();
		
		if(!StringUtils.isEmpty(thread)){
			numberOfExceptions = ParsedLogUtils.getExceptionsByThread(info, thread).size();
			rs.setSignals(ParsedLogUtils.getSignalsByThread(info, thread));
		} else if (!StringUtils.isEmpty(jsessionId)) {
			thread = ParsedLogUtils.FindThreadByJsessionId(jsessionId, info);
			numberOfExceptions = ParsedLogUtils.getExceptionsByThread(info, thread).size();
			rs.setSignals(ParsedLogUtils.getSignalsByThread(info, thread));
		} else if(!StringUtils.isEmpty(searchContent)){
			rs.setSignals(ParsedLogUtils.getSignalsByContent(searchContent, info));
			
		} else {
			numberOfExceptions = info.getExeptionModelsSize();
			rs.setSignals(ParsedLogUtils.readBasicInfoOfSignals(info, -1, -1));
		}
		rs.setNumberOfExceptions(numberOfExceptions);
		rs.setSuccess("");
		rs.setLogfile(info.getLogFile());
		rs.setInfoFile(infoFile);
		return rs;
	}

}
