package olap.olap.project.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MultiDim {
	
	private Map<String, Dimension> dimensions = new HashMap<String, Dimension>();
		
	public void addDimension(Dimension dim) {
		dimensions.put(dim.getName().toLowerCase(), dim);
	}

	public Collection<Dimension> getDimensions() {
		return dimensions.values();
	}
	
	public Dimension getDimension(String ptr) {
		return dimensions.get(ptr.toLowerCase());
	}

	public void print() {
		System.out.println("MULTIDIM: ");
		for(Dimension d: dimensions.values()) {
			d.print();
		}
	}
}
