package olap.olap.project.model.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import olap.olap.project.model.Dimension;
import olap.olap.project.model.Hierarchy;
import olap.olap.project.model.Level;
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
			createDimensions();
			createFactTable();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xml.generateXml(multiDim, outFileName);
	}

	public Collection<Dimension> getCubeDimensions() {
		System.out.println(multiDim.getCube().getDimensions().values());
		return (Collection<Dimension>) multiDim.getCube().getDimensions()
				.values();
	}

	public List<String> getDBTableNames() throws Exception {
		final Connection conn = connectionManager
				.getConnectionWithCredentials();
		DatabaseMetaData dbmd = conn.getMetaData();
		List<String> names = new ArrayList<String>();
		String[] types = { "TABLE" };
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
		String query = "CREATE TABLE " + multiDim.getCube().getName() + " (\n";
		// -----------------------------------------
		// AGREGO LAS MEASURES

		for (Measure m : multiDim.getCube().getMeasures()) {
			query += m.getName() + " " + m.getType() + " ,\n";
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
					query += s + "_" + p.getName() + " " + p.getType()
							+ " REFERENCES " + d.getName() + "(" + p.getName()
							+ "),\n";
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
					if (!first) {
						query += "," + p.getName();
					} else {
						query += p.getName();
						first = false;
					}

				}
			}
		}
		query += " )\n)";
		// -----------------------------------------
		// PRINTEO LA QUERY

		PreparedStatement statement = conn.prepareStatement(query);

		System.out.println("-----------------------------");

		System.out.println(statement.toString());
		//statement.execute();
		connectionManager.closeConnection(conn);

	}

	private void createDimensions() throws Exception {

		final Connection conn = connectionManager
				.getConnectionWithCredentials();
		boolean first = true;
		Collection<Dimension> dimensions = multiDim.getCube().getDimensions()
				.values();

		Set<Dimension> nonRepeat = new HashSet<Dimension>(dimensions);
		for (Dimension d : nonRepeat) {
			first = true;

			Map<Integer, String> parameters = new HashMap<Integer, String>();
			int key = 1;
			String query = "CREATE TABLE " + d.getName() + " (\n";
			Level level = d.getLevel();
			for (Property p : level.getProperties()) {
				parameters.put(key++, p.getName());
				parameters.put(key++, p.getType());
				query += p.getName() + " " + p.getType() + " , \n";
			}
			for (Hierarchy h : d.getHierarchies()) {
				for (Level l : h.getLevels()) {
					for (Property p : l.getProperties()) {
						query += p.getName() + " " + p.getType() + " , \n";
					}
				}
			}
			query += "PRIMARY KEY( ";
			for (Property p : level.getProperties()) {
				if (p.isPK()) {
					if (!first) {
						query += ", " + p.getName();
					} else {
						query += p.getName();
						first = false;
					}
				}
			}
			query += ") \n)";
			PreparedStatement statement = conn.prepareStatement(query);
			System.out.println("-----------------------------");

			//System.out.println(statement.toString());
			statement.execute();

		}

		connectionManager.closeConnection(conn);
	}
}
