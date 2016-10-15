package qla.modules.prettyprinter;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XML11Serializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * Helper class for formatting XML.
 * @author Atreidas
 *
 */
public class XmlPrettyPrinter {
	
	/**
	 * Formatting XML string with given indent.
	 * @param input - unformatted XML string.
	 * @param indent - indent.
	 * @return formatted XML string.
	 */
	public static String prettyFormat(String input, int indent) {
		 String formattedString = null;
	     try {
	         final InputSource src = new InputSource(new StringReader(input));
	         final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();

	         // the last parameter sets indenting/pretty-printing to true:
	         OutputFormat outputFormat = new OutputFormat("WHATEVER", "UTF-8", true);
	         outputFormat.setIndent(indent);
	         // line width = 0 means no line wrapping:
	         outputFormat.setLineWidth(0);
	         outputFormat.setOmitXMLDeclaration(true);
	         StringWriter sw = new StringWriter();
	         XML11Serializer writer = new XML11Serializer(sw, outputFormat);
	         writer.serialize((Element)document);
	         formattedString = sw.toString(); 
	         return formattedString;
	     } catch (Exception e) {
	         e.printStackTrace();
	         return input;
	     }
		
	}
	/**
	 * Formatting XML string with indent 2.
	 * @param input - unformatted XML string.
	 * @return formatted XML string.
	 */
	public static String prettyFormat(String input) {
	    return prettyFormat(input, 2);
	}
}
