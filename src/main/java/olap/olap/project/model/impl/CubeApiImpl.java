package olap.olap.project.model.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import org.dom4j.Document;
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

	public Document generateMDXAuto(String outFileName) throws IOException {

		XmlConverter xml = new XmlConverter();

		try {

			createFactTable();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xml.generateXml(multiDim, outFileName);
	}

	public List<Dimension> getCubeDimensions() {
		// TODO Auto-generated method stub
		return (ArrayList<Dimension>) multiDim.getCube().getDimensions()
				.values();
	}

	public List<String> getDBTableNames() throws Exception {
		final Connection conn = connectionManager.getConnectionWithCredentials();		
		DatabaseMetaData dbmd = conn.getMetaData();
		List<String> names = new ArrayList<String>();
        String[] types = {"TABLE"};
        ResultSet rs = dbmd.getTables(null, null, "%", types);
        while (rs.next()) {
        	names.add(rs.getString("TABLE_NAME"));
        }
		return names;
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
			parameters.put(key++, m.getType());
			query += "? ? ,\n";
		}
		// -----------------------------------------
		// AGREGO LAS DIMENSIONES Y LAS FOREIGN KEYS
		// Collection<Dimension> dimensions = multiDim.getCube().getDimensions()
		// .values();
		Set<String> dimensiones = multiDim.getCube().getDimensions().keySet();
		for (String s : dimensiones) {
			Dimension d = multiDim.getCube().getDimensions().get(s);
			Set<Property> properties = d.getLevel().getProperties();
			for (Property p : properties) {
				if (p.isPK()) {
					parameters.put(key++, s + "_" + p.getName());
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
		for (String s : dimensiones) {
			Dimension d = multiDim.getCube().getDimensions().get(s);
			Set<Property> properties = d.getLevel().getProperties();
			for (Property p : properties) {
				if (p.isPK()) {
					parameters.put(key++, s + "_" + p.getName());
					if (!first) {
						query += ",?";
					} else {
						query += "?";
						first = false;
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

		System.out.println("-----------------------------");

		System.out.println(statement.toString());
		// statement.execute();
		connectionManager.closeConnection(conn);

	}

	private void createDimensions() {

	}
}
