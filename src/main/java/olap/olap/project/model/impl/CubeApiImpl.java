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
import java.util.Map;
import java.util.Set;

import olap.olap.project.model.Cube;
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
	private String factTableName; /*
								 * el nombre de la fact table en la base de
								 * datos
								 */

	public CubeApiImpl() {
		this.factTableName = null;
	}

	public String getFactTableName() {
		return this.factTableName;
	}

	public boolean setFactTableName(String name) {
		int propCount = getFactTablePropertyCount();
		int fieldCount = getTableFieldsCount(name);
		if (propCount != fieldCount) {
			return false;
		}
		this.factTableName = name;
		this.multiDim.getCube().setName(name);
		return true;
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

	public Document generateMDXAuto(String outFileName) throws Exception {

		XmlConverter xml = new XmlConverter();
		Connection conn = null;
		try {
			conn = connectionManager.getConnectionWithCredentials();
			conn.setAutoCommit(false);
			createDimensions(conn);
			createFactTable(conn);

		} catch (Exception e) {
			conn.rollback();
		}
		if (!conn.isClosed())
			conn.commit();
		connectionManager.closeConnection(conn);
		return xml.generateXml(multiDim, outFileName,true);
	}

	public Collection<Dimension> getCubeDimensions() {
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
			conn = connectionManager.getConnectionWithCredentials();
			Statement st = conn.createStatement();
			rs = st.executeQuery(q);
			ResultSetMetaData rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
			;
			for (int i = 1; i <= columnCount; i++) {
				ret.add(rs.getMetaData().getColumnName(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public List<String> getPropertiesForDimension(String table)
			throws Exception {
		Collection<Dimension> dimensions = multiDim.getCube().getDimensions()
				.values();
		Iterator<Dimension> it = dimensions.iterator();
		while (it.hasNext()) {
			Dimension d = it.next();
			if (d.getName().equals(table)) {
				return d.getPropertyNames();
			}
		}
		return null;
	}

	public int getFactTablePropertyCount() {
		Cube cube = multiDim.getCube();
		return cube.getMeasures().size() + cube.getDimensions().size();
	}

	public List<String> getFactTableProperties() {
		Cube cube = multiDim.getCube();
		List<String> ret = new ArrayList<String>();
		Iterator<Measure> it = cube.getMeasures().iterator();
		while (it.hasNext()) {
			Measure m = it.next();
			ret.add(m.getName());
		}
		Iterator<String> it2 = cube.getDimensions().keySet().iterator();
		while (it2.hasNext()) {
			String dim = it2.next();
			ret.add(dim);
		}
		return ret;
	}

	public boolean changeFactTablePropertyName(String propName, String fieldName) {
		/* ya se tiene que haber likeado una fact table de la DB */
		if (factTableName == null) {
			return false;
		}
		Cube cube = multiDim.getCube();
		Iterator<Measure> it = cube.getMeasures().iterator();
		while (it.hasNext()) {
			Measure m = it.next();
			if (m.getName().equals(propName)) {
				String mtype = SQLAttribute.valueOf(m.getType().toUpperCase())
						.toString().toLowerCase();
				String ftype = getFieldType(factTableName, fieldName);
				if (ftype.equals("int4")) {
					ftype = "integer";
				}
				
				
				if (mtype.equals(ftype)) {
					m.setName(fieldName);
					return true;
				} else {
					return false;
				}
			}
		}
		Iterator<String> it2 = cube.getDimensions().keySet().iterator();
		while (it2.hasNext()) {
			String dimname = it2.next();
			if (dimname.equals(propName)) {
				String ftype = getFieldType(factTableName, fieldName);
				Dimension obj = cube.getDimensions().remove(propName);
				cube.getDimensions().put(fieldName, obj);
				return true;
			}

		}
		return true;
	}

	public boolean linkDimension(String cubeDim, String dbTableName) {
		int columnCount = getTableFieldsCount(dbTableName);
		return multiDim.getCube().changeDimensionName(cubeDim, dbTableName,
				columnCount);
	}

	private int getTableFieldsCount(String dbTableName) {
		String q = "select * from " + dbTableName;
		ResultSet rs;
		int columnCount = 0;
		Connection conn;
		try {
			conn = connectionManager.getConnectionWithCredentials();
			Statement st = conn.createStatement();
			rs = st.executeQuery(q);
			ResultSetMetaData rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnCount;
	}

	public Document generateMDXManual(String outFileName) throws IOException {
		XmlConverter xml = new XmlConverter();
		return xml.generateXml(multiDim, outFileName,false);
	}

	private void createFactTable(Connection conn) throws Exception {

		// Map<Integer, String> parameters = new HashMap<Integer, String>();
		// int key = 1;
		// -----------------------------------------
		// PREPARO EL STATEMENT
		String query = "CREATE TABLE " + multiDim.getCube().getName() + " (\n";
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
			boolean firstKey = true;
			Dimension d = multiDim.getCube().getDimensions().get(s);
			Set<Property> properties = d.getLevel().getProperties();
			for (Property p : properties) {
				if (p.isPK() && firstKey) {
					String type = SQLAttribute.valueOf(
							p.getType().toUpperCase()).toString();
					query += s + "_" + p.getName() + " " + type
							+ " REFERENCES " + d.getName() + "("
							+ d.getLevel().getName() + "_" + p.getName()
							+ ") ON DELETE CASCADE ,\n";
					firstKey = false;
				}
			}
		}
		// -----------------------------------------
		// AGREGO LAS PRIMARY KEYS
		query += "PRIMARY KEY (";
		boolean first = true;
		for (String s : dimensiones) {
			boolean firstKey = true;
			Dimension d = multiDim.getCube().getDimensions().get(s);
			Set<Property> properties = d.getLevel().getProperties();

			for (Property p : properties) {
				if (p.isPK() && firstKey) {
					if (!first) {
						query += "," + s + "_" + p.getName();
					} else {
						query += s + "_" + p.getName();
						first = false;
					}
					firstKey = false;
				}
			}
		}
		query += " )\n)";
		// -----------------------------------------
		// PRINTEO LA QUERY

		PreparedStatement statement = conn.prepareStatement(query);

		
		statement.execute();

	}

	private void createDimensions(Connection conn) throws Exception {

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
				// if (p.isPK())
				// query += " UNIQUE";
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
			boolean firstKey = true;
			for (Property p : level.getProperties()) {
				if (p.isPK() && firstKey) {
					if (!first) {
						query += ", " + level.getName() + "_" + p.getName();
					} else {
						query += level.getName() + "_" + p.getName();
						first = false;
					}
					firstKey = false;
				}
			}
			query += ") \n)";
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.execute();

		}
	}

	public boolean changePropertyName(String tableName, String propName,
			String fieldName) {
		
		Dimension dim = multiDim.getDimensionValue(tableName);
		String fieldType = getFieldType(tableName, fieldName);
		/* tiene que chequar que el tipo sea correcto */
		return dim.changePropertyName(propName, fieldName, fieldType);
	}

	private String getFieldType(String tableName, String fieldName) {
		String type = null;
		String q = "select * from " + tableName;
		ResultSet rs;
		int columnCount = 0;
		Connection conn;
		try {
			conn = connectionManager.getConnectionWithCredentials();
			Statement st = conn.createStatement();
			rs = st.executeQuery(q);
			ResultSetMetaData rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
			;
			for (int i = 1; i <= columnCount; i++) {
				if (fieldName.equals(rsmd.getColumnName(i))) {
					type = rsmd.getColumnTypeName(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}

}