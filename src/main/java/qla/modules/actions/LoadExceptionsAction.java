package qla.modules.actions;

import java.io.IOException;
import java.util.List;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.LoadExceptionsActionRQ;
import qla.modules.actions.models.LoadExceptionsActionRS;
import qla.modules.loganalyser.LogAnalisationInfo;
import qla.modules.loganalyser.LogAnalyseInfoSaver;
import qla.modules.loganalyser.models.LogExeptionModel;
import qla.modules.stringutils.StringUtils;

public class LoadExceptionsAction extends AbstractAction<LoadExceptionsActionRQ>{

	public LoadExceptionsAction(Class rqClass) {
		super(rqClass);
	}

	@Override
	protected AbstractActionCommand proccess(LoadExceptionsActionRQ rq) throws ActionException {
		String parsedFile = rq.getParsedfile();
		String exceptionId = rq.getExceptionId();
		String thread = rq.getThread();
		LoadExceptionsActionRS rs = new LoadExceptionsActionRS();
		
		try {
			LogAnalisationInfo info = LogAnalyseInfoSaver.restore(ActionHelper.getPathToParsedLogFile(parsedFile));
			List<LogExeptionModel> exceptions = info.getExeptionModels();
			if(exceptions == null || exceptions.isEmpty()) {
				rs.setSuccess("No exceptins found.");
			} else {
				if(StringUtils.isEmpty(exceptionId)){
					if(StringUtils.isEmpty(thread)){
						rs.setExceptions(ParsedLogUtils.getExseptionsWithoutSource(info));
					} else {
						rs.setExceptions(ParsedLogUtils.getExceptionsByThread(info, thread));
					}
				} else {
					rs.setException(info.getLogExeptionModel(Integer.parseInt(exceptionId)));
				}
				rs.setSuccess("");
			}
			
		} catch (ClassNotFoundException | IOException e) {
			throw new ActionException(e.getMessage());
		} 
		return rs;
	}

}
