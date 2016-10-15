package qla.modules.log.processors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import qla.modules.loganalyser.LogAnalisationInfo;

public interface ILogProcessor {
	LogAnalisationInfo process(String pathToLogfile) throws IOException;
	void setCallback(IProcessCallback callback);
}
