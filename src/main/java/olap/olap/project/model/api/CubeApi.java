package olap.olap.project.model.api;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import olap.olap.project.model.Dimension;

import org.dom4j.Document;
import org.dom4j.DocumentException;

public interface CubeApi {

	public boolean setDBCredentials(String dbUrl, String name, String password);

	public void loadMultildimXml(File xmlfile) throws DocumentException,
			IOException;

	/* -------------------- AUTOMATIC MODE ------------------------------- */

	/*
	 * Creates MDX out star xml and tables
	 * 
	 * returns: MDX star xml
	 */
	public Document generateMDXAuto(String outFileName) throws IOException, Exception;

	/* -------------------- MANUAL MODE ------------------------------- */

	public Collection<Dimension> getCubeDimensions();

	public List<String> getDBTableNames() throws SQLException, Exception;

	public boolean linkDimension(String cubeDim, String dbTableName);

	public List<String> getDBFieldsForTable(String table) throws Exception;

	public List<String> getPropertiesForDimension(String table)
			throws Exception;
	
	public String getFactTableName();
	
	public boolean setFactTableName(String name);

	/*
	 * Creates MDX out star xml and tables returns: MDX star xml
	 */
	public Document generateMDXManual(String outFileName) throws IOException;

	public boolean changePropertyName(String tableName, String propName,
			String fieldName);
	
	/*para saber la cantidad de propiedades en la fact table*/
	public int getFactTablePropertyCount();
	
	/*para obtener la lista de propiedades de la fact table*/
	public List<String> getFactTableProperties();
	
	
	public boolean changeFactTablePropertyName(String propName, String fieldName);
	
	
	

}
