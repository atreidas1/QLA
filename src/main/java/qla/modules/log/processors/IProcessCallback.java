package qla.modules.log.processors;

import java.util.Map;

public interface IProcessCallback {
	void startProcessing(Map<String, Object> data);
	void progressOfProcessing(Map<String, Object> data);
	void endOfProcessing(Map<String, Object> data);
}
