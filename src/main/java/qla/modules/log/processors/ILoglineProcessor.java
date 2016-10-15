package qla.modules.log.processors;

import qla.modules.log.LogFile;
import qla.modules.log.Logline;
import qla.modules.loganalyser.LogAnalisationInfo;

public interface ILoglineProcessor {
	boolean isNeedProcessing(Logline logline);
	void proccess(Logline logline, LogAnalisationInfo info, LogFile logFile);
	
}
