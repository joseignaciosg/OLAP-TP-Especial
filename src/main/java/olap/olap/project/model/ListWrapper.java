package olap.olap.project.model;

import java.util.List;

public class ListWrapper {
	
	private String tableName;
	private List<String> propList;
	private List<String> fieldList;
	
	public ListWrapper(String tableName, List<String> propList,
			List<String> fieldList) {
		super();
		this.tableName = tableName;
		this.propList = propList;
		this.fieldList = fieldList;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getPropList() {
		return propList;
	}

	public void setPropList(List<String> propList) {
		this.propList = propList;
	}

	public List<String> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<String> fieldList) {
		this.fieldList = fieldList;
	}
	
	
	
}
