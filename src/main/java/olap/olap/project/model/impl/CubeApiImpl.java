package olap.olap.project.model.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import olap.olap.project.model.Dimension;
import olap.olap.project.model.Measure;
import olap.olap.project.model.MultiDim;
import olap.olap.project.model.Property;
import olap.olap.project.model.api.CubeApi;
import olap.olap.project.model.db.ConnectionManager;
import olap.olap.project.model.db.ConnectionManagerPostgreWithCredentials;
import olap.olap.project.xml.XmlConverter;

import org.dom4j.DocumentException;

public class CubeApiImpl implements CubeApi {

	MultiDim multiDim;
	ConnectionManager connectionManager;

	public CubeApiImpl() {
	}

	public boolean setDBCredentials(String dbUrl, String name, String password) {
		connectionManager = ConnectionManagerPostgreWithCredentials
				.setConnectionManagerWithCredentials(dbUrl, name, password);

		return false;
	}

	public void loadMultildimXml(File xmlfile) throws DocumentException,
			IOException {
		XmlConverter parser = new XmlConverter();
		multiDim = parser.parse(xmlfile);
		// for debugging
		multiDim.print();
	}

	public void generateMDXAuto(String outFileName) throws IOException {

		XmlConverter xml = new XmlConverter();
		xml.generateXml(multiDim, outFileName);

		try {
			 createFactTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Dimension> getCubeDimensions() {
		// TODO Auto-generated method stub
		return (ArrayList<Dimension>) multiDim.getCube().getDimensions()
				.values();
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

	private void createFactTable() throws Exception {

		final Connection conn = connectionManager
				.getConnectionWithCredentials();

		Map<Integer, String> parameters = new HashMap<Integer, String>();
		int key = 1;
		// -----------------------------------------
		// PREPARO EL STATEMENT
		String query = "CREATE TABLE ? { \n";
		parameters.put(key++, multiDim.getCube().getName());
		// -----------------------------------------
		// AGREGO LAS MEASURES

		for (Measure m : multiDim.getCube().getMeasures()) {
			parameters.put(key++, m.getName());
			parameters.put(key, m.getType());
			query += "? ? ,\n";
		}
		// -----------------------------------------
		// AGREGO LAS DIMENSIONES Y LAS FOREIGN KEYS
		ArrayList<Dimension> dimensions = (ArrayList<Dimension>) multiDim
				.getCube().getDimensions().values();
		for (Dimension d : dimensions) {
			Set<Property> properties = d.getLevel().getProperties();
			for (Property p : properties) {
				if (p.isPK()) {
					parameters.put(key++, d.getName() + "_" + p.getName());
					parameters.put(key++, p.getType());
					parameters.put(key++, d.getName());
					parameters.put(key++, p.getName());
					query += "? ? REFERENCES ?(?),\n";
				}
			}
		}
		// -----------------------------------------
		// AGREGO LAS PRIMARY KEYS
		query += "PRIMARY KEY (";
		boolean first = true;
		for (Dimension d : dimensions) {
			Set<Property> properties = d.getLevel().getProperties();
			for (Property p : properties) {
				if (p.isPK()) {
					parameters.put(key++, p.getName());
					if (!first) {
						query += ",?";
					} else {
						query += "?";
					}

				}
			}
		}
		query += " );\n";
		// -----------------------------------------
		// PRINTEO LA QUERY
		System.out.println(query);
		for (int i = 1; i < key; i++) {
			System.out.println(i + ": " + parameters.get(i));
		}
		PreparedStatement statement = conn.prepareStatement(query,
				PreparedStatement.RETURN_GENERATED_KEYS);

		for (int i = 1; i < key; i++) {
			statement.setString(i, parameters.get(i));
		}
		//statement.execute();
		connectionManager.closeConnection(conn);

	}

	private void createDimensions() {

	}
}
