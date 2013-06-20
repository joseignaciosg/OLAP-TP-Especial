package olap.olap.project.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import olap.olap.project.model.Cube;
import olap.olap.project.model.Dimension;
import olap.olap.project.model.Measure;
import olap.olap.project.model.Property;

public class TableCreator {

	public TableCreator(String url, String user, String password, Cube cube)
			throws Exception {

		PreparedStatement statement;
		final ConnectionManager connectionManager = ConnectionManagerPostgreWithCredentials
				.setConnectionManagerWithCredentials(url, user, password);
		final Connection conn = connectionManager
				.getConnectionWithCredentials();
		System.out.println(connectionManager.toString());

		statement = conn.prepareStatement("INSERT INTO NOTIFICATIONS("
				+ "user_from, user_to, read, type) VALUES (?, ?, ?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS);
	
		connectionManager.closeConnection(conn);
	}

	// public String createFactTable(Cube cube) {
	// Map<Integer, String> parameters = new HashMap<Integer, String>();
	// int key = 1;
	// // PREPARO EL STATEMENT
	// String query = "CREATE TABLE ? { \n";
	// parameters.put(key++, cube.getName());
	// // AGREGO LAS MEASURES
	// for (Measure m : cube.getMeasures()) {
	// parameters.put(key++, m.getName());
	// parameters.put(key, m.getType());
	// query += "? ? ,\n";
	// }
	// // AGREGO LAS DIMENSIONES Y LAS FOREIGN KEYS
	// ArrayList<Dimension> dimensions = (ArrayList<Dimension>) cube
	// .getDimensions().values();
	// for (Dimension d : dimensions) {
	// Set<Property> properties = d.getLevel().getProperties();
	// for (Property p : properties) {
	// if (p.isPK()) {
	// parameters.put(key++, p.getName());
	// parameters.put(key++, p.getType());
	// parameters.put(key++, d.getName());
	// parameters.put(key++, p.getName());
	// query += "? ? REFERENCES ?(?),\n";
	// }
	// }
	// }
	// // AGREGO LAS PRIMARY KEYS
	// query += "PRIMARY KEY (";
	// boolean first = true;
	// for (Dimension d : dimensions) {
	// Set<Property> properties = d.getLevel().getProperties();
	// for (Property p : properties) {
	// if (p.isPK()) {
	// parameters.put(key++, p.getName());
	// if (!first) {
	// query += ",?";
	// } else {
	// query += "?";
	// }
	//
	// }
	// }
	// }
	// query += " );\n";
	//
	// return query;
	// }

}
