package olap.olap.project.model;

public enum SQLAttribute {

	GEOMETRY("Integer"), STRING("varchar"), NUMERIC("Numeric"), INTEGER(
			"Integer"), BOOLEAN("Boolean"), DATE("Date"), TIME("Time"), TIMESTAMP(
			"Timestamp");

	private String name;

	SQLAttribute(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

}
