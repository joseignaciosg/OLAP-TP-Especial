package olap.olap.project.model.impl;

import java.io.File;
import java.util.List;

import olap.olap.project.model.Dimension;
import olap.olap.project.model.api.CubeApi;

public class CubeApiImpl implements CubeApi {

	public boolean setDBCredentials(String dbUrl, String name, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean loadMultildimXml(File xmlfile) {
		// TODO Auto-generated method stub
		return false;
	}

	public File generateMDXAuto(String outFileName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Dimension> getCubeDimensions() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getDBTableNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean linkDimension(Dimension cubeDim, String dbTableName) {
		// TODO Auto-generated method stub
		return false;
	}

	public File generateMDXManual(String outFileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
