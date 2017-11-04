package qla.modules.actions;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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
	public static final String GET_REQUESTS_LIBRARY_SUBACTION = "GET_REQUESTS_LIBRARY";
	public static final String GET_REQUESTS_SUBACTION = "GET_REQUESTS";
	/**
	 * {
	 * folderKey,
	 * file,
	 * pathToFolder,
	 * subAction[GET_FILES_IN_FOLDER_BY_KEY | GET_FILES_IN_FOLDER | GET_FILE_CONTENT]
	 * }
	 */
	public FolderChooserAction(@SuppressWarnings("rawtypes") Class rqClass) {
		super(rqClass);
	}
	public AbstractActionCommand proccess(FolderChooserActionRQ rq) throws ActionException {
		FolderChooserActionRS rs = new FolderChooserActionRS();
		ActionHelper.checkForNull(rq.getSubAction(), "There is no any command.");
		String subaction = rq.getSubAction();
		String folderKey = rq.getFolderKey();
		String pathToFolder = rq.getPathToFolder();
		String filename = rq.getFile();
		switch (subaction) {
		case "GET_FILES_IN_FOLDER_BY_KEY":
			if(!StringUtils.isEmpty(folderKey)){
				pathToFolder = ActionHelper.getPathToFolderByKey(folderKey, rq.isIncludeBaseDir());
				File directory = new File(pathToFolder);
				if(!directory.exists()) {
					directory.mkdir();
				}
				List<File> files = ActionHelper.getFilesInFolder(pathToFolder);
				setFilesToRs(files, rs);
			}
			break;
		case "GET_FILES_IN_FOLDER":
			if(!StringUtils.isEmpty(pathToFolder)){
				List<File> files = ActionHelper.getFilesInFolder(pathToFolder);
				setFilesToRs(files, rs);
			}
			break;
		case "GET_FILE_CONTENT":
			ActionHelper.checkStringForNullOrEmpty(folderKey, "Choose any folder!");
			ActionHelper.checkStringForNullOrEmpty(filename, "Choose any file!");
			String folder = ActionHelper.getPathToFolderByKey(folderKey, rq.isIncludeBaseDir());
			try {
				String fileContent = String.join("\n", Files.readAllLines(FileSystems.getDefault().getPath(folder, filename)));
				rs.setFileContent(fileContent);
			} catch (IOException e) {
				rs.setError(e.getMessage());
			}
			break;
		default:
			throw new ActionException("Invalid subaction:" + subaction);
		}
		rs.setSuccess("");
		return rs;
	}
	
	
	private void setFilesToRs(List<File> files, FolderChooserActionRS rs) {
		List<String> fileNames = new ArrayList<>();
		for (File file : files) {
			fileNames.add(file.getName());
		}
		rs.setListOflogfiles(fileNames);
	}
//	@Override
//	public AbstractActionCommand proccess(FolderChooserActionRQ rq) throws ActionException {
//		FolderChooserActionRS rs = new FolderChooserActionRS();
//		String subAction = rq.getSubAction();
//		String pathToFolder = "";
//		if(!StringUtils.isEmpty(subAction)){
//			switch (subAction) {
//			case GET_LOGFILES_SUBACTION:
//				pathToFolder = ActionHelper.getLogFilesFolder();
//				break;
//			case GET_PARSED_LOGFILES_SUBACTION:
//				pathToFolder = ActionHelper.getParsedLogFilesFolder();
//				break;
//			default:
//				throw new ActionException("Invalid subaction:" + subAction);
//			}
//		} else {
//			pathToFolder = rq.getFolder();
//		}
//		File folder = new File(pathToFolder);
//		if(folder.exists() && folder.isDirectory()){
//				List<File> files = ActionHelper.getFilesInFolder(pathToFolder);
//				List<String> fileNames = new ArrayList<>();
//				for (File file : files) {
//					fileNames.add(file.getName());
//				}
//				rs.setListOflogfiles(fileNames);
//		} else {
//			throw new ActionException("Folder by given path does not exist:" + pathToFolder);
//		}
//		rs.setSuccess("");
//		return rs;
//	}
}
