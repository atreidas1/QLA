package qla.modules.loganalyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import qla.modules.confuguration.AppConfiguration;
import qla.modules.loganalyser.models.SignalModel;
import qla.modules.prettyprinter.XmlPrettyPrinter;

public class LogAnalyseUtils {
	
	public static SignalModel getSignalModel(int id, String parsedFile) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(parsedFile));
		while(hasNextSignalModel(scanner)) {
			SignalModel signalModel = readNextSignalModel(scanner);
			if(signalModel.getId() == id) {
				scanner.close();
				return signalModel;
			}
		}
		return null;
	}

	private static SignalModel readNextSignalModel(Scanner scanner) {
		SignalModel signalModel = new SignalModel();
		signalModel.setId(Integer.parseInt(scanner.nextLine()));
		signalModel.setType(scanner.nextLine());
		signalModel.setServantName(scanner.nextLine());
		signalModel.setRqRsName(scanner.nextLine());
		String request = scanner.nextLine();
		if(!request.isEmpty()) {
			signalModel.setSource(XmlPrettyPrinter.prettyFormat(request));
		}
		String errors = scanner.nextLine();;
		if(!errors.isEmpty()) {
			signalModel.setErrors(XmlPrettyPrinter.prettyFormat(errors));
		}
		String warnings = scanner.nextLine();
		if(!warnings.isEmpty()) {
			signalModel.setWarnings(XmlPrettyPrinter.prettyFormat(warnings));
		}
		return signalModel;
	}

	private static boolean hasNextSignalModel(Scanner scanner) {
		return scanner.hasNextLine();
	}
	
	public static String getRequestName(String rqRs) {
		return rqRs.substring(1,rqRs.indexOf(' '));
	}
	
	public static String getErrors(String requestOrResponse) {
		return getPartOfStringByPattern("<Errors>.{1,}</Errors>", requestOrResponse);
	}
	
	public static String getTDPWarnings(String requestOrResponse) {
		return getPartOfStringByPattern("<ns1:Warnings.{1,}</ns1:Warnings>", requestOrResponse);
	}
	
	public static String getServantName(String line) {
		Pattern pattern = Pattern.compile(AppConfiguration.getProperty("parser.patternForExtractServantName"));
		Matcher matcher = pattern.matcher(line);
		matcher.find();
		String fullServantName = matcher.group(0);
		System.out.println(fullServantName);
		return fullServantName.substring(fullServantName.lastIndexOf(".") + 1, fullServantName.length());
	}
	
	public static String getSabreWarnings(String requestOrResponse) {
		return getPartOfStringByPattern("<Warnings.{1,}</Warnings>", requestOrResponse);
	}
	
	public static File writeSignalsToFile(String pathToFile, List<qla.modules.loganalyser.models.SignalModel> requestsAndResponses) throws FileNotFoundException {
		File file = new File(pathToFile);
		PrintWriter printWriter = new PrintWriter(file);
		Iterator<SignalModel> iterator = requestsAndResponses.iterator();
		while (iterator.hasNext()) {
			SignalModel rqRsModel =  iterator.next();
			printWriter.print(rqRsModel.toString());
		}
		printWriter.close();
		return file;
	}
	
	public static String getSabreActionName(String sabreRqRs) {
		String actionName = getPartOfStringByPattern("<eb:Action>.{1,}</eb:Action>", sabreRqRs);
		actionName = actionName.replace("<eb:Action>", "").replace("</eb:Action>", "");
		return actionName;
	}
	
	public static String getPartOfStringByPattern(String searchPattern, String string){
		String partOfString = "";
		Pattern pattern = Pattern.compile(searchPattern);
		Matcher matcher = pattern.matcher(string);
		if(matcher.find()){
			partOfString = matcher.group();
		}
		return partOfString;
	}
	
	public static List<String> getPartsOfStringByPattern(String searchPattern, String string) {
		List<String> partsOfString = new ArrayList<>();
		Pattern pattern = Pattern.compile(searchPattern);
		Matcher matcher = pattern.matcher(string);
		while(matcher.find()){
			partsOfString.add(matcher.group());
		}
		return partsOfString;
	}
	/**
	 * Define thread name by given jsession id.
	 * @param pathToFile
	 * @param jsessionId
	 * @return Thread Name or empty string if thread name didn't find by jsession id
	 * @throws IOException 
	 */
	@SuppressWarnings("resource")
	public static String defineThreadName(String pathToFile, String jsessionId) throws IOException {
		String threadName = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(pathToFile), "UTF8"));
		String line;
		while ((line=br.readLine()) != null) {
			if(line.contains(AppConfiguration.getProperty("parser.patternForIncomingRequest"))){
				String assumeThreadName = line.split(" ")[3];
				assumeThreadName = assumeThreadName.substring(1, assumeThreadName.length()-1);
				String RqOrRs = (line=br.readLine())!=null ? line : "";
				if(RqOrRs.contains(jsessionId)){
					threadName = assumeThreadName;
					br.close();
					return threadName;
				}
			}
			
		}
		return threadName;
	}
}
