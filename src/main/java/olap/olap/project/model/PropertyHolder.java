package olap.olap.project.model;

import java.util.HashSet;
import java.util.Set;

public abstract class PropertyHolder {

	private Set<Property> properties = new HashSet<Property>();

	public void addProperty(Property p) {
		properties.add(p);
	}
	
	public Set<Property> getProperties() {
		return properties;
	}
	
	protected void printProps() {
		for(Property p: properties) {
			p.print();
		}
	}
}
