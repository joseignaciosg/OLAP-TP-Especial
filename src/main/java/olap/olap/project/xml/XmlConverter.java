package olap.olap.project.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import olap.olap.project.model.*;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

@SuppressWarnings("unchecked")
public class XmlConverter {

	MultiDim multiDim = new MultiDim();
	
	public Document parse(File xml) throws DocumentException, IOException {
		SAXReader reader = new SAXReader();
		Document in = reader.read(xml);
		Cube cube = null;
		Element multidim = in.getRootElement();
		Iterator<Element> i = multidim.elementIterator();
		Element cubeElement = null;
		while (i.hasNext()) {
            Element e = i.next();
            if (e.getName().equals("cubo")) {
            	cubeElement = e;
            } else if (e.getName().equals("dimension")){
            	parseDimension(e);
            } else {
            	throw new RuntimeException("invalid " + e.getName() +" dimension or cube tags only accepted");
            }
        }
		cube = parseCube(cubeElement);
		cube.print();
//		multiDim.print();
		
		Document out = DocumentHelper.createDocument();

		Element schema = out.addElement("Schema");
//		Element cube = schema.addElement("Cube");
		Element table = schema.addElement("Table");

		XMLWriter writer = new XMLWriter(
				new FileWriter( "output.xml" )
				);
		writer.write(out);
		writer.close();
		return out;
	}
	
	private Cube parseCube(Element c) {
		Cube cube = new Cube(c.attributeValue("name"));
		Iterator<Element> i = c.elementIterator();
		while (i.hasNext()) {
			Element e = i.next();
			if (e.getName().equals("measure")) {
				cube.addMeasure(new Measure(e.attributeValue("name"), e.attributeValue("type"), e.attributeValue("agg")));
			} else if(e.getName().equals("dimension")) {
				String ptr = e.attributeValue("ptr");
				Dimension dim = multiDim.getDimension(ptr);
				if (dim == null) {
	            	throw new RuntimeException("No dimension "+ptr+" was found");
				}
				cube.addDimension(e.attributeValue("name"), dim);
			} else {
            	throw new RuntimeException("invalid " + e.getName() +" measure or dimension tags only accepted");
            }
		}
		return cube;
	}
	
	private void parseDimension(Element dimension) {
		Dimension dim = new Dimension(dimension.attributeValue("name"));
		Iterator<Element> i = dimension.elementIterator();
		while(i.hasNext()) {
			Element e = i.next();
			if(e.getName().equals("level")) {
				parseProperties(dim, e);
			} else if(e.getName().equals("hierarchy")) {
				pasreHierarchy(dim, e);
			} else {
            	throw new RuntimeException("invalid tag '" + e.getName() +"' level or hierarchy tags only accepted");
            }
		}
		multiDim.addDimension(dim);
	}

	private void parseProperties(PropertyHolder ph, Element level) {
		Iterator<Element> i = level.elementIterator();
		while(i.hasNext()) {
			Element prop = i.next();
			boolean id;
			if (prop.attribute("ID") != null) {
				id = prop.attributeValue("ID").equals("true");
			} else {
				id = false;
			}
			Property property = new Property(prop.getText(), prop.attributeValue("type"), id);
			ph.addProperty(property);
		}
	}
	
	private void pasreHierarchy(Dimension dim, Element h) {
		Hierarchy hierachy = new Hierarchy(h.attributeValue("name"));
		Iterator<Element> i = h.elementIterator();
		while(i.hasNext()) {
			Element l = i.next();
			Level level = new Level(l.attributeValue("name"), Integer.valueOf(l.attributeValue("pos")));
			parseProperties(level, l);
			hierachy.addLevel(level);
		}
		dim.addHierarchy(hierachy);
	}
	
	public static void main(String[] args) throws DocumentException, IOException {
		XmlConverter xml = new XmlConverter();
		xml.parse(new File("in2.xml"));
	}
}
