package qla.modules.actions;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.SignalInfoActionRQ;
import qla.modules.actions.models.SignalInfoActionRS;
import qla.modules.loganalyser.LogAnalisationInfo;
import qla.modules.loganalyser.LogAnalyseInfoSaver;
import qla.modules.loganalyser.models.SignalModel;
import qla.modules.prettyprinter.PrettyPrinter;
import qla.modules.prettyprinter.XmlPrettyPrinter;

public class SignalInfoAction extends AbstractAction<SignalInfoActionRQ> {

	@SuppressWarnings("rawtypes")
	public SignalInfoAction(Class rqClass) {
		super(rqClass);
	}

	@Override
	public AbstractActionCommand proccess(SignalInfoActionRQ rq) throws ActionException {
		String signalId = rq.getSignalId();
		String parsedFile = rq.getParsedFile();
		ActionHelper.checkStringForNullOrEmpty(signalId, "Signal id null or empty!");
		ActionHelper.checkStringForNullOrEmpty(parsedFile, "Parsed filename null or empty!");
		int id = 0;
		SignalModel signal;
		try {
			id = Integer.parseInt(signalId);
			LogAnalisationInfo info = LogAnalyseInfoSaver
					.restore(ActionHelper.getPathToParsedLogFile(parsedFile));
			signal = ParsedLogUtils.readSignalById(id, info);
			ActionHelper.checkForNull(signal,
					String.format("Signal with id = %s not found in %s", signalId, parsedFile));

			String source = signal.getSource();
			String warnings = signal.getWarnings();
			String errors = signal.getErrors();
			String contentType = signal.getContentType();
			if (!source.isEmpty()) {
				signal.setSource(PrettyPrinter.prettyPrint(source, contentType));
			}
			if (!warnings.isEmpty()) {
				signal.setWarnings(PrettyPrinter.prettyPrint(warnings, contentType));
			}
			if (!errors.isEmpty()) {
				signal.setErrors(PrettyPrinter.prettyPrint(errors, contentType));
			}
		} catch (Exception exception) {
			throw new ActionException(exception.getMessage());
		}
		SignalInfoActionRS rs = new SignalInfoActionRS();
		rs.setSignal(signal);
		rs.setSuccess("");
		return rs;
	}
}
