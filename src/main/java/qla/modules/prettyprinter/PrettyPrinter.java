package qla.modules.prettyprinter;

public class PrettyPrinter {
	public static final String CONTENT_TYPE_XML = "XML";
	public static final String CONTENT_TYPE_JSON = "JSON";
	
	
	public static String prettyPrint(String string, String contentType){
		switch (contentType) {
		case CONTENT_TYPE_XML:
			return XmlPrettyPrinter.prettyFormat(string);
		case CONTENT_TYPE_JSON:
			return JsonPrettyPrinter.prettyPrint(string);
		default:
			break;
		}
		return string;
	}
}
