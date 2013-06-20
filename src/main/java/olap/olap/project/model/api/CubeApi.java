package olap.olap.project.model.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;

import olap.olap.project.model.Dimension;

public interface CubeApi {

	public boolean setDBCredentials(String dbUrl, String name, String password);

	public void loadMultildimXml(String xmlfile) throws DocumentException,
			IOException;

	/* -------------------- AUTOMATIC MODE ------------------------------- */

	/*
	 * Creates MDX out star xml and tables
	 * 
	 * returns: MDX star xml
	 */
	public void generateMDXAuto(String outFileName) throws IOException;

	/* -------------------- MANUAL MODE ------------------------------- */

	public List<Dimension> getCubeDimensions();

	public List<String> getDBTableNames();

	public boolean linkDimension(Dimension cubeDim, String dbTableName);

	/*
	 * Creates MDX out star xml and tables returns: MDX star xml
	 */
	public void generateMDXManual(String outFileName);

}
