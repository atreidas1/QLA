package qla.modules.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import qla.modules.actions.exeption.ActionException;
import qla.modules.confuguration.AppConfiguration;
import qla.modules.servers.models.ServerModel;

public class ActionHelper {
	public static Map<String, Object> createErrorAction(String message) {
		Map<String, Object> resp = new HashMap<>();
		resp.put(IAction.ACTION_PROP, UIActions.ERROR);
		resp.put("message", message);
		return resp;
	}

	public static Map<String, Object> createSuccesfulAction(String message) {
		Map<String, Object> resp = new HashMap<>();
		resp.put(IAction.ACTION_PROP, UIActions.SUCCESS);
		resp.put("message", message);
		return resp;
	}

	public static void checkStringForNullOrEmpty(String string, String messageOnFail) throws ActionException {
		if (string == null || string.isEmpty()) {
			throw new ActionException(messageOnFail);
		}
	}

	public static String getPathToLogFile(String fileName) throws ActionException {
		checkStringForNullOrEmpty(fileName, "Choose any logfile!");
		return AppConfiguration.getProperty("logfiles.folder") + fileName;
	}

	public static String getPathToParsedLogFile(String fileName) throws ActionException {
		checkStringForNullOrEmpty(fileName, "Choose any logfile!");
		return AppConfiguration.getProperty("parsed.logs.folder") + fileName;
	}

	public static String generatePathToParsedLog(String choosedLog) {
		String filename = generateOutFilename(choosedLog);
		return AppConfiguration.getProperty("parsed.logs.folder") + filename;
	}

	public static String generateOutFilename(String filename) {
		String out;
		out = filename +  AppConfiguration.getProperty("parsed.logs.suffix") 
		+ AppConfiguration.getProperty("parsed.logs.ext");
		return out;
	}

	public static String defineLogFilenameByParsed(String parsedfileName) {
		int lustUnderscoreIndex = parsedfileName.lastIndexOf('_');
		String logFilename = parsedfileName.substring(0, lustUnderscoreIndex);
		List<File> files = getFilesInLogsFolder();
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File file = iterator.next();
			String fileName = file.getName();
			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
			if(fileName.equals(logFilename)){
				return file.getName();
			}
		}
		return null;
	}

	private static List<File> getFilesInLogsFolder() {
		String pathTofolder = AppConfiguration.getProperty("logfiles.folder");
		return getFilesInFolder(pathTofolder);
	}

	private static List<File> getFilesInFolder(String pathToFolder) {
		File folder = new File(pathToFolder);
		File[] files = folder.listFiles();
		List<File> files2 = new ArrayList<>();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].isDirectory()) {
				files2.add(files[i]);
			}
		}
		return files2;
	}

	public static void checkForNull(Object object, String message) throws ActionException {
		if(object == null) {
			throw new ActionException(message);
		}
		
	}

}
