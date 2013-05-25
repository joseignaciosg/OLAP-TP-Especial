package olap.olap.project.model;

import java.util.HashSet;
import java.util.Set;

public class Dimension {
	
	private Set<Property> properties = new HashSet<Property>();
	private Set<Hierachy> hierachies = new HashSet<Hierachy>();
	private String name;
	
	public Dimension(String name) {
		this.name = name;
	}

	public void addProperty(Property prop) {
		properties.add(prop);
	}
	
	public void addHierachyy(Hierachy h) {
		hierachies.add(h);
	}

	public Set<Hierachy> getHierachies() {
		return hierachies;
	}

	public Set<Property> getProperties() {
		return properties;
	}

	public String getName() {
		return name;
	}
	
}
