package qla.modules.log.processors;

import qla.modules.log.LogFile;
import qla.modules.log.Logline;
import qla.modules.loganalyser.models.LogModel;

import java.util.concurrent.atomic.AtomicInteger;

public interface ILoglineProcessor {
	boolean isNeedProcessing(Logline logline);
	LogModel proccess(Logline logline, LogFile logFile);

}
