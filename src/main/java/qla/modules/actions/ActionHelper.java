package qla.modules.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import qla.modules.actions.exeption.ActionException;
import qla.modules.confuguration.AppConfiguration;

public class ActionHelper {
	public static final String PARSED_LOGS_FOLDER = "parsed";
	public static final String PARSED_LOGS_EXT  = ".bin";
	
	public static Map<String, Object> createErrorAction(String message) {
		Map<String, Object> resp = new HashMap<>();
		resp.put(IAction.ACTION_PROP, UIActions.ERROR);
		resp.put("message", message);
		return resp;
	}

	public static String getPathToTempFolder() throws ActionException {
		String pathToTempFolder = getLogFilesFolder() + "temp";
		File file = new File(pathToTempFolder);
		if(!file.exists()) {
			file.mkdir();
		}
		return pathToTempFolder;
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
		return getLogFilesFolder() + fileName;
	}

	public static String getPathToParsedLogFile(String fileName) throws ActionException {
		checkStringForNullOrEmpty(fileName, "Choose any logfile!");
		return getParsedLogFilesFolder() + fileName;
	}

	public static String generatePathToParsedLog(String choosedLog) throws ActionException {
		String filename = generateOutFilename(choosedLog);
		return getParsedLogFilesFolder() + filename;
	}

	public static String generateOutFilename(String filename) {
		String out;
		out = filename + PARSED_LOGS_EXT;
		return out;
	}

	public static String defineLogFilenameByParsed(String parsedfileName) throws ActionException {
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

	public static List<File> getFilesInLogsFolder() throws ActionException {
		String pathTofolder = getLogFilesFolder();
		return getFilesInFolder(pathTofolder);
	}

	public static String getLogFilesFolder() throws ActionException {
		String pathToFolder = AppConfiguration.getProperty("logfiles.folder");
		if(StringUtils.isEmpty(pathToFolder)){
			throw new ActionException("Path to logfiles folder did not configured!");
		}
		pathToFolder = pathToFolder.endsWith(File.separator) ? pathToFolder : pathToFolder + File.separator;
		return pathToFolder;
	}

	public static String getParsedLogFilesFolder() throws ActionException {
		String pathToFolder = getLogFilesFolder() + PARSED_LOGS_FOLDER + File.separator;
		File file = new File(pathToFolder);
		if(!file.exists()) {
			file.mkdir();
		}
		return pathToFolder;
	}
	
	public static List<File> getFilesInFolder(String pathToFolder) {
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
