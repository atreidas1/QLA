package qla.modules.loganalyser;

import java.io.File;
import java.io.IOException;

public interface ILogAnalyser {
	File analyseLog(String pathToLogFile, String pathToResultFile, String threadName) throws IOException;
}
