package qla.modules.loganalyser;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class PalLogAnalyserTest {

	@Test
	public void test() throws IOException {
		ILogAnalyser analyser = new PalLogAnalyser();
		long start = System.currentTimeMillis();
		analyser.analyseLog("d:/matrixtdp (7).log", "d:/DEV/out.txt", "http-0.0.0.0-8080-2");
		System.out.println("Processing time: "+ (System.currentTimeMillis()-start));
	}

}
