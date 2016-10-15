package qla.modules.actions;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.DeleteFilesActionRQ;
import qla.modules.actions.models.DeleteFilesActionRS;

public class DeleteFilesAction extends AbstractAction<DeleteFilesActionRQ>{

	public DeleteFilesAction(Class rqClass) {
		super(rqClass);
	}

	@Override
	protected AbstractActionCommand proccess(DeleteFilesActionRQ rq) throws ActionException {
		List<String> logfiles = rq.getLogfiles();
		List<String> parsedFiles = rq.getParsedFiles();
		DeleteFilesActionRS rs = new DeleteFilesActionRS();
		if(logfiles != null) {
			for (Iterator<String> logfile = logfiles.iterator(); logfile.hasNext();) {
				String logfilename = logfile.next();
				if(!logfilename.isEmpty()) {
					File file = new File(ActionHelper.getPathToLogFile(logfilename));
					file.delete();
				}
			}
			rs.setLogfilesDeleted(true);
		}
		
		if(parsedFiles != null) {
			for (Iterator<String> paesedfile = parsedFiles.iterator(); paesedfile.hasNext();) {
				String parsedfilename = paesedfile.next();
				if(!parsedfilename.isEmpty()) {
					File file = new File(ActionHelper.getPathToParsedLogFile(parsedfilename));
					file.delete();
				}
			}
			rs.setParsedfilesDeleted(true);
		}
		rs.setSuccess("");
		return rs;
	}

}
