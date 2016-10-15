package qla.modules.notepadintegration;

import java.io.IOException;

import qla.modules.confuguration.AppConfiguration;

public class NotepadUtils {

	public static void openNotepad(String args[]) throws IOException {
		String pathToNotepad = AppConfiguration.getProperty("notepad.path");
		String arguments = "";
		if (args != null && args.length > 0) {
			arguments = " " + String.join(" ", args);
		}
		Runtime.getRuntime().exec(pathToNotepad + arguments);
	}

	public static void openNotepadWithFile(String pathtoFile) throws IOException {
		String[] args = { "\"" + pathtoFile + "\"" };
		openNotepad(args);
	}

	public static void openNotepadWithFileOnSelectedLine(String pathtoFile, String lineNumber) throws IOException {
		String[] args = { "-n" + lineNumber, "\"" +pathtoFile + "\""};
		openNotepad(args);
	}

}
