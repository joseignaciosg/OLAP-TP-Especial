package olap.olap.project.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import olap.olap.project.model.Attribute;
import olap.olap.project.model.Cube;
import olap.olap.project.model.Dimension;
import olap.olap.project.model.Hierarchy;
import olap.olap.project.model.Level;
import olap.olap.project.model.Measure;
import olap.olap.project.model.MultiDim;
import olap.olap.project.model.Property;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

@SuppressWarnings("unchecked")
public class XmlConverter {

	/**
	 * Converts a xmlFile to a MultiDim
	 */
	public MultiDim parse(File xml) throws DocumentException, IOException {
		MultiDim multiDim = new MultiDim();
		SAXReader reader = new SAXReader();
		Document in = reader.read(xml);
		Element multidim = in.getRootElement();
		Iterator<Element> i = multidim.elementIterator();
		Element cubeElement = null;
		while (i.hasNext()) {
			Element e = i.next();
			// System.out.println(e.getName());
			if (e.getName().equals("cubo")) {
				cubeElement = e;
			} else if (e.getName().equals("dimension")) {
				parseDimension(multiDim, e);
			} else {
				throw new RuntimeException("invalid " + e.getName()
						+ " dimension or cube tags only accepted");
			}
		}
		parseCube(multiDim, cubeElement);
		// multiDim.print();
		return multiDim;
	}

	/**
	 * Converts a MultiDim to a GeoMondrian XML Hay que leer bien
	 * http://mondrian.pentaho.com/documentation/schema.php#Star_schemas creo q
	 * no hice las cosas muy bien igual esta x la mitad
	 */

	public Document generateXml(MultiDim multiDim, String fileName)
			throws IOException {

		Document out = DocumentHelper.createDocument();
		out = out.addDocType("Schema", null, "mondrian.dtd");
		Element schema = out.addElement("Schema");
		schema.addAttribute("name", multiDim.getCube().getName());
		Element cubeElem = schema.addElement("Cube");
		cubeElem.addAttribute("name", multiDim.getCube().getName());
		cubeElem.addAttribute("cache", "true");
		Cube cube = multiDim.getCube();
		for (Measure m : cube.getMeasures()) {
			Element measure = cubeElem.addElement("Measure");
			measure.addAttribute("aggregator", m.getAgg());
			measure.addAttribute("name", m.getName());
			measure.addAttribute("datatype",
					Attribute.valueOf(m.getType().toUpperCase()).toString());
		}

		for (Entry<String, Dimension> entry : multiDim.getCube()
				.getDimensions().entrySet()) {
			Element dim = cubeElem.addElement("Dimension");
			Dimension dimension = entry.getValue();
			dim.addAttribute("name", entry.getKey());
			if (dimension.getName().equals("temporal")) {
				dim.addAttribute("type", "TimeDimension");
			}
			for (Hierarchy h : dimension.getHierarchies()) {
				handleHierarchy(dim, h, entry.getKey());
			}
		}


//		XMLWriter writer = new XMLWriter(new FileWriter(fileName));
//		writer.write(out);
//		writer.close();


		return out;
	}

	private void handleHierarchy(Element dim, Hierarchy h, String dimName) {
		Element hierarchy = dim.addElement("Hierarchy");
		hierarchy.addAttribute("hasAll", "true");
		hierarchy.addAttribute("name", h.getName());
		Element table = hierarchy.addElement("table");
		table.addAttribute("name",dimName);
		for (Level l : h.getLevels()) {
			handleLevel(hierarchy, l);
		}
	}

	private void handleLevel(Element hierarchy, Level l) {
		for (Property p : l.getProperties()) {
			Element level = hierarchy.addElement("Level");
			level.addAttribute("name", l.getName() + "-" + p.getName());
			level.addAttribute("column", l.getName() + "-" + p.getName());
			level.addAttribute("type", Attribute.valueOf(p.getType().toUpperCase()).toString());
		}
	}

	private void parseCube(MultiDim multiDim, Element c) {
		Cube cube = new Cube(c.attributeValue("name"));
		Iterator<Element> i = c.elementIterator();
		while (i.hasNext()) {
			Element e = i.next();
			if (e.getName().equals("measure")) {
				cube.addMeasure(new Measure(e.attributeValue("name"), e
						.attributeValue("type"), e.attributeValue("agg")));
			} else if (e.getName().equals("dimension")) {
				String ptr = e.attributeValue("ptr");
				Dimension dim = multiDim.getDimension(ptr);
				if (dim == null) {
					throw new RuntimeException("No dimension " + ptr
							+ " was found");
				}
				System.out.println(e.attributeValue("name"));
				cube.addDimension(e.attributeValue("name"), dim);
			} else {
				throw new RuntimeException("invalid " + e.getName()
						+ " measure or dimension tags only accepted");
			}
		}
		multiDim.setCube(cube);
	}

	private void parseDimension(MultiDim multiDim, Element dimension) {
		// System.out.println(dimension.attributeValue("name"));
		Dimension dim = new Dimension(dimension.attributeValue("name"));
		Iterator<Element> i = dimension.elementIterator();
		while (i.hasNext()) {
			Element e = i.next();
			if (e.getName().equals("level")) {
				Level level = new Level(dim.getName(), 0);
				parseProperties(level, e);
				dim.setLevel(level);
			} else if (e.getName().equals("hierarchy")) {
				pasreHierarchy(dim, e);
			} else {
				throw new RuntimeException("invalid tag '" + e.getName()
						+ "' level or hierarchy tags only accepted");
			}
		}
		multiDim.addDimension(dim);
	}

	private void parseProperties(Level level, Element levelElem) {
		Iterator<Element> i = levelElem.elementIterator();
		while (i.hasNext()) {
			Element prop = i.next();
			boolean id;
			if (prop.attribute("ID") != null) {
				id = prop.attributeValue("ID").equals("true");
			} else {
				id = false;
			}
			Property property = new Property(prop.getText().replaceAll("\\s",
					""), prop.attributeValue("type"), id);
			level.addProperty(property);
		}
	}

	private void pasreHierarchy(Dimension dim, Element h) {
		Hierarchy hierachy = new Hierarchy(h.attributeValue("name"));
		Iterator<Element> i = h.elementIterator();
		while (i.hasNext()) {
			Element l = i.next();
			Level level = new Level(l.attributeValue("name"), Integer.valueOf(l
					.attributeValue("pos")));
			parseProperties(level, l);
			hierachy.addLevel(level);
		}
		dim.addHierarchy(hierachy);
	}

	public static void main(String[] args) throws DocumentException,
			IOException {
		XmlConverter xml = new XmlConverter();
		MultiDim multiDim = xml.parse(new File("in/in.xml"));
		xml.generateXml(multiDim, "out/output3.xml");
	}
}
