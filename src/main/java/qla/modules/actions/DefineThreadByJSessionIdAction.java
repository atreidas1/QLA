package qla.modules.actions;

import java.io.IOException;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.DefineThreadByJSessionIdActionRQ;
import qla.modules.actions.models.DefineThreadByJSessionIdActionRS;
import qla.modules.confuguration.AppConfiguration;
import qla.modules.loganalyser.LogAnalisationInfo;
import qla.modules.loganalyser.LogAnalyseInfoSaver;
import qla.modules.loganalyser.LogAnalyseUtils;
/**
 * Action for defining thread name by JSESSIONID
 * @author Mikhail_Mylitsa
 *
 */
public class DefineThreadByJSessionIdAction extends AbstractAction<DefineThreadByJSessionIdActionRQ> {

	public DefineThreadByJSessionIdAction(Class rqClass) {
		super(rqClass);
	}
	
	/**
	 *Request example: {"jsessionId":"8D9807B5923B18FFF81EBFCC95821A17","logFile":null,"action":"DEFINE_THREAD_ACTION"}
	 */
	@Override
	public AbstractActionCommand proccess(DefineThreadByJSessionIdActionRQ rq) throws ActionException {
		String jsessionId = rq.getJsessionId();
		String infoFile = rq.getLogFile();
		DefineThreadByJSessionIdActionRS rs = new DefineThreadByJSessionIdActionRS();
		ActionHelper.checkStringForNullOrEmpty(jsessionId, "Value jsessionId null or empty");
		ActionHelper.checkStringForNullOrEmpty(infoFile, "Please choose any log file!");
		String threadName;
		LogAnalisationInfo info;
		try {
			info = LogAnalyseInfoSaver.restore(ActionHelper.getPathToParsedLogFile(infoFile));
			threadName = ParsedLogUtils.FindThreadByJsessionId(jsessionId, info);
		} catch (IOException | ClassNotFoundException e) {
			throw new ActionException(e.getMessage());
		}
		if(!threadName.isEmpty()) {
			rs.setThreadName(threadName);
			rs.setSuccessAction(UIActions.THREAD_DEFINED);
		} else {
			rs.setError("Thread name by JsessionId=" + jsessionId +" not defined!");
		}
		rs.setSuccess("");
		return rs;
	}

}
