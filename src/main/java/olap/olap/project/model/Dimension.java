package olap.olap.project.model;

import java.util.HashSet;
import java.util.Set;

public class Dimension extends PropertyHolder {
	
	private Set<Hierarchy> hierarchies = new HashSet<Hierarchy>();
	private String name;
	
	public Dimension(String name) {
		this.name = name;
	}

	public void addHierarchy(Hierarchy h) {
		hierarchies.add(h);
	}

	public Set<Hierarchy> getHierarchies() {
		return hierarchies;
	}

	public String getName() {
		return name;
	}
	
	public void print() {
		System.out.println("DIM: "+name);
		printProps();
		for (Hierarchy h: hierarchies) {
			h.print();
		}
	}
}
