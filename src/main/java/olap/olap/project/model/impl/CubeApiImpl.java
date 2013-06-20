package olap.olap.project.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

import olap.olap.project.model.Cube;
import olap.olap.project.model.Dimension;
import olap.olap.project.model.MultiDim;
import olap.olap.project.model.api.CubeApi;
import olap.olap.project.xml.XmlConverter;

public class CubeApiImpl implements CubeApi {

	Cube cube;
	MultiDim multiDim;

	public CubeApiImpl() {
	}

	public boolean setDBCredentials(String dbUrl, String name, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public void loadMultildimXml(String xmlfile) throws DocumentException,
			IOException {

		XmlConverter xml = new XmlConverter();
		multiDim = xml.parse(new File(xmlfile));
	}

	public void generateMDXAuto(String outFileName) throws IOException {

		XmlConverter xml = new XmlConverter();
		xml.generateXml(multiDim, outFileName);
	}

	public List<Dimension> getCubeDimensions() {
		// TODO Auto-generated method stub
		return (ArrayList<Dimension>) cube.getDimensions().values();
	}

	public List<String> getDBTableNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean linkDimension(Dimension cubeDim, String dbTableName) {
		// TODO Auto-generated method stub
		return false;
	}

	public void generateMDXManual(String outFileName) {
		// TODO Auto-generated method stub
	}

}
