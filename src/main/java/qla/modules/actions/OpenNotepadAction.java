package qla.modules.actions;

import java.io.IOException;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.OpenNotepadActionRQ;
import qla.modules.actions.models.OpenNotepadActionRS;
import qla.modules.notepadintegration.NotepadUtils;

public class OpenNotepadAction extends AbstractAction<OpenNotepadActionRQ> {

	public OpenNotepadAction(Class rqClass) {
		super(rqClass);

	}

	@Override
	protected AbstractActionCommand proccess(OpenNotepadActionRQ rq) throws ActionException {
		String logFilename = rq.getLogFilename();
		String LineNumber = rq.getLineNumber();
		if (logFilename == null || logFilename.isEmpty()) {
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
		OpenNotepadActionRS rs = new OpenNotepadActionRS();
		rs.setSuccess("");
		return rs;
	}

}
