package qla.modules.loganalyser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LogAnalyseInfoSaver {
	public static File save(LogAnalisationInfo info, File file) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(info);
		out.close();
		fileOut.close();
		return file;
	}

	public static File save(LogAnalisationInfo info, String pathToFile) throws IOException {
		return save(info, new File(pathToFile));
	}
	public static LogAnalisationInfo restore(File file) throws IOException, ClassNotFoundException {
		LogAnalisationInfo logAnalisationInfo = null;
		FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        logAnalisationInfo = (LogAnalisationInfo) in.readObject();
        in.close();
        fileIn.close();
        return logAnalisationInfo;
	}
	
	public static LogAnalisationInfo restore(String pathToFile) throws IOException, ClassNotFoundException {
        return restore(new File(pathToFile));
	}
}
