package olap.olap.project.model.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import olap.olap.project.model.Dimension;
import olap.olap.project.model.Hierarchy;
import olap.olap.project.model.Level;
import olap.olap.project.model.Measure;
import olap.olap.project.model.MultiDim;
import olap.olap.project.model.Property;
import olap.olap.project.model.SQLAttribute;
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
	
	public List<String> getDBFieldsForTable(String table) throws Exception {
		String q = "select * from " + table;
		List<String> ret = new ArrayList<String>();
		ResultSet rs;
		int columnCount = 0;
		Connection conn;
		try {
			conn= connectionManager
					.getConnectionWithCredentials();
			Statement st = conn.createStatement();
			rs = st.executeQuery(q);
			ResultSetMetaData rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();;
			for(int i=1; i <= columnCount; i++){
				ret.add(rs.getMetaData().getColumnName(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public List<String> getPropertiesForDimension(String table) throws Exception{
		 Collection<Dimension> dimensions = multiDim.getCube().getDimensions().values();
		 Iterator<Dimension> it = dimensions.iterator();
		 while(it.hasNext()){
			 Dimension d = it.next();
			 if(d.getName().equals(table)){
				 return d.getPropertyNames();
			 }
		 }
		 return null;
	}
	
	public boolean linkDimension(String cubeDim, String dbTableName) {
		int columnCount = getTableFieldsCount(dbTableName);
		return multiDim.getCube().changeDimensionName(cubeDim, dbTableName,columnCount);
	}
	
	private int getTableFieldsCount(String dbTableName){
		String q = "select * from " + dbTableName;
		ResultSet rs;
		int columnCount = 0;
		Connection conn;
		try {
			conn= connectionManager
					.getConnectionWithCredentials();
			Statement st = conn.createStatement();
			rs = st.executeQuery(q);
			ResultSetMetaData rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnCount;
	}
	

	public void generateMDXManual(String outFileName) {
		// TODO Auto-generated method stub
	}

	private void createFactTable() throws Exception {

		final Connection conn = connectionManager
				.getConnectionWithCredentials();

		// Map<Integer, String> parameters = new HashMap<Integer, String>();
		// int key = 1;
		// -----------------------------------------
		// PREPARO EL STATEMENT
		String query = "CREATE TABLE " + multiDim.getCube().getName() + "_fact" + " (\n";
		// -----------------------------------------
		// AGREGO LAS MEASURES

		for (Measure m : multiDim.getCube().getMeasures()) {
			String type = SQLAttribute.valueOf(m.getType().toUpperCase())
					.toString();
			query += m.getName() + " " + type + " ,\n";
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
					String type = SQLAttribute.valueOf(
							p.getType().toUpperCase()).toString();
					query += s + "_" + p.getName() + " " + type
							+ " REFERENCES " + d.getName() + "("
							+ d.getLevel().getName() + "_" + p.getName()
							+ ") ON DELETE CASCADE ,\n";
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
						query += "," + s + "_" + p.getName();
					} else {
						query += s + "_" + p.getName();
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
		statement.execute();
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

			String query = "CREATE TABLE " + d.getName() + " (\n";
			Level level = d.getLevel();
			for (Property p : level.getProperties()) {
				String type = SQLAttribute.valueOf(p.getType().toUpperCase())
						.toString();
				query += level.getName() + "_" + p.getName() + " " + type;
				if (p.isPK())
					query += " UNIQUE";
				query += " , \n";

			}
			for (Hierarchy h : d.getHierarchies()) {
				for (Level l : h.getLevels()) {
					for (Property p : l.getProperties()) {
						String type = SQLAttribute.valueOf(
								p.getType().toUpperCase()).toString();
						query += l.getName() + "_" + p.getName() + " " + type
								+ " , \n";
					}
				}
			}
			query += "PRIMARY KEY( ";
			for (Property p : level.getProperties()) {
				if (p.isPK()) {
					if (!first) {
						query += ", " + level.getName() + "_" + p.getName();
					} else {
						query += level.getName() + "_" + p.getName();
						first = false;
					}
				}
			}
			query += ") \n)";
			PreparedStatement statement = conn.prepareStatement(query);
			System.out.println("-----------------------------");

			System.out.println(statement.toString());
			statement.execute();

		}

		connectionManager.closeConnection(conn);
	}
	
	public boolean changePropertyName(String tableName, String propName, String fieldName){
		System.out.println(tableName);
		Dimension dim = multiDim.getDimensionValue(tableName);
		/*tiene que chequar que el tipo sea correcto*/
		return dim.changePropertyName(propName, fieldName);
	}
	
}