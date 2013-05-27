package olap.olap.project.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlValidator {

	
	public static void validate(String xml) throws Exception{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setValidating(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		builder.setErrorHandler(new ErrorHandler() {
		    public void error(SAXParseException exception) throws SAXException {
		        // do something more useful in each of these handlers
		        exception.printStackTrace();
		    }
		    public void fatalError(SAXParseException exception) throws SAXException {
		        exception.printStackTrace();
		    }

		    public void warning(SAXParseException exception) throws SAXException {
		        exception.printStackTrace();
		    }
		});
		builder.parse(xml);
	}
}
