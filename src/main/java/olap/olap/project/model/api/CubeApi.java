package olap.olap.project.model.api;

import java.io.File;
import java.util.List;

import olap.olap.project.model.Dimension;


public interface CubeApi {
	
	public boolean setDBCredentials(String dbUrl, String name, String password);
	public boolean loadMultildimXml(File xmlfile);
	
	
	/* -------------------- AUTOMATIC MODE ------------------------------- */
	
	/*
	 * Creates MDX out star xml and tables
	 * 
	 * returns: MDX star xml
	 * */
	public File generateMDXAuto(String outFileName);
	
	
	/* --------------------   MANUAL MODE   ------------------------------- */
	
	public List<Dimension> getCubeDimensions();
	
	public List<String> getDBTableNames();
	
	public boolean linkDimension(Dimension cubeDim, String dbTableName);
	
	
	/*
	 * Creates MDX out star xml and tables
	 * returns: MDX star xml
	 * */
	public File generateMDXManual(String outFileName);
	
	
}
