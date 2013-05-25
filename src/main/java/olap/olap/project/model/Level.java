package olap.olap.project.model;

import java.util.HashSet;
import java.util.Set;

public class Level {

	private Set<Property> properties = new HashSet<Property>();
	private String name;
	private int pos;
	
	public Level(String name, int pos) {
		super();
		this.name = name;
		this.pos = pos;
	}

	public void addProperty(Property p) {
		properties.add(p);
	}
	
	public Set<Property> getProperties() {
		return properties;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPos() {
		return pos;
	}
	
	
	
}
