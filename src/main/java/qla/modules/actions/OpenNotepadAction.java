package qla.modules.actions;

import java.io.IOException;

import org.springframework.util.StringUtils;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.OpenNotepadActionRQ;
import qla.modules.actions.models.OpenNotepadActionRS;
import qla.modules.confuguration.AppConfiguration;
import qla.modules.loganalyser.LogAnalisationInfo;
import qla.modules.loganalyser.LogAnalyseInfoSaver;
import qla.modules.loganalyser.models.SignalModel;
import qla.modules.notepadintegration.NotepadUtils;
import qla.modules.prettyprinter.PrettyPrinter;

public class OpenNotepadAction extends AbstractAction<OpenNotepadActionRQ> {

	public OpenNotepadAction(Class rqClass) {
		super(rqClass);

	}

	@Override
	protected AbstractActionCommand proccess(OpenNotepadActionRQ rq) throws ActionException {
		OpenNotepadActionRS rs = new OpenNotepadActionRS();
		String logFilename = rq.getLogFilename();
		String LineNumber = rq.getLineNumber();
		String parsedfile = rq.getParsedfileName();
		int signalId = rq.getSignalId();
		if(!StringUtils.isEmpty(parsedfile) && signalId != -1) {
			openSourceOfSignalInNotepad(parsedfile, signalId);
		} else if (StringUtils.isEmpty(logFilename)) {
			throw new ActionException("File name null or empty.");
		} else {
			String pathToLogfile = ActionHelper.getPathToLogFile(logFilename);
			try {
				if (LineNumber != null) {
					NotepadUtils.openNotepadWithFileOnSelectedLine(pathToLogfile, LineNumber);
				} else {
					NotepadUtils.openNotepadWithFile(pathToLogfile);
				}
			} catch (IOException e) {
				throw new ActionException(e.getMessage());
			}
		}
		rs.setSuccess("");
		return rs;
	}

	private void openSourceOfSignalInNotepad(String parsedile, int signalId) throws ActionException {
		try {
			LogAnalisationInfo info = LogAnalyseInfoSaver
					.restore(ActionHelper.getPathToParsedLogFile(parsedile));
			SignalModel signalModel = info.getSignalModel(signalId);
			String signalSource = PrettyPrinter.prettyPrint(signalModel.getSource(), signalModel.getContentType());
			NotepadUtils.createNewFileAndOpen(ActionHelper.getPathToTempFolder() + "\\temp.txt",
					signalSource, signalModel.getContentType().toLowerCase());
		} catch (Exception exception) {
			throw new ActionException(exception.getMessage());
		}
		
	}

}
