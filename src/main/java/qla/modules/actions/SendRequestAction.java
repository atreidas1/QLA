package qla.modules.actions;

import java.util.Map;

import org.springframework.util.StringUtils;
import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.SendRequestActionRQ;
import qla.modules.actions.models.SendRequestActionRS;
import qla.modules.confuguration.AppConfiguration;
import qla.modules.soapclient.SendRequestTask;
import qla.modules.soapclient.SystemRequestParameters;

public class SendRequestAction extends AbstractAction<SendRequestActionRQ>{
    private SendRequestTask sendRequestTask;
    private Map<String, SystemRequestParameters> systemParams;

	public SendRequestAction(@SuppressWarnings("rawtypes") Class rqClass) {
		super(rqClass);
	}

	@Override
	protected AbstractActionCommand proccess(SendRequestActionRQ rq) throws ActionException {
		String subaction = rq.getSubaction();
		SendRequestActionRS rs = new SendRequestActionRS();
		switch (subaction) {
		case "SEND_REQUEST":
			ActionHelper.checkStringForNullOrEmpty(rq.getUrl(), "Please enter server URL!");
			ActionHelper.checkStringForNullOrEmpty(rq.getSystem(), "Please enter system to send request!");
			ActionHelper.checkStringForNullOrEmpty(rq.getRequestSource(), "Please enter request source!");
			sendAsynchronously(rq);
			rs.setAction("requestSended");
			break;
		case "CANCEL_REQUEST":
			cancelRequest();
			rs.setAction("requestCanceled");
		default:
			break;
		}
		return rs;
	}

	private void cancelRequest(){
		if(sendRequestTask != null) {
			sendRequestTask.setCanceled(true);
			sendRequestTask = null;
		}
	}
	
	private void sendAsynchronously(SendRequestActionRQ rq) {
		String timeoutProp = AppConfiguration.getProperty("request.timeout");
		SystemRequestParameters parameters = systemParams.get(rq.getSystem());
		sendRequestTask = new SendRequestTask(rq.getUrl(), rq.getRequestSource(),
				rq.getServant(),  rq.getSession(), parameters);
		new Thread(sendRequestTask).start();
	}

	public Map<String, SystemRequestParameters> getSystemParams() {
		return systemParams;
	}

	public void setSystemParams(Map<String, SystemRequestParameters> systemParams) {
		this.systemParams = systemParams;
	}  
}
