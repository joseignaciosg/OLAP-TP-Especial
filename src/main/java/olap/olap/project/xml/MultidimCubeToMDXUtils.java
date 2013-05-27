package olap.olap.project.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import olap.olap.project.model.Cube;

public class MultidimCubeToMDXUtils {
	
	private Cube cube;
	
	public MultidimCubeToMDXUtils(Cube cube) {
		this.cube = cube;
	}
	
	public static OutputStream convertToMDX(Cube cube) throws FileNotFoundException, UnsupportedEncodingException, XMLStreamException, FactoryConfigurationError{
		
		OutputStream outputStream = new FileOutputStream(new File("doc.xml"));
		XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(
				new OutputStreamWriter(outputStream, "utf-8"));
		
		
		
		return outputStream;
	}

}
