package qla.modules.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;
import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.FolderChooserActionRQ;
import qla.modules.actions.models.FolderChooserActionRS;

public class FolderChooserAction extends AbstractAction<FolderChooserActionRQ>  {
	public static final String GET_LOGFILES_SUBACTION = "GET_LOG_FILES";
	public static final String GET_PARSED_LOGFILES_SUBACTION = "GET_PARSED_LOG_FILES";
	
	public FolderChooserAction(@SuppressWarnings("rawtypes") Class rqClass) {
		super(rqClass);
	}
	
	@Override
	public AbstractActionCommand proccess(FolderChooserActionRQ rq) throws ActionException {
		FolderChooserActionRS rs = new FolderChooserActionRS();
		String subAction = rq.getSubAction();
		String pathToFolder = "";
		if(!StringUtils.isEmpty(subAction)){
			switch (subAction) {
			case GET_LOGFILES_SUBACTION:
				pathToFolder = ActionHelper.getLogFilesFolder();
				break;
			case GET_PARSED_LOGFILES_SUBACTION:
				pathToFolder = ActionHelper.getParsedLogFilesFolder();
				break;
			default:
				throw new ActionException("Invalid subaction:" + subAction);
			}
		} else {
			pathToFolder = rq.getFolder();
		}
		File folder = new File(pathToFolder);
		if(folder.exists() && folder.isDirectory()){
				List<File> files = ActionHelper.getFilesInFolder(pathToFolder);
				List<String> fileNames = new ArrayList<>();
				for (File file : files) {
					fileNames.add(file.getName());
				}
				rs.setListOflogfiles(fileNames);
		} else {
			throw new ActionException("Folder by given path does not exist:" + pathToFolder);
		}
		rs.setSuccess("");
		return rs;
	}
}
