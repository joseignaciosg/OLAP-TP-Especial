package olap.olap.project.model.api;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import olap.olap.project.model.Dimension;

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
	public Document generateMDXAuto(String outFileName) throws IOException;

	/* -------------------- MANUAL MODE ------------------------------- */

	public Collection<Dimension> getCubeDimensions();

	public List<String> getDBTableNames() throws SQLException, Exception;

	public boolean linkDimension(String cubeDim, String dbTableName);

	/*
	 * Creates MDX out star xml and tables returns: MDX star xml
	 */
	public void generateMDXManual(String outFileName);

}
