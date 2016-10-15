package qla.modules.log.processors;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.Test;

public class TDPLogProcessorTest {

	@Test
	public void test() {
		TDPLogProcessor tdpLogProcessor = new TDPLogProcessor();
		Pattern pattern = Pattern.compile(".*(XML Request|XML Response):.*", Pattern.DOTALL);
		String rq = "2016-09-13 11:15:23,688 [DEBUG] [http-0.0.0.0-8080-11] [com.datalex.m3.servants.reservation.air.bean.ReservationAirBookingSvBean] XML Request:";
		System.out.println(pattern.matcher(rq).matches());
		
		SignalLoglineProcessor tdpLoglineProcessor = new SignalLoglineProcessor();
		tdpLoglineProcessor.setConditionPattern(".*(XML Request|XML Response):.*");
		tdpLoglineProcessor.setExtractPattern("<.*>$");
		tdpLoglineProcessor.setSignalNamePattern("<(\\w*?) ");
		tdpLoglineProcessor.setWarningsPattern("<ns1:Warnings.{1,}</ns1:Warnings>");
		tdpLoglineProcessor.setErrorsPattern("(<Errors>.{1,}</Errors>|<Error .*/>)");
		tdpLoglineProcessor.setSystem("TDP");
		tdpLoglineProcessor.setActorPattern(".*[.](\\w*?)\\]");
		
		SignalLoglineProcessor sabreLoglineProcessor = new SignalLoglineProcessor();
		sabreLoglineProcessor.setConditionPattern(".*(Sending to Sabre host|Response from Sabre host) :.*");
		sabreLoglineProcessor.setExtractPattern("<.*>$");
		sabreLoglineProcessor.setSignalNamePattern("<eb:Action>(\\w*?)</eb:Action>");
		sabreLoglineProcessor.setWarningsPattern("<Warnings.{1,}</Warnings>");
		sabreLoglineProcessor.setErrorsPattern("<Errors>.*</Errors>");
		sabreLoglineProcessor.setSystem("SABRE");
		sabreLoglineProcessor.setActorPattern("<eb:Service.*>(\\w*?)</eb:Service>");
		
		SignalLoglineProcessor aceLoglineProcessor = new SignalLoglineProcessor();
		aceLoglineProcessor.setConditionPattern(".*(Sending Message to ACE|Received From ACE):.*");
		aceLoglineProcessor.setExtractPattern("<.*>$");
		aceLoglineProcessor.setSignalNamePattern("<ns2:(\\w*?) ");
		aceLoglineProcessor.setSystem("ACE");
		
		ExceptionLoglineProcessor exceptionLoglineProcessor = new ExceptionLoglineProcessor();
		exceptionLoglineProcessor.setConditionPattern(".*\n((\\w*?)[.]){1,20}(\\w*?)(Exception|Error).*");
		exceptionLoglineProcessor.setExceptionNamePattern("\n([\\w.]*?)(\n|:)");
		
		MultiLoglineProcessor astralProcessor = new MultiLoglineProcessor();
		astralProcessor.setConditionPattern(".*(DATA REQUEST START|RECEIVED MESSAGE START).*");
		astralProcessor.setExtractPattern("MatipHandlerTypeAConv] (.*?)$");
		astralProcessor.setRequestPattern(".*DATA REQUEST START.*");
		astralProcessor.setResponsePattern(".*RECEIVED MESSAGE START.*");
		astralProcessor.setEndPattern(".*(DATA REQUEST END|RECEIVED MESSAGE END).*");
		astralProcessor.setSystem("ASTRAL");

		
		tdpLogProcessor.setProcessor(sabreLoglineProcessor);
		tdpLogProcessor.setProcessor(tdpLoglineProcessor);
		tdpLogProcessor.setProcessor(aceLoglineProcessor);
		tdpLogProcessor.setProcessor(exceptionLoglineProcessor);
		tdpLogProcessor.setProcessor(astralProcessor);
		
		try {
			tdpLogProcessor.process("d:\\testlog.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
