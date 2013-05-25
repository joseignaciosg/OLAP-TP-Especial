package olap.olap.project.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlConverter {

	public Document parse(File xml) throws DocumentException, IOException {
		SAXReader reader = new SAXReader();
		Document in = reader.read(xml);
		Element multidim = in.getRootElement();
		Iterator<Element> i = multidim.elementIterator();
		while (i.hasNext()) {
            Element dimension = i.next();
            if (dimension.getName().equals("cubo")) {
            	String name = dimension.attributeValue("name");
//            	parseDimension(dimension);
            } else {
//            	parseCube(dimension);
            }
        }
		
		
		Document out = DocumentHelper.createDocument();

		Element schema = out.addElement("Schema");
		Element cube = schema.addElement("Cube");
		Element table = schema.addElement("Table");

		XMLWriter writer = new XMLWriter(
				new FileWriter( "output.xml" )
				);
		writer.write(out);
		writer.close();
		return out;
	}

	public static void main(String[] args) throws DocumentException, IOException {
		XmlConverter xml = new XmlConverter();
		xml.parse(new File("in.xml"));
	}
}
