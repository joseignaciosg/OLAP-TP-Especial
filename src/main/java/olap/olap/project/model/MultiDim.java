package olap.olap.project.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MultiDim {
	
	private Map<String, Dimension> dimensions = new HashMap<String, Dimension>();
	private Cube cube;

	public MultiDim() {}
	public MultiDim(Cube cube) {
		this.cube = cube;
	}
	
	public Cube getCube() {
		return cube;
	}

	public void setCube(Cube cube) {
		this.cube = cube;
	}

	public void addDimension(Dimension dim) {
		dimensions.put(dim.getName().toLowerCase(), dim);
	}

	public Map<String, Dimension> getDimensions() {
		return dimensions;
	}
	
	public Dimension getDimension(String ptr) {
		return dimensions.get(ptr.toLowerCase());
	}
	
	public Dimension getDimensionValue(String ptr){
		Iterator<Dimension> it = dimensions.values().iterator();
		while(it.hasNext()){
			Dimension d = it.next();
			if(d.getName().equals(ptr)){
				return d;
			}
		}
		return null;
	}

	public void print() {
		System.out.println("MULTIDIM: ");
		for(Dimension d: dimensions.values()) {
			d.print();
		}
	}
	
	
}
