package olap.olap.project.model;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

public class MultiDim {
	
	private Set<Dimension> dimensions = new HashSet<Dimension>();
	private String name;
	
	public MultiDim(String name) {
		this.name = name;
	}
	
	public void addDimension(Dimension dim) {
		dimensions.add(dim);
	}

	public Set<Dimension> getDimensions() {
		return dimensions;
	}

	public String getName() {
		return name;
	}
}
