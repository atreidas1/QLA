package qla.modules.loganalyser;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import qla.modules.loganalyser.models.SignalModel;

public class LogAnalyseInfoSaverTest {

	@Test
	public void test() throws ClassNotFoundException, IOException {
		LogAnalisationInfo analisationInfo = LogAnalyseInfoSaver.restore("d:/saved.ser");
		SignalModel model = analisationInfo.getSignalModel(580);
		System.out.println(model);
	}

}
